package xyz.tag.twitch.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.tag.twitch.config.impl.ConfigDataImpl;
import xyz.tag.twitch.entity.Device;
import xyz.tag.twitch.entity.Log;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.repo.DeviceRepo;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ConfigDataTest {
    private static final Iterable<Device> devices = new ArrayList<>();
    @Mock
    private DeviceRepo deviceRepo;

    @InjectMocks
    private ConfigDataImpl configData;

    @BeforeAll
    static void beforeAll() {
        Device allFloodLights = new Device("All Flood Lights", false, LocalDateTime.now().minusMinutes(100), "All Flood Lights", "101");
        final Log allFloodLightsLog = new Log(ESwitch.OFF, EStatus.UNREACHABLE, ZonedDateTime.now().minusMinutes(5), ZonedDateTime.now());

        Device ultraVioletDevice = new Device("Swimming Pool", false, LocalDateTime.now().minusMinutes(120), "Flood-Light-1", "01");
        final Log ultraVioletLog = new Log(ESwitch.OFF, EStatus.UNREACHABLE, ZonedDateTime.now().minusMinutes(10), ZonedDateTime.now());

        Device redDevice = new Device("Backyard", false, LocalDateTime.now(), "Flood-Light-1", "02");
        final Log redLog = new Log(ESwitch.OFF, EStatus.UNREACHABLE, ZonedDateTime.now().minusMinutes(20), ZonedDateTime.now());

        Device greenDevice = new Device("Backyard", false, LocalDateTime.now(), "Flood-Light-2", "03");
        final Log greenLog = new Log(ESwitch.OFF, EStatus.UNREACHABLE, ZonedDateTime.now(), ZonedDateTime.now());

        Device blueDevice = new Device("Swimming Pool", false, LocalDateTime.now(), "Flood-Light-2", "04");
        final Log blueLog = new Log(ESwitch.OFF, EStatus.UNREACHABLE, ZonedDateTime.now(), ZonedDateTime.now());

        allFloodLights.getLogs().add(allFloodLightsLog);
        ultraVioletDevice.getLogs().add(ultraVioletLog);
        redDevice.getLogs().add(redLog);
        greenDevice.getLogs().add(greenLog);
        blueDevice.getLogs().add(blueLog);
        ((ArrayList<Device>) devices).add(allFloodLights);
        ((ArrayList<Device>) devices).add(ultraVioletDevice);
        ((ArrayList<Device>) devices).add(redDevice);
        ((ArrayList<Device>) devices).add(greenDevice);
        ((ArrayList<Device>) devices).add(blueDevice);
    }

    @BeforeEach
    void setUp() {
        when(deviceRepo.saveAll(any())).thenReturn((List<Device>) devices);
    }

    @Test
    void setupAppConfigData() {
        configData.setupAppConfigData();
        assertEquals(((ArrayList<Device>) devices).size(), ((ArrayList<Device>) configData.getDevices()).size());
        verify(deviceRepo, times(1)).saveAll(anyList());
    }
}