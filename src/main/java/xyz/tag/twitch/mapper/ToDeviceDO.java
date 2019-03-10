package xyz.tag.twitch.mapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.tag.twitch.dto.DeviceDTO;
import xyz.tag.twitch.dto.LogDTO;
import xyz.tag.twitch.entity.Device;
import xyz.tag.twitch.entity.Log;

import java.util.stream.Collectors;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.mapper
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 11:21
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Component
public class ToDeviceDO implements Converter<DeviceDTO, Device> {
    private Converter<LogDTO, Log> converter;

    public ToDeviceDO(Converter<LogDTO, Log> converter) {
        this.converter = converter;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param s the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public final Device convert(DeviceDTO s) {
        return new Device(s.getId(), s.isOnn(), s.getLocation(), s.getType(), s.getChannel(), s.getLocalDateTime(), s.getLogs().stream().map(converter::convert).collect(Collectors.toList()));
    }
}
