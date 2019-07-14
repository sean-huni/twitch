package xyz.tag.twitch.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import xyz.tag.twitch.dto.DeviceDTO;
import xyz.tag.twitch.dto.LogDTO;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.exception.DeviceNotFound;
import xyz.tag.twitch.service.DeviceService;
import xyz.tag.twitch.service.RaspberryPiService;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.service.impl
 * USER      : sean
 * DATE      : 14-Sun-Jul-2019
 * TIME      : 10:56
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
class DeviceServiceImplTest {
    private final Req req = new Req(ESwitch.ONN);
    @Autowired
    private DeviceService deviceService;

    @MockBean
    private RaspberryPiService raspberryPiService;

    @BeforeEach
    void setup() {
        final Resp resp = new Resp(HttpStatus.OK.value(), "Successful");
        when(raspberryPiService.invokeDeviceSwitch(req, 1L)).thenReturn(resp);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenDeviceIdAndSwitchOpt_whenToggleSwitch_thenVerifyInvocation() throws DeviceNotFound {
        deviceService.toggleSwitch(1L, ESwitch.ONN);
        verify(raspberryPiService, times(1)).invokeDeviceSwitch(req, 1L);
    }

    @Test
    void givenDeviceService_whenFindAllDevices_thenReturnDevices() {
        final Collection<DeviceDTO> devices = deviceService.findAllDevices();

        assertNotNull(devices);
        assertTrue(devices.size() > 3);
    }

    @Test
    void givenDeviceService_whenFindAllDeviceLogs_thenReturnDeviceLogs() {
        final Collection<LogDTO> deviceLogs = deviceService.findDeviceLogs(1L);

        assertNotNull(deviceLogs);
        assertTrue(deviceLogs.size() >= 1);
    }
}