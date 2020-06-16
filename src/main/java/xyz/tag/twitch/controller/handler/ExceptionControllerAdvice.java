package xyz.tag.twitch.controller.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.tag.twitch.exception.DeviceNotFoundException;

import java.time.LocalDateTime;

@Log4j2
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(DeviceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ResponseEntity<CustomErrorResponse> handleResourceNotFound(DeviceNotFoundException errorMsg) {
        log.error(errorMsg.getMessage(), errorMsg);
        CustomErrorResponse cer = new CustomErrorResponse(errorMsg.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(cer, HttpStatus.NOT_FOUND);
    }
}
