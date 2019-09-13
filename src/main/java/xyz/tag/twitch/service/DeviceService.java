package xyz.tag.twitch.service;

import xyz.tag.twitch.dto.DeviceDTO;
import xyz.tag.twitch.dto.LogDTO;
import xyz.tag.twitch.dto.RollingLogDTO;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.exception.DeviceNotFound;

import java.util.Collection;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.service
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 11:17
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
public interface DeviceService {

    /**
     * Turns the switch Onn or Off
     *
     * @param id
     * @param option
     * @return
     */
    void toggleSwitch(Long id, ESwitch option) throws DeviceNotFound;

    Collection<DeviceDTO> findAllDevices();

    Collection<LogDTO> findDeviceLogs(Long id);

    Collection<RollingLogDTO> meshUpRollingLogs();
}
