package xyz.tag.twitch.dto.electrodev;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.dto.electrodev
 * USER      : sean
 * DATE      : 07-Thu-Mar-2019
 * TIME      : 20:02
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resp {
    private Integer code;
    private String response;
}
