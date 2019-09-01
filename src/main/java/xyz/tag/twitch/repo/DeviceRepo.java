package xyz.tag.twitch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.tag.twitch.entity.Device;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.repo
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 08:57
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Repository
public interface DeviceRepo extends JpaRepository<Device, Long> {
    Device findByLogsId(Long id);
}
