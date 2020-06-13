package xyz.tag.twitch.unit.feign;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.feign.ElectroDeviceFeignService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
@ExtendWith(MockitoExtension.class)
@Slf4j
public class FeignSwitchTest {
    @Mock
    private ElectroDeviceFeignService electroDeviceFeignService;

    @BeforeEach
    void pretest() {
        electroDeviceFeignService = mock(ElectroDeviceFeignService.class);
    }

    @AfterEach
    void tearDown() {
        verify(electroDeviceFeignService, times(1)).invokeSwitch(any(), any());
    }

    @Test
    void givenDeviceID1_whenTogglingSwitchOnn_thenSucceedResp() {
//        mockClient.add(HttpMethod.PUT, "/device/1?switch=ONN", HttpsURLConnection.HTTP_ACCEPTED);
        when(electroDeviceFeignService.invokeSwitch(1L, new Req(ESwitch.ONN))).thenReturn(new Resp(200, "Successful"));
        final Resp resp2 = electroDeviceFeignService.invokeSwitch(1L, new Req(ESwitch.ONN));
        assertEquals(200, (int) resp2.getCode());
    }

    @Test
    void givenDeviceID1_whenTogglingSwitchOff_thenSucceedResp() {
//        mockClient.add(HttpMethod.PUT, "/device/1?switch=OFF", HttpsURLConnection.HTTP_ACCEPTED);
        when(electroDeviceFeignService.invokeSwitch(1L, new Req(ESwitch.OFF))).thenReturn(new Resp(200, "Successful"));
        final Resp resp1 = electroDeviceFeignService.invokeSwitch(1L, new Req(ESwitch.OFF));
        assertEquals(200, (int) resp1.getCode());
    }
}
