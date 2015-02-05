package senbuyurken.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.ParentInfo;
import senbuyurken.repositories.ParentInfoRepository;

import java.util.List;

/**
 * Created by SametCokpinar on 01/03/15.
 */
@Service
@Transactional
public class ParentInfoService {

    @Autowired
    private ParentInfoRepository parentInfoRepository;

    public List<ParentInfo> findAllBabyInfo() {
        return parentInfoRepository.findAll();
    }

    public ParentInfo save(ParentInfo parentInfo) {
        return parentInfoRepository.save(parentInfo);
    }

    public ParentInfo findByUser(Integer id) {
        return parentInfoRepository.findByUser(id);
    }

}
