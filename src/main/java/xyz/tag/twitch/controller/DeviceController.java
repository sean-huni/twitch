package xyz.tag.twitch.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import xyz.tag.twitch.dto.DeviceDTO;
import xyz.tag.twitch.dto.LogDTO;
import xyz.tag.twitch.dto.RollingLogDTO;
import xyz.tag.twitch.dto.req.enums.ESwitchDTO;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.exception.DeviceNotFound;
import xyz.tag.twitch.service.DeviceService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.controller
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 12:10
 * E-MAIL    : sean2kay@gmail.com
 * CELL      : +27-81-814-3302
 */
@Log4j2
@RestController
@RequestMapping("api/v1/devices")
public class DeviceController {
    private final DeviceService deviceService;
    private final Converter toSwitchDO;

    public DeviceController(DeviceService deviceService, @Qualifier("toSwitchDO") Converter toSwitchDO) {
        this.deviceService = deviceService;
        this.toSwitchDO = toSwitchDO;
    }

    @GetMapping
    public Map<String, Collection<DeviceDTO>> getAllDevices() {
        final Map<String, Collection<DeviceDTO>> devicesMap = new HashMap<>();
        devicesMap.put("devices", deviceService.findAllDevices());
        return devicesMap;
    }

    @GetMapping("rolling-logs")
    public Map<String, Collection<RollingLogDTO>> getDeviceLogs() {
        Map<String, Collection<RollingLogDTO>> rollingLogsMap = new HashMap<>();
        rollingLogsMap.put("rolling-logs", deviceService.meshUpRollingLogs());
        return rollingLogsMap;
    }

    @GetMapping("{id}/logs")
    public Map<String, Collection<LogDTO>> getDeviceLogs(@PathVariable("id") Long id) {
        Map<String, Collection<LogDTO>> logsMap = new HashMap<>();
        logsMap.put("logs", deviceService.findDeviceLogs(id));
        return logsMap;
    }

    @PutMapping("{id}")
    public void toggleSwitch(@PathVariable("id") Long id, @RequestBody ESwitchDTO option) {
        log.info("Incoming PUT-Req: device-id: {}, switch: {}", id, option);
        try {
            deviceService.toggleSwitch(id, (ESwitch) toSwitchDO.convert(option));
        } catch (DeviceNotFound dnf) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, dnf.getMessage());
        }
    }
}
