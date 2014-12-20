package senbuyurken.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.BabyInfo;
import senbuyurken.repositories.BabyInfoRepository;

import java.util.List;

/**
 * User: SametCokpinar
 * Date: 17/12/14
 * Time: 23:12
 */
@Service
@Transactional
public class BabyInfoService {

    @Autowired
    private BabyInfoRepository babyInfoRepository;

    public List<BabyInfo> findAllBabyInfo() {
        return babyInfoRepository.findAll();
    }

    public BabyInfo create(BabyInfo babyInfo) {
        return babyInfoRepository.save(babyInfo);
    }

}
