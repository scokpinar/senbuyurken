package senbuyurken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import senbuyurken.entities.ParentInfo;

/**
 * Created by SametCokpinar on 01/03/15.
 */
public interface ParentInfoRepository extends JpaRepository<ParentInfo, Integer> {

    @Query("SELECT pi FROM ParentInfo pi WHERE pi.user.id = :user_id")
    ParentInfo findByUser(@Param("user_id") Integer id);
}
