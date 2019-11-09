package xyz.tag.twitch.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.enums
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 08:16
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Getter
@ToString
public enum ESwitch {
    OFF("OFF"), ONN("ONN");

    private final String status;

    ESwitch(String status) {
        this.status = status;
    }
}
