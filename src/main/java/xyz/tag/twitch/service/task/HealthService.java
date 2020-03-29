package xyz.tag.twitch.service.task;

import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.tag.twitch.dto.electrodev.RespHC;
import xyz.tag.twitch.entity.RespHealthCheckDO;
import xyz.tag.twitch.enums.DeviceType;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.feign.DeviceHealthCheckFeignService;
import xyz.tag.twitch.repo.RespHealthCheckRepo;

import java.time.LocalDateTime;

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
    private DeviceHealthCheckFeignService deviceHealthCheckFeignService;
    private Converter<RespHC, RespHealthCheckDO> respHCRespHealthCheckDOConverter;

    public HealthService(RespHealthCheckRepo respHealthCheckRepo, Converter<RespHC, RespHealthCheckDO> respHCRespHealthCheckDOConverter, DeviceHealthCheckFeignService deviceHealthCheckFeignService) {
        this.respHealthCheckRepo = respHealthCheckRepo;
        this.respHCRespHealthCheckDOConverter = respHCRespHealthCheckDOConverter;
        this.deviceHealthCheckFeignService = deviceHealthCheckFeignService;
    }

    @Scheduled(cron = "${purge.cron.expression}")
    public void ping3rdPartyDevices() {

        RespHC hostResp = null;
        try {
            hostResp = deviceHealthCheckFeignService.pingHostDevice();
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
