package xyz.tag.twitch.enums;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.enums
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 13:36
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
public enum EStatus {
    ONLINE("ONLINE"), //The Device is Online
    OFFLINE("OFFLINE"), //The device in online but channel is unreachable.
    UNREACHABLE("UNREACHABLE"); //The device & channel both are unavailable for use.

    private final String status;

    EStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
