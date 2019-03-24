package xyz.tag.twitch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@EnableScheduling
public class TwitchApp {
    private DeviceRepo deviceRepo;

    public TwitchApp(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitchApp.class, args);
    }

    @PostConstruct
    private void startUpData() {
        Iterable<Device> devices = new ArrayList<>();
        Device ultraVioletDevice = new Device("Bedroom", false, LocalDateTime.now().minusMinutes(120), "ULTRA-VIOLET-BULB", "01");
        final Log ultraVioletLog = new Log(ESwitch.valueOf("OFF"), EStatus.valueOf("UNREACHABLE"), ZonedDateTime.now().minusMinutes(10), ZonedDateTime.now());

        Device redDevice = new Device("Bathroom", false, LocalDateTime.now(), "RED-LED", "02");
        final Log redLog = new Log(ESwitch.valueOf("OFF"), EStatus.valueOf("UNREACHABLE"), ZonedDateTime.now().minusMinutes(20), ZonedDateTime.now());

        Device greenDevice = new Device("Bathroom", false, LocalDateTime.now(), "GREEN-LED", "03");
        final Log greenLog = new Log(ESwitch.valueOf("OFF"), EStatus.valueOf("UNREACHABLE"), ZonedDateTime.now(), ZonedDateTime.now());

        Device blueDevice = new Device("Bathroom", false, LocalDateTime.now(), "BLUE-LED", "04");
        final Log blueLog = new Log(ESwitch.valueOf("OFF"), EStatus.valueOf("UNREACHABLE"), ZonedDateTime.now(), ZonedDateTime.now());

        ultraVioletDevice.getLogs().add(ultraVioletLog);
        redDevice.getLogs().add(redLog);
        greenDevice.getLogs().add(greenLog);
        blueDevice.getLogs().add(blueLog);
        ((ArrayList<Device>) devices).add(ultraVioletDevice);
        ((ArrayList<Device>) devices).add(redDevice);
        ((ArrayList<Device>) devices).add(greenDevice);
        ((ArrayList<Device>) devices).add(blueDevice);
        deviceRepo.saveAll(devices);
    }
}
