package xyz.tag.twitch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import xyz.tag.twitch.config.ConfigData;

import javax.annotation.PostConstruct;

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
    private final ConfigData configData;

    public TwitchApp(ConfigData configData) {
        this.configData = configData;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitchApp.class, args);
    }

    @PostConstruct
    private void startUpData() {
        configData.setupAppConfigData();
    }
}
