package xyz.tag.twitch.util;

import com.google.gson.*;
import feign.gson.GsonDecoder;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.util
 * USER      : sean
 * DATE      : 24-Sun-Mar-2019
 * TIME      : 22:51
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
public class CustomGsonDecoder extends GsonDecoder {

    public CustomGsonDecoder() {
        super(new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                        return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), dtf);
                    }
                }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    @Override
                    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                        return new JsonPrimitive(dtf.format(localDateTime));
                    }
                }).create());
    }
}
