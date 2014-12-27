package senbuyurken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import senbuyurken.entities.DiaryEntry;

/**
 * User: SametCokpinar
 * Date: 20/12/14
 * Time: 21:22
 */
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Integer> {
}
