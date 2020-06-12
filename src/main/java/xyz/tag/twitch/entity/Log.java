package xyz.tag.twitch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.enums.ESwitch;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import static xyz.tag.twitch.constant.Constants.SCHEMA_NAME;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.entity
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 08:15
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "log", schema = SCHEMA_NAME)
public class Log {
    private static final String HIBERNATE_SEQ_LOG = "log_seq";

    @Id
    @SequenceGenerator(name = HIBERNATE_SEQ_LOG, sequenceName = SCHEMA_NAME + "." + HIBERNATE_SEQ_LOG)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = HIBERNATE_SEQ_LOG)
    private Long id;
    private ESwitch eSwitch;
    private EStatus eStatus;
    private ZonedDateTime reqDateTime;
    private ZonedDateTime respDateTime;
//    @ManyToOne
//    private Device device;

    public Log(ESwitch eSwitch, EStatus eStatus, ZonedDateTime reqDateTime, ZonedDateTime respDateTime) {
        this.eSwitch = eSwitch;
        this.eStatus = eStatus;
        this.reqDateTime = reqDateTime;
        this.respDateTime = respDateTime;
    }
}
