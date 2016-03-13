package senbuyurken.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.DiaryEntry;
import senbuyurken.repositories.DiaryEntryRepository;

import java.util.List;

/**
 * User: SametCokpinar
 * Date: 25/12/14
 * Time: 22:37
 */
@Service
@Transactional
public class DiaryEntryService {

    @Autowired
    private DiaryEntryRepository diaryEntryRepository;

    public List<DiaryEntry> findAllDiaryEntry() {
        return diaryEntryRepository.findAll();
    }

    public DiaryEntry save(DiaryEntry diaryEntry) {
        return diaryEntryRepository.save(diaryEntry);
    }

    public List<DiaryEntry> findByEmail(String email) {
        return diaryEntryRepository.findByEmail(email);
    }

    public void delete(Integer entryId) {
        diaryEntryRepository.delete(entryId);
    }

}
