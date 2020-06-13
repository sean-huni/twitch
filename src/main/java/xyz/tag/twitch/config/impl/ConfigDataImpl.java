package xyz.tag.twitch.config.impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import xyz.tag.twitch.config.ConfigData;
import xyz.tag.twitch.entity.Device;
import xyz.tag.twitch.entity.Log;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.repo.DeviceRepo;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Component
public class ConfigDataImpl implements ConfigData {
    private final DeviceRepo deviceRepo;
    @Getter
    private final Iterable<Device> devices = new ArrayList<>();

    public ConfigDataImpl(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    public void setupAppConfigData() {
        Device allFloodLights = new Device("All Flood Lights", false, LocalDateTime.now().minusMinutes(100), "All Flood Lights", "101");
        final Log allFloodLightsLog = new Log(ESwitch.valueOf("OFF"), EStatus.valueOf("UNREACHABLE"), ZonedDateTime.now().minusMinutes(5), ZonedDateTime.now());

        Device ultraVioletDevice = new Device("Swimming Pool", false, LocalDateTime.now().minusMinutes(120), "Flood-Light-1", "01");
        final Log ultraVioletLog = new Log(ESwitch.valueOf("OFF"), EStatus.valueOf("UNREACHABLE"), ZonedDateTime.now().minusMinutes(10), ZonedDateTime.now());

        Device redDevice = new Device("Backyard", false, LocalDateTime.now(), "Flood-Light-1", "02");
        final Log redLog = new Log(ESwitch.valueOf("OFF"), EStatus.valueOf("UNREACHABLE"), ZonedDateTime.now().minusMinutes(20), ZonedDateTime.now());

        Device greenDevice = new Device("Backyard", false, LocalDateTime.now(), "Flood-Light-2", "03");
        final Log greenLog = new Log(ESwitch.valueOf("OFF"), EStatus.valueOf("UNREACHABLE"), ZonedDateTime.now(), ZonedDateTime.now());

        Device blueDevice = new Device("Swimming Pool", false, LocalDateTime.now(), "Flood-Light-2", "04");
        final Log blueLog = new Log(ESwitch.valueOf("OFF"), EStatus.valueOf("UNREACHABLE"), ZonedDateTime.now(), ZonedDateTime.now());

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
        this.deviceRepo.saveAll(devices);
    }
}
