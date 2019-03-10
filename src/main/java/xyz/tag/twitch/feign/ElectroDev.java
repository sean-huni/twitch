package xyz.tag.twitch.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.feign
 * USER      : sean
 * DATE      : 07-Thu-Mar-2019
 * TIME      : 19:58
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */

public interface ElectroDev {

    @Headers("Content-Type: application/json")
    @RequestLine("PUT /device/{id}")
    Resp invokeSwitch(@Param("id") final Long id, @RequestBody final Req req);
}
