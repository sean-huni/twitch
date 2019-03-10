package xyz.tag.twitch.enums;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.enums
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 08:16
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
public enum ESwitch {
    OFF("OFF"), ONN("ONN");

    private final String status;

    ESwitch(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
