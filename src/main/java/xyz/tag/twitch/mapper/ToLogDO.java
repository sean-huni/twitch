package xyz.tag.twitch.mapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.tag.twitch.dto.LogDTO;
import xyz.tag.twitch.entity.Log;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.mapper
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 14:24
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Component
public class ToLogDO implements Converter<LogDTO, Log> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param s the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public final Log convert(LogDTO s) {
        return new Log(s.getId(), s.getESwitch(), s.getEStatus(), s.getReqDateTime(), s.getRespDateTime());
    }
}
