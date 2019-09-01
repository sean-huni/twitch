package xyz.tag.twitch.enums;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.enums
 * USER      : sean
 * DATE      : 24-Sun-Mar-2019
 * TIME      : 16:10
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
public enum DeviceType {
    HOST_DEVICE("HOST"), RELAY_CHANNEL("CHANNEL");

    private final String deviceStatus;

    DeviceType(String deviceType) {
        this.deviceStatus = deviceType;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }
}
