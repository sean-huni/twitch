package xyz.tag.twitch.unit.feign;

import feign.Feign;
import feign.gson.GsonDecoder;
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
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.feign.ElectroDev;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
@ExtendWith(MockitoExtension.class)
@Slf4j
public class FeignSwitchTest {
    private static final String REST_ELECTRO_DEV_TEST_ENDPOINT = "http://192.168.0.146:8083/api/v1";
    @Mock
    private ElectroDev electroDev;
    private MockClient mockClient;

    @BeforeEach
    public void pretest() {
        mockClient = new MockClient().noContent(HttpMethod.PUT, "/device/{id}");

        electroDev = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(feign.Logger.Level.FULL)
                .client(mockClient)
//                .target(new MockTarget<>(ElectroDev.class));
                .target(ElectroDev.class, REST_ELECTRO_DEV_TEST_ENDPOINT);
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void tearDown() {
        mockClient.verifyStatus();
    }

    @Test
    public void givenDeviceID1_whenTogglingSwitchOnn_thenSucceedResp() {
//        mockClient.add(HttpMethod.PUT, "/device/1?switch=ONN", HttpsURLConnection.HTTP_ACCEPTED);
        when(electroDev.invokeSwitch(1L, new Req(ESwitch.ONN))).thenReturn(new Resp(200, "Successful"));
        final Resp resp2 = electroDev.invokeSwitch(1L, new Req(ESwitch.ONN));
        assertEquals(200, (int) resp2.getCode());
    }

    @Test
    public void givenDeviceID1_whenTogglingSwitchOff_thenSucceedResp() {
//        mockClient.add(HttpMethod.PUT, "/device/1?switch=OFF", HttpsURLConnection.HTTP_ACCEPTED);
        when(electroDev.invokeSwitch(1L, new Req(ESwitch.OFF))).thenReturn(new Resp(200, "Successful"));
        final Resp resp1 = electroDev.invokeSwitch(1L, new Req(ESwitch.OFF));
        assertEquals(200, (int) resp1.getCode());
    }
}
