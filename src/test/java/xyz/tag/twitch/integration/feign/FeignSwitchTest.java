package xyz.tag.twitch.integration.feign;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.feign.ElectroDev;

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
public class FeignSwitchTest {
    private ElectroDev electroDev;

    @BeforeEach
    public void pretest() {
        electroDev = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(feign.Logger.Level.FULL)
                .target(ElectroDev.class, REST_ELECTRO_DEV_ENDPOINT);
    }

    @Test
    public void testFeignSwitch() {

        final Resp resp1 = electroDev.invokeSwitch(1L, new Req(ESwitch.OFF));
        final Resp resp2 = electroDev.invokeSwitch(1L, new Req(ESwitch.ONN));
        final Resp resp3 = electroDev.invokeSwitch(1L, new Req(ESwitch.OFF));
        final Resp  resp4 = electroDev.invokeSwitch(1L, new Req(ESwitch.ONN));


        assertTrue(resp1.getCode() == 200);
        assertTrue(resp2.getCode() == 200);
        assertTrue(resp3.getCode() == 200);
        assertTrue(resp4.getCode() == 200);
    }
}
