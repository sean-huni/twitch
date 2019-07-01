package xyz.tag.twitch.service;

import feign.RetryableException;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.service
 * USER      : sean
 * DATE      : 30-Sun-Jun-2019
 * TIME      : 10:54
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
public interface RaspberryPiService {
    Resp invokeDeviceSwitch(Req req, long channel) throws RetryableException;
}
