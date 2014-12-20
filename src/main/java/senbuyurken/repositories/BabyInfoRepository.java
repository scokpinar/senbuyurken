package senbuyurken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import senbuyurken.entities.BabyInfo;

/**
 * User: SametCokpinar
 * Date: 17/12/14
 * Time: 23:11
 */
public interface BabyInfoRepository extends JpaRepository<BabyInfo, Integer> {
}
