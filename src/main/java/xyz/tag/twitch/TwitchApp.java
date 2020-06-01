package xyz.tag.twitch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import xyz.tag.twitch.entity.Device;
import xyz.tag.twitch.entity.Log;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.repo.DeviceRepo;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 16:00
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class TwitchApp {
    private final DeviceRepo deviceRepo;

    public TwitchApp(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitchApp.class, args);
    }

    @PostConstruct
    private void startUpData() {
        Iterable<Device> devices = new ArrayList<>();
        Device allFloodLights = new Device("All Flood Lights", false, LocalDateTime.now().minusMinutes(100), "Flood-Light-1", "101");
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
        deviceRepo.saveAll(devices);
    }
}
