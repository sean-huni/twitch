package xyz.tag.twitch.integration.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.integration.controller
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 19:01
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
//@WebMvcTest(value = SwitchController.class)
public class SwitchCtrlTests {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void pretest() {
        this.mockMvc = webAppContextSetup(wac).build();
    }

//    @Ignore("NullPointerException on mockMvc")
    @Test
    public void aGivenAllDevices_whenRetrievingDevices_thenPass() throws Exception {

        assertNotNull(mockMvc);
        mockMvc.perform(get("/api/v1/switch")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn();
    }
}
