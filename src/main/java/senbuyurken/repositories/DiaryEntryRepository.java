package senbuyurken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import senbuyurken.entities.DiaryEntry;

import java.util.List;

/**
 * User: SametCokpinar
 * Date: 20/12/14
 * Time: 21:22
 */
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Integer> {

    @Query("SELECT de FROM DiaryEntry de WHERE de.user.email = :email order by de.diary_entry_id desc")
    List<DiaryEntry> findByEmail(@Param("email") String email);
}
