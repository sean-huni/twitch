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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

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
public class Device {

    @Id
    @GeneratedValue
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
