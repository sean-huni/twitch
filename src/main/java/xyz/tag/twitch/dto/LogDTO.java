package xyz.tag.twitch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.enums.ESwitch;

import java.time.ZonedDateTime;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.dto
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 14:19
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogDTO {
    private Long id;
    private ESwitch eSwitch;
    private EStatus eStatus;
    private ZonedDateTime reqDateTime;
    private ZonedDateTime respDateTime;
}
