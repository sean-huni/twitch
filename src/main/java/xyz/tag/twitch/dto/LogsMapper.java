package xyz.tag.twitch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.tag.twitch.entity.Log;
import xyz.tag.twitch.entity.RespHealthCheckDO;

import java.util.Collection;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.dto
 * USER      : sean
 * DATE      : 31-Sat-Aug-2019
 * TIME      : 11:17
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogsMapper {
    private Collection<Log> operationalLogs;
    private Collection<RespHealthCheckDO> healthCheckLogs;
}
