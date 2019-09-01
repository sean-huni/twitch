package xyz.tag.twitch.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import xyz.tag.twitch.dto.electrodev.RespHC;

import java.util.Collection;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.feign
 * USER      : sean
 * DATE      : 24-Sun-Mar-2019
 * TIME      : 15:58
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
public interface DeviceHealthCheck {

    @Headers("Content-Type: application/json")
    @RequestLine("GET /host")
    RespHC pingHostDevice();

    @Headers("Content-Type: application/json")
    @RequestLine("GET /channels/{id}")
    RespHC pingChannel(@Param("id") final Long id);

    @Headers("Content-Type: application/json")
    @RequestLine("GET /channels")
    Collection<RespHC> pingAllChannels();

}
