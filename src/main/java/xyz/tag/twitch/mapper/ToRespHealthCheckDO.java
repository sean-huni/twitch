package xyz.tag.twitch.mapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.tag.twitch.dto.electrodev.RespHC;
import xyz.tag.twitch.entity.RespHealthCheckDO;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.mapper
 * USER      : sean
 * DATE      : 24-Sun-Mar-2019
 * TIME      : 23:55
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */

@Component
public class ToRespHealthCheckDO implements Converter<RespHC, RespHealthCheckDO> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param s the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public RespHealthCheckDO convert(RespHC s) {
        return new RespHealthCheckDO(null, s.getName(), s.getDeviceType(), s.getEStatus(), s.getLDateTime());
    }
}
