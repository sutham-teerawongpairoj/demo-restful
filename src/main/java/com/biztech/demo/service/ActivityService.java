package com.biztech.demo.service;

import com.biztech.demo.model.ActivityModel;
import com.biztech.demo.repository.ActivityRepository;
import com.biztech.demo.util.BiztechException;
import com.biztech.demo.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ActivityService {

    private static Logger logger = LogManager.getLogger(ActivityService.class);

    private ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Transactional(readOnly=false, rollbackFor = {BiztechException.class}, isolation = Isolation.READ_COMMITTED)
    public void addLog(ActivityModel model) throws  Exception {

        logger.info("Add activity logs");
        if (StringUtil.isEmpty(model.getActivity())) throw new BiztechException("1001E", "Parameter is not found [Activity:"+ model.getActivity() +"]");

        try {
            model.setCreatedDate(new Date());
            activityRepository.save(model);
            activityRepository.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BiztechException("9999E", "Other Exception");
        }
    }
}
