package xyz.tag.twitch.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static xyz.tag.twitch.constant.Constants.SCHEMA_NAME;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.entity
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 08:47
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "device", schema = SCHEMA_NAME)
public class Device {
    private static final String HIBERNATE_SEQ_DEVICE = "device_seq";

    @Id
    @SequenceGenerator(name = HIBERNATE_SEQ_DEVICE, sequenceName = SCHEMA_NAME + "." + HIBERNATE_SEQ_DEVICE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = HIBERNATE_SEQ_DEVICE)
    private Long id;
    @Getter(AccessLevel.NONE)
    private Boolean onn;
    private String location;
    private String type;
    private String channel;
    private LocalDateTime localDateTime;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Collection<Log> logs = new ArrayList<>();

    public Device(String location, Boolean onn, LocalDateTime localDateTime, String type, String channel) {
        this.location = location;
        this.onn = onn;
        this.localDateTime = localDateTime;
        this.type = type;
        this.channel = channel;
    }

    public Boolean isOnn() {
        return onn;
    }
}
