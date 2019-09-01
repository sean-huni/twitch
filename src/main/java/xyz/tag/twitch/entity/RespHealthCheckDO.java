package xyz.tag.twitch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.tag.twitch.enums.DeviceType;
import xyz.tag.twitch.enums.EStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Entity
public class RespHealthCheckDO {

    @Id
    @GeneratedValue
    private Long id;
    private String name; // Device-Name
    private DeviceType deviceType; // Host-Device or Channel
    private EStatus eStatus;
    private LocalDateTime lDateTime;
}
