package xyz.tag.twitch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.tag.twitch.entity.RespHealthCheckDO;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.repo
 * USER      : sean
 * DATE      : 24-Sun-Mar-2019
 * TIME      : 23:52
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Repository
public interface RespHealthCheckRepo extends JpaRepository<RespHealthCheckDO, Long> {

}
