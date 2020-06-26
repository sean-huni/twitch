package xyz.tag.twitch.controller.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
class CustomErrorResponse {
    private final String errorMsg;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime timestamp;

    CustomErrorResponse(String errorMsg, LocalDateTime timestamp) {
        this.errorMsg = errorMsg;
        this.timestamp = timestamp;
    }
}
