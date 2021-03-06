package xyz.tag.twitch.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import xyz.tag.twitch.dto.DeviceDTO;
import xyz.tag.twitch.dto.LogDTO;
import xyz.tag.twitch.dto.RollingLogDTO;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.exception.DeviceNotFoundException;
import xyz.tag.twitch.feign.ElectroDeviceFeignService;
import xyz.tag.twitch.service.DeviceService;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private ElectroDeviceFeignService raspberryPiService;

    @BeforeEach
    void setup() {
        Resp resp = new Resp(HttpStatus.OK.value(), "Successful");
        when(raspberryPiService.invokeSwitch(1L, req)).thenReturn(resp);
    }

    @Test
    void givenDeviceIdAndSwitchOpt_whenToggleSwitch_thenVerifyInvocation() throws DeviceNotFoundException {
        deviceService.toggleSwitch(3L, ESwitch.ONN);
        verify(raspberryPiService, times(1)).invokeSwitch(2L, req);
    }

    @Test
    void givenDeviceIdAndSwitchOpt_whenToggleSwitch_thenThrowDeviceNotFoundException_andVerifyZeroDeviceSwitchInteractions() throws DeviceNotFoundException {
        DeviceService deviceServiceLocal = mock(DeviceService.class);
        doThrow(new DeviceNotFoundException("404 Device not Found Test.")).when(deviceServiceLocal).toggleSwitch(anyLong(), any());

        Exception exception = assertThrows(DeviceNotFoundException.class, () -> deviceServiceLocal.toggleSwitch(1L, ESwitch.ONN));

        verify(raspberryPiService, times(0)).invokeSwitch(1L, req);
        assertEquals("404 Device not Found Test.", exception.getMessage());
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

    @Test
    void givenDeviceService_whenMeshUpRollingLogs_thenReturnRollingLogs() {
        Collection<RollingLogDTO> rLogs = deviceService.meshUpRollingLogs();

        assertNotNull(rLogs);
        assertFalse(rLogs.isEmpty());
        assertTrue(rLogs.size() > 2);
    }
}