package xyz.tag.twitch.integration.feign;

import feign.Feign;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.tag.twitch.dto.electrodev.RespHC;
import xyz.tag.twitch.enums.DeviceType;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.feign.DeviceHealthCheck;
import xyz.tag.twitch.util.CustomGsonDecoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static xyz.tag.twitch.constant.Constants.REST_ELECTRO_DEV_ENDPOINT;

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
    private DeviceHealthCheck electroDev;

    @BeforeEach
    public void pretest() {
        electroDev = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new CustomGsonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(feign.Logger.Level.FULL)
                .target(DeviceHealthCheck.class, REST_ELECTRO_DEV_ENDPOINT);
    }

    @Test
    public void testFeignSwitch() {
        final RespHC hostResp = electroDev.pingHostDevice();

        log.info("Host-Response: {}", hostResp);

        assertEquals(EStatus.ONLINE, hostResp.getEStatus());
        assertEquals(DeviceType.HOST_DEVICE.getDeviceStatus(), hostResp.getDeviceType().getDeviceStatus());
        assertEquals("RPi-1", hostResp.getName());
        assertTrue(hostResp.getLDateTime().isAfter(LocalDateTime.now().minusSeconds(2)));
    }
}