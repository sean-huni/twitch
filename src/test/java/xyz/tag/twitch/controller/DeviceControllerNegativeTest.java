package xyz.tag.twitch.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import xyz.tag.twitch.exception.DeviceNotFound;
import xyz.tag.twitch.service.DeviceService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.controller
 * USER      : sean
 * DATE      : 03-Fri-Jan-2020
 * TIME      : 10:05
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc //need this in Spring Boot test
@Slf4j
class DeviceControllerNegativeTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Test
    void cGivenDeviceById_whenHttpPutOnDevice_thenReturn404() throws Exception {
        assertNotNull(mockMvc);
        doThrow(new DeviceNotFound("404 Device not Found Test.")).when(deviceService).toggleSwitch(anyLong(), any());
        final MvcResult resp = mockMvc.perform(put("/api/v1/devices/00")
                .content("{\"status\": \"ONN\"}")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        verify(deviceService, times(1)).toggleSwitch(anyLong(), any());
        verifyZeroInteractions(deviceService);

        log.debug("Toggle-Switch Resp: {}", resp.getResponse().getContentAsString());
    }

}