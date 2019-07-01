package xyz.tag.twitch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import xyz.tag.twitch.dto.DeviceDTO;
import xyz.tag.twitch.dto.LogDTO;
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
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Slf4j
@RestController
@RequestMapping("api/v1/devices")
public class DeviceController {
    private DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public Map<String, Collection<DeviceDTO>> getAllDevices() {
        final Map<String, Collection<DeviceDTO>> devicesMap = new HashMap<>();
        devicesMap.put("devices", deviceService.findAllDevices());
        return devicesMap;
    }

    @GetMapping("{id}/logs")
    public Map<String, Collection<LogDTO>> getDeviceLogs(@PathVariable("id") Long id) {
        Map<String, Collection<LogDTO>> logsMap = new HashMap<>();
        logsMap.put("logs", deviceService.findDeviceLogs(id));
        return logsMap;
    }

    @PutMapping("{id}")
    public void toggleSwitch(@PathVariable("id") Long id, @RequestParam("switch") ESwitch option) {
//       final EnumSwitch option = !sw.isEmpty() && sw.equals("0") ? EnumSwitch.OFF : EnumSwitch.ONN;
        log.info("Incoming PUT-Req: device-id: {}, switch: {}", id, option);
        try {
            deviceService.toggleSwitch(id, option);
        } catch (DeviceNotFound dnf) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, dnf.getMessage());
        }
    }
}
