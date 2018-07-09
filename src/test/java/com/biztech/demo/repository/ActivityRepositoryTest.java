package com.biztech.demo.repository;

import com.biztech.demo.Application;
import com.biztech.demo.model.ActivityModel;
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
public class ActivityRepositoryTest {

    private static Logger logger = LogManager.getLogger(ActivityRepositoryTest.class);

    @Autowired
    private ActivityRepository activityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void init() {

    }

    @Test
    public void addLogs() {

        ActivityModel model = new ActivityModel();
        model.setActivity("TestActivityCase01");
        model.setCreatedDate(new Date());
        activityRepository.save(model);

        logger.info("model id : "+ model.getId() +", activity : "+ model.getActivity()+", createdDate : "+ model.getCreatedDate());

        ActivityModel modelResult = new ActivityModel();
        modelResult.setId(model.getId());
        modelResult = entityManager.getReference(ActivityModel.class, modelResult.getId());

        logger.info("model result id : "+ modelResult.getId() +", activity : "+ modelResult.getActivity()+", createdDate : "+ modelResult.getCreatedDate());

        assertEquals(model.getId(), modelResult.getId());
        assertEquals(model.getActivity(), modelResult.getActivity());
        assertEquals(model.getCreatedDate(), modelResult.getCreatedDate());
    }
}
