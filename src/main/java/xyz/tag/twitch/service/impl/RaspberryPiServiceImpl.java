package xyz.tag.twitch.service.impl;

import feign.Feign;
import feign.RetryableException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.stereotype.Service;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;
import xyz.tag.twitch.feign.ElectroDev;
import xyz.tag.twitch.service.RaspberryPiService;

import static xyz.tag.twitch.constant.Constants.REST_ELECTRO_DEV_ENDPOINT;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.service.impl
 * USER      : sean
 * DATE      : 30-Sun-Jun-2019
 * TIME      : 10:52
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Service
public class RaspberryPiServiceImpl implements RaspberryPiService {
    private ElectroDev electroDev = Feign.builder()
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .logger(new Slf4jLogger())
            .logLevel(feign.Logger.Level.FULL)
            .target(ElectroDev.class, REST_ELECTRO_DEV_ENDPOINT);

    /**
     * @param req
     * @param channel
     * @return
     */
    public Resp invokeDeviceSwitch(Req req, long channel) throws RetryableException {
        final Resp resp = electroDev.invokeSwitch(channel, req);
        return resp;
    }
}
