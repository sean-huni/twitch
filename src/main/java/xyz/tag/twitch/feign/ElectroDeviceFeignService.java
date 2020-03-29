package xyz.tag.twitch.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.tag.twitch.config.FeignConfig;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;

import static xyz.tag.twitch.constant.Constants.REST_ELECTRO_DEV_ENDPOINT;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.feign
 * USER      : sean
 * DATE      : 07-Thu-Mar-2019
 * TIME      : 19:58
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */

@FeignClient(value = "ELECTRONIC-DEVICE", url = REST_ELECTRO_DEV_ENDPOINT, configuration = FeignConfig.class)
public interface ElectroDeviceFeignService {

    @PutMapping(value = "/devices/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Resp invokeSwitch(@PathVariable("id") final Long id, @RequestBody final Req req);
}
