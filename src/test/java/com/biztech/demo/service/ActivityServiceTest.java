package com.biztech.demo.service;


import com.biztech.demo.Application;
import com.biztech.demo.model.ActivityModel;
import com.biztech.demo.util.BiztechException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class ActivityServiceTest {

    private static Logger logger = LogManager.getLogger(ActivityServiceTest.class);

    @Autowired
    private ActivityService activityService;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void init() {

    }

    @Test
    public void addLogsNormalcase01() throws Exception {

        ActivityModel model = new ActivityModel();
        model.setActivity("TestActivityCase01");
        model.setCreatedDate(new Date());
        activityService.addLog(model);

        ActivityModel modelResult = new ActivityModel();
        modelResult.setId(model.getId());
        modelResult = entityManager.getReference(ActivityModel.class, modelResult.getId());

        assertEquals(model.toString(), modelResult.toString());
    }

    @Test
    public void addLogsAbnormalcase02() throws Exception {

        ActivityModel model = new ActivityModel();
        try {
            activityService.addLog(model);
        } catch (BiztechException e) {
            assertEquals(e.getExceptionCode(), "1001E");
            assertEquals(e.getExceptionMessage(), "Parameter is not found [activity:"+ model.getActivity() +"]");
        }
    }

}
