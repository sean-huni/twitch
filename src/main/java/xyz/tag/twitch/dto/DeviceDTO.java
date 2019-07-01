package xyz.tag.twitch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.dto
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 11:19
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "device")
//@JsonTypeName("device")
//@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT ,use = JsonTypeInfo.Id.NAME)
public class DeviceDTO {
    private Long id;
    @Getter(AccessLevel.NONE)
    private Boolean onn;
    private String location;
    private String type;
    private String channel;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime localDateTime;
    private Collection<LogDTO> logs;

    public Boolean isOnn() {
        return onn;
    }
}
