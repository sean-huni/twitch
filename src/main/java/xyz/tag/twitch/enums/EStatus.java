package xyz.tag.twitch.enums;

import lombok.Getter;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.enums
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 13:36
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Getter
public enum EStatus {
    ONLINE("ONLINE"), //The Device is Online.
    OFFLINE("OFFLINE"), //The device in online but channel is unreachable.
    UNREACHABLE("UNREACHABLE"), //The device & channel both are unavailable for use.
    SYSTEM_ERROR("SYSTEM_ERROR"); //Unknown Exception occurred.

    private final String status;

    EStatus(String status) {
        this.status = status;
    }

}
