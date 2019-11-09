package xyz.tag.twitch.mapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.tag.twitch.dto.req.enums.ESwitchDTO;
import xyz.tag.twitch.enums.ESwitch;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.mapper
 * USER      : sean
 * DATE      : 09-Sat-Nov-2019
 * TIME      : 10:31
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Component("toSwitchDO")
public class ToESwitchDO implements Converter<ESwitchDTO, ESwitch> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public ESwitch convert(final ESwitchDTO source) {
        return ESwitch.valueOf(source.getStatus());
    }
}
