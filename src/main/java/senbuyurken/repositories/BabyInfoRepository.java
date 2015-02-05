package senbuyurken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import senbuyurken.entities.BabyInfo;

/**
 * User: SametCokpinar
 * Date: 17/12/14
 * Time: 23:11
 */
public interface BabyInfoRepository extends JpaRepository<BabyInfo, Integer> {

    @Query("SELECT bi FROM BabyInfo bi WHERE bi.user.id = :user_id")
    BabyInfo findByUser(@Param("user_id") Integer id);
}
