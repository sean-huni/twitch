package xyz.tag.twitch.integration.feign;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.feign.ElectroDev;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
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
    private ElectroDev electroDev = Mockito.spy(ElectroDev.class);

    @BeforeEach
    public void pretest() {
        electroDev = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(feign.Logger.Level.FULL)
                .target(ElectroDev.class, REST_ELECTRO_DEV_ENDPOINT);

        when(electroDev.invokeSwitch(isA(Long.class), isA(Req.class)));
    }

    @Test
    public void givenDeviceID1_whenTogglingSwitchOnn_thenSucceedResp() {
        final Resp resp2 = electroDev.invokeSwitch(1L, new Req(ESwitch.ONN));
        assertTrue(resp2.getCode() == 200);
    }

    @Test
    public void givenDeviceID1_whenTogglingSwitchOff_thenSucceedResp() {
        final Resp resp1 = electroDev.invokeSwitch(1L, new Req(ESwitch.OFF));
        assertTrue(resp1.getCode() == 200);
    }
}
