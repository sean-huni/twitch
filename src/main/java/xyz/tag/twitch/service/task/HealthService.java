package xyz.tag.twitch.service.task;

import feign.Feign;
import feign.RetryableException;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.tag.twitch.dto.electrodev.RespHC;
import xyz.tag.twitch.entity.RespHealthCheckDO;
import xyz.tag.twitch.enums.DeviceType;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.feign.DeviceHealthCheck;
import xyz.tag.twitch.repo.RespHealthCheckRepo;
import xyz.tag.twitch.util.CustomGsonDecoder;

import java.time.LocalDateTime;

import static xyz.tag.twitch.constant.Constants.REST_ELECTRO_DEV_ENDPOINT;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.task
 * USER      : sean
 * DATE      : 24-Sun-Mar-2019
 * TIME      : 23:41
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Service
@Configuration
@Slf4j
public class HealthService {
    private final RespHealthCheckRepo respHealthCheckRepo;
    private final DeviceHealthCheck deviceHealthCheck;
    private Converter<RespHC, RespHealthCheckDO> respHCRespHealthCheckDOConverter;

    public HealthService(RespHealthCheckRepo respHealthCheckRepo, Converter<RespHC, RespHealthCheckDO> respHCRespHealthCheckDOConverter) {
        this.respHealthCheckRepo = respHealthCheckRepo;
        this.respHCRespHealthCheckDOConverter = respHCRespHealthCheckDOConverter;
        deviceHealthCheck = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new CustomGsonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(feign.Logger.Level.FULL)
                .target(DeviceHealthCheck.class, REST_ELECTRO_DEV_ENDPOINT);
    }

    @Scheduled(cron = "${purge.cron.expression}")
    public void ping3rdPartyDevices() {

        RespHC hostResp = null;
        try {
            hostResp = deviceHealthCheck.pingHostDevice();
            log.debug("Device Response: {}", hostResp);
        } catch (RetryableException e) {
            log.error("Connection Exception: " + e.getMessage());
            hostResp = new RespHC("RPi-1", DeviceType.HOST_DEVICE, EStatus.UNREACHABLE, LocalDateTime.now());
        } catch (Exception e) {
            log.error("System Error", e);
            hostResp = new RespHC("RPi-1", DeviceType.HOST_DEVICE, EStatus.SYSTEM_ERROR, LocalDateTime.now());
        } finally {
            RespHealthCheckDO healthCheckDO = respHCRespHealthCheckDOConverter.convert(hostResp);
            respHealthCheckRepo.save(healthCheckDO);
            log.info("Host-Response: {}", hostResp);
        }
    }
}
