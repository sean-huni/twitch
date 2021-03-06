package xyz.tag.twitch.controller;


import feign.RetryableException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.feign.ElectroDeviceFeignService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
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
@Log4j2
@DirtiesContext
class DeviceControllerPositiveTest {

    private final Req req = new Req(ESwitch.ONN);
    private final Resp resp = new Resp(HttpStatus.OK.value(), "Successful");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElectroDeviceFeignService raspberryPiService;

    @BeforeAll
    static void setUp() {
        log.info("Kicking off tests...");
    }

    @AfterAll
    static void tearDown() {
        log.info("Tearing down tests...");
    }

    @BeforeEach
    void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(deviceService).build();
        try {
            lenient().when(raspberryPiService.invokeSwitch(isA(Long.class), isA(Req.class))).thenReturn(resp);
        } catch (RetryableException e) {
            e.printStackTrace();
        }
    }

    @Test
    void aGivenMvcMock_whenGetDevicesViaRestAPI_thenReturnAllDevices() throws Exception {

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
                .andExpect(jsonPath("$['devices'][3]['id']").value(4));
    }

    @Test
    void bGivenDeviceId_whenRetrievingDeviceLogs_thenReturnDeviceLogs() throws Exception {
        assertNotNull(mockMvc);
        final MvcResult mvcResult = mockMvc.perform(get("/api/v1/devices/01/logs")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$['logs'][0]['id']").isNotEmpty())
                .andExpect(jsonPath("$['logs'][0]['id']").isNumber())
                .andExpect(jsonPath("$['logs'][0]['id']").value(1))
                .andExpect(jsonPath("$['logs'][0]['eswitch']").value(ESwitch.OFF.getStatus()))
                .andExpect(jsonPath("$['logs'][0]['estatus']").value(EStatus.UNREACHABLE.getStatus()))
                .andReturn();

        log.debug("Device ID-01 Logs: {}", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void givenDeviceById1_whenHttpPutOnDevice_thenToggleSwitchONN() throws Exception {
        assertNotNull(mockMvc);
        final MvcResult resp = mockMvc.perform(put("/api/v1/devices/3")
                .content("{\"status\": \"ONN\"}")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        verify(raspberryPiService, times(1)).invokeSwitch(2L, req);
        verifyZeroInteractions(raspberryPiService);

        log.debug("Toggle-Switch Resp: {}", resp.getResponse().getContentAsString());
    }

    @Test
    void dGivenRestController_whenHttpGetRollingLogs_thenReturnRollingLogs() throws Exception {
        assertNotNull(mockMvc);

        final MvcResult resp = mockMvc.perform(get("/api/v1/devices/rolling-logs")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$['rolling-logs'][0]['dateTime']").isNotEmpty())
                .andExpect(jsonPath("$['rolling-logs'][0]['status']").exists())
                .andExpect(jsonPath("$['rolling-logs'][0]['item']").isString())
                .andReturn();

        log.debug("Toggle-Switch Resp: {}", resp.getResponse().getContentAsString());
    }
}
