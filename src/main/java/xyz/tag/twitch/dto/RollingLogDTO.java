package xyz.tag.twitch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.dto
 * USER      : sean
 * DATE      : 31-Sat-Aug-2019
 * TIME      : 10:49
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RollingLogDTO {
    private Long deviceId;
    private String item;    //Device Name or Switch Name/Location
    private String status;  //Online, Offline, Unreachable, Onn , Off
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime dateTime;
}
