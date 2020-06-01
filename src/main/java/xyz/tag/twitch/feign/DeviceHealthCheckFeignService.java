package xyz.tag.twitch.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.tag.twitch.config.FeignConfig;
import xyz.tag.twitch.dto.electrodev.RespHC;

import java.util.Collection;

import static xyz.tag.twitch.constant.Constants.REST_ELECTRO_DEV_ENDPOINT;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.feign
 * USER      : sean
 * DATE      : 24-Sun-Mar-2019
 * TIME      : 15:58
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */

@FeignClient(value = "DEVICE-HEALTH-CHECK", url = REST_ELECTRO_DEV_ENDPOINT, configuration = FeignConfig.class)
public interface DeviceHealthCheckFeignService {

    @GetMapping(value = "/host", consumes = MediaType.APPLICATION_JSON_VALUE)
    RespHC pingHostDevice();

    @GetMapping(value = "/channels/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    RespHC pingChannel(@PathVariable("id") final Long id);

    @GetMapping(value = "/channels", consumes = MediaType.APPLICATION_JSON_VALUE)
    Collection<RespHC> pingAllChannels();

}
