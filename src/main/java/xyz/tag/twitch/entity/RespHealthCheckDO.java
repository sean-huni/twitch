package xyz.tag.twitch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import xyz.tag.twitch.enums.DeviceType;
import xyz.tag.twitch.enums.EStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static xyz.tag.twitch.constant.Constants.SCHEMA_NAME;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.dto.electrodev
 * USER      : sean
 * DATE      : 24-Sun-Mar-2019
 * TIME      : 15:49
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "resp_health_check", schema = SCHEMA_NAME)
public class RespHealthCheckDO {
    private static final String HIBERNATE_SEQ_LOG = "health_seq";

    @Id
    @SequenceGenerator(name = HIBERNATE_SEQ_LOG, sequenceName = SCHEMA_NAME + "." + HIBERNATE_SEQ_LOG)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = HIBERNATE_SEQ_LOG)
    private Long id;
    private String name; // Device-Name
    private DeviceType deviceType; // Host-Device or Channel
    private EStatus eStatus;
    private LocalDateTime lDateTime;
}
