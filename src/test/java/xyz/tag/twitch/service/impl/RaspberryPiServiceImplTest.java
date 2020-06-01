package xyz.tag.twitch.service.impl;

import feign.RetryableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.feign.ElectroDeviceFeignService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.service.impl
 * USER      : sean
 * DATE      : 13-Sat-Jul-2019
 * TIME      : 15:04
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RaspberryPiServiceImplTest {
    final Req req = new Req(ESwitch.ONN);

    @MockBean
    private ElectroDeviceFeignService raspberryPiService;

    @BeforeEach
    void setup() {
        when(raspberryPiService.invokeSwitch(1L, req)).thenThrow(RetryableException.class);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenDevice_whenInvokeDeviceSwitch_thenThrowException() {
        Assertions.assertThrows(RetryableException.class, () -> {
            raspberryPiService.invokeSwitch(1L, req);
        });

        verify(raspberryPiService, times(1)).invokeSwitch(1L, req);
        verifyNoMoreInteractions(raspberryPiService);
    }
}