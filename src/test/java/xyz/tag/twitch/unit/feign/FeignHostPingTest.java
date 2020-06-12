package xyz.tag.twitch.unit.feign;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.tag.twitch.dto.electrodev.RespHC;
import xyz.tag.twitch.enums.DeviceType;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.feign.DeviceHealthCheckFeignService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.unit.feign
 * USER      : sean
 * DATE      : 24-Sun-Mar-2019
 * TIME      : 17:19
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@ExtendWith(SpringExtension.class)
@Slf4j
public class FeignHostPingTest {
    @Mock
    private DeviceHealthCheckFeignService deviceHealthCheckFeignService;

    @BeforeEach
    public void pretest() {
    }


    @Test
    public void givenFeignEndpoint_whenPingingHostDevice_thenReturnHostDeviceHealthInfo() {
        when(deviceHealthCheckFeignService.pingHostDevice())
                .thenReturn(new RespHC("RPi-1", DeviceType.HOST_DEVICE, EStatus.ONLINE, LocalDateTime.now()));
        final RespHC hostResp = deviceHealthCheckFeignService.pingHostDevice();

        log.info("Host-Response: {}", hostResp);

        assertEquals(EStatus.ONLINE, hostResp.getEStatus());
        assertEquals(DeviceType.HOST_DEVICE.getDeviceStatus(), hostResp.getDeviceType().getDeviceStatus());
        assertEquals("RPi-1", hostResp.getName());
        assertTrue(hostResp.getLDateTime().isAfter(LocalDateTime.now().minusSeconds(2)));
        verify(deviceHealthCheckFeignService, times(1)).pingHostDevice();
    }

    @Test
    public void givenFeignEndpoint_whenPingingRelayChannel_thenReturnRelayChannelHealthInfo() {
        when(deviceHealthCheckFeignService.pingChannel(1L))
                .thenReturn(new RespHC("RPi-1", DeviceType.RELAY_CHANNEL, EStatus.ONLINE, LocalDateTime.now()));
        final RespHC hostResp = deviceHealthCheckFeignService.pingChannel(1L);

        log.info("Host-Response: {}", hostResp);

        assertEquals(EStatus.ONLINE, hostResp.getEStatus());
        assertEquals(DeviceType.RELAY_CHANNEL.getDeviceStatus(), hostResp.getDeviceType().getDeviceStatus());
        assertEquals("RPi-1", hostResp.getName());
        assertTrue(hostResp.getLDateTime().isAfter(LocalDateTime.now().minusSeconds(2)));
        verify(deviceHealthCheckFeignService, times(1)).pingChannel(1L);
    }
}