package xyz.tag.twitch.mapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.tag.twitch.dto.LogsMapper;
import xyz.tag.twitch.dto.RollingLogDTO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.mapper
 * USER      : sean
 * DATE      : 31-Sat-Aug-2019
 * TIME      : 11:21
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Component("RollingDTOConverter")
public class ToRollingLogsDTO implements Converter<LogsMapper, Collection<RollingLogDTO>> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     * <p>
     * Combines the health logs with operational logs.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Collection<RollingLogDTO> convert(LogsMapper source) {
        final Collection<RollingLogDTO> rollingLogs = new ArrayList<>();
        source.getHealthCheckLogs().parallelStream().forEach(respHealthCheckDO -> {
            final RollingLogDTO rollingLogDTO = new RollingLogDTO(null, respHealthCheckDO.getName(), respHealthCheckDO.getEStatus().getStatus(), respHealthCheckDO.getLDateTime());
            rollingLogs.add(rollingLogDTO);
        });
        source.getOperationalLogs().parallelStream().forEach(log -> {
            final RollingLogDTO rollingLogDTO = new RollingLogDTO(log.getId(), null, log.getESwitch().getStatus(), log.getRespDateTime().toLocalDateTime());
            rollingLogs.add(rollingLogDTO);
        });
        return rollingLogs;
    }
}
