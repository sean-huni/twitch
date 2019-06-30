package xyz.tag.twitch.integration.controller;


import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.service.RaspberryPiService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.integration.controller
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 19:01
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc //need this in Spring Boot test
@Slf4j
public class SwitchCtrlTests {

    private final Req req = new Req(ESwitch.ONN);
    private final Resp resp = new Resp(200, "Successful");

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RaspberryPiService raspberryPiService;

    @BeforeAll
    public static void setUp() {
        log.info("Kicking off tests...");
    }

    @AfterAll
    public static void tearDown() {
        log.info("Tearing down tests...");
    }

    @BeforeEach
    void beforeEachTest() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(deviceService).build();
        try {
            lenient().when(raspberryPiService.invokeDeviceSwitch(isA(Req.class), isA(Long.class))).thenReturn(resp);
        } catch (RetryableException e) {
            e.printStackTrace();
        }
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void aGivenAllDevices_whenRetrievingDevices_thenReturnAllDevices() throws Exception {

        assertNotNull(mockMvc);
        mockMvc.perform(get("/api/v1/devices")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$['devices'][0]['id']").isNotEmpty())
                .andExpect(jsonPath("$['devices'][0]['id']").isNumber())
                .andExpect(jsonPath("$['devices'][0]['id']").value(1))

                .andExpect(jsonPath("$['devices'][3]['id']").isNotEmpty())
                .andExpect(jsonPath("$['devices'][3]['id']").isNumber())
                .andExpect(jsonPath("$['devices'][3]['id']").value(7));
    }

    @Test
    public void bGivenAllDevicesById_whenUpdatingDevice_thenToggleSwitch() throws Exception {
        assertNotNull(mockMvc);
//        doNothing().when(deviceService).toggleSwitch(isA(Long.class), isA(ESwitch.class));
//        when(raspberryPiService.invokeDeviceSwitch(req, 1L)).thenReturn(resp);
        final MvcResult resp = mockMvc.perform(put("/api/v1/devices/01?switch=ONN")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        verify(raspberryPiService, times(1)).invokeDeviceSwitch(req, 1L);
        verifyZeroInteractions(raspberryPiService);

        log.info("Toggle-Switch Resp: {}", resp.getResponse().getContentAsString());
//                .andExpect(jsonPath())
    }
}
