package com.biztech.demo.service;

import com.biztech.demo.constants.GlobalConstant;
import com.biztech.demo.model.ActivityModel;
import com.biztech.demo.repository.ActivityRepository;
import com.biztech.demo.util.BiztechException;
import com.biztech.demo.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (StringUtil.isEmpty(model.getActivity()))
            throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_PARAMETER_NOT_FOUND, GlobalConstant.ERROR_MSG_PARAMETER_NOT_FOUND, null, "activity", model.getActivity());

        try {
            model.setCreatedDate(new Date());
            activityRepository.save(model);
            activityRepository.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_OTHER_EXCEPTION, GlobalConstant.ERROR_MSG_OTHER_EXCEPTION, e);
        }
    }
}
