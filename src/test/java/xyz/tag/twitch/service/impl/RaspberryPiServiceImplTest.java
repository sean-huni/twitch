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
import xyz.tag.twitch.service.RaspberryPiService;

import static org.mockito.Mockito.*;

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
    private RaspberryPiService raspberryPiService;

    @BeforeEach
    void setup() {
        when(raspberryPiService.invokeDeviceSwitch(req, 1L)).thenThrow(RetryableException.class);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenDevice_whenInvokeDeviceSwitch_thenThrowException() {
        Assertions.assertThrows(RetryableException.class, () -> {
            raspberryPiService.invokeDeviceSwitch(req, 1L);
        });

        verify(raspberryPiService, times(1)).invokeDeviceSwitch(req, 1L);
        verifyNoMoreInteractions(raspberryPiService);
    }
}