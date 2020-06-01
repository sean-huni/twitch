package xyz.tag.twitch.unit.feign;

import feign.Feign;
import feign.gson.GsonEncoder;
import feign.mock.HttpMethod;
import feign.mock.MockClient;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.tag.twitch.dto.electrodev.RespHC;
import xyz.tag.twitch.enums.DeviceType;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.feign.DeviceHealthCheckFeignService;
import xyz.tag.twitch.util.CustomGsonDecoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private static final String REST_DEV_HEALTH_TEST_ENDPOINT = "http://192.168.0.146:8083/api/v1";

    @Mock
    private DeviceHealthCheckFeignService deviceHealthCheckFeignService;
    private MockClient mockClient;

    @BeforeEach
    public void pretest() {
        mockClient = new MockClient().noContent(HttpMethod.PUT, "/device/{id}");

        deviceHealthCheckFeignService = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new CustomGsonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(feign.Logger.Level.FULL)
                .client(mockClient)
                .target(DeviceHealthCheckFeignService.class, REST_DEV_HEALTH_TEST_ENDPOINT);
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void tearDown() {
        mockClient.verifyStatus();
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
    }
}