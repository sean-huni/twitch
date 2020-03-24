package xyz.tag.twitch.config;

import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.tag.twitch.util.CustomGsonDecoder;


@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    Encoder encoder() {
        return new GsonEncoder();
    }


    @Bean
    Decoder decoder() {
        return new CustomGsonDecoder();
    }

}
