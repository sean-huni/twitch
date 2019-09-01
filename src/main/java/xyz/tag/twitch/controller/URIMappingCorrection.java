package xyz.tag.twitch.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.controller
 * USER      : sean
 * DATE      : 20-Wed-Mar-2019
 * TIME      : 02:19
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@RestController
public class URIMappingCorrection {

    @GetMapping("/devices")
    public ResponseEntity<Object> redirectDevicesToHome(){
        return getResponseEntity();
    }

    @GetMapping("/home")
    public ResponseEntity<Object> redirectHomeToHome(){
        return getResponseEntity();
    }

    private ResponseEntity<Object> getResponseEntity() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
