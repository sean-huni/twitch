package xyz.tag.twitch.dto.electrodev;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.tag.twitch.enums.DeviceType;
import xyz.tag.twitch.enums.EStatus;

import java.time.LocalDateTime;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.dto.electrodev
 * USER      : sean
 * DATE      : 24-Sun-Mar-2019
 * TIME      : 15:49
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespHC {
    private String name; // Device-Name
    @SerializedName("deviceType")
    private DeviceType deviceType; // Host-Device or Channel
    @SerializedName("eStatus")
    private EStatus eStatus;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime lDateTime;
}
