package com.biztech.demo.repository;

import com.biztech.demo.Application;
import com.biztech.demo.model.MemberModel;
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
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberRespositoryTest {

    private static Logger logger = LogManager.getLogger(MemberRespositoryTest.class);

    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private MemberModel model01;
    private MemberModel model02;
    private MemberModel model03;
    private MemberModel model04;
    private MemberModel model05;
    private MemberModel model06;
    private MemberModel model07;

    @Before
    public void init() {

        // for test case update
        model01 = new MemberModel();
        model01.setName("NameUpdate01");
        model01.setSurname("SurnameUpdate01");
        model01.setUserId("UserIdUpdate01");
        model01.setCreatedDate(new Date());
        entityManager.persist(model01);
        entityManager.flush();

        // for test case delete
        model02 = new MemberModel();
        model02.setName("NameDelete");
        model02.setSurname("SurnameDelete");
        model02.setUserId("UserIdDelete");
        model02.setCreatedDate(new Date());
        entityManager.persist(model02);
        entityManager.flush();

        // for test case find by name
        model03 = new MemberModel();
        model03.setName("find03");
        model03.setSurname("surname03");
        model03.setUserId("userId03");
        model03.setCreatedDate(new Date());
        entityManager.persist(model03);
        entityManager.flush();

        // for test case find by name
        model04 = new MemberModel();
        model04.setName("find04");
        model04.setSurname("surname04");
        model04.setUserId("userId04");
        model04.setCreatedDate(new Date());
        entityManager.persist(model04);
        entityManager.flush();

        // for test caes find by surname
        model05 = new MemberModel();
        model05.setName("name05");
        model05.setSurname("find05");
        model05.setUserId("userId05");
        model05.setCreatedDate(new Date());
        entityManager.persist(model05);
        entityManager.flush();

        // for test case find by surname
        model06 = new MemberModel();
        model06.setName("name06");
        model06.setSurname("find06");
        model06.setUserId("userId06");
        model06.setCreatedDate(new Date());
        entityManager.persist(model06);
        entityManager.flush();

        // for test case find by id
        model07 = new MemberModel();
        model07.setName("name07");
        model07.setSurname("surname07");
        model07.setUserId("userId07");
        model07.setCreatedDate(new Date());
        entityManager.persist(model07);
        entityManager.flush();
    }

    // find all
    @Test
    public void findAllMember() {

        List<MemberModel> models = memberRepository.findAll();

        assertEquals(models.size(), 7);
        assertEquals(models.get(0).toString(), model01.toString());
        assertEquals(models.get(1).toString(), model02.toString());
        assertEquals(models.get(2).toString(), model03.toString());
        assertEquals(models.get(3).toString(), model04.toString());
        assertEquals(models.get(4).toString(), model05.toString());
        assertEquals(models.get(5).toString(), model06.toString());
        assertEquals(models.get(6).toString(), model07.toString());
    }

    // insert normalcase 01
    @Test
    public void addNewMember01() {

        MemberModel model = new MemberModel();
        model.setUserId("UserId01");
        model.setName("Name01");
        model.setSurname("Surname01");
        model.setCreatedDate(new Date());
        memberRepository.save(model);

        MemberModel result = new MemberModel();
        result = entityManager.getReference(MemberModel.class, model.getId());

        assertEquals(model.getId(), result.getId());
        assertEquals(model.getUserId(), result.getUserId());
        assertEquals(model.getName(), result.getName());
        assertEquals(model.getSurname(), result.getSurname());
        assertEquals(model.getCreatedDate(), result.getCreatedDate());
    }

    // update
    @Test
    public void updateNewModel01() {

        model01.setUserId(model01.getUserId()+"-Up01");
        model01.setName(model01.getName()+"-Up01");
        model01.setSurname(model01.getSurname()+"-Up01");
        model01.setCreatedDate(new Date());
        memberRepository.save(model01);
        logger.info("Model01 : "+ model01.toString());

        MemberModel result = new MemberModel();
        result = entityManager.getReference(MemberModel.class, model01.getId());
        logger.info("Model result : "+ result.toString());

        assertEquals(model01.getId(), result.getId());
        assertEquals(model01.getUserId(), result.getUserId());
        assertEquals(model01.getName(), result.getName());
        assertEquals(model01.getSurname(), result.getSurname());
        assertEquals(model01.getCreatedDate(), result.getCreatedDate());
    }

    // delete
    @Test
    public void deleteMember() {

        memberRepository.delete(model02);
        logger.info("Model02 : "+ model02.toString());

        MemberModel result = new MemberModel();
        try {
            result = entityManager.getReference(MemberModel.class, model02.getId());
        } catch (EntityNotFoundException e) {
            String error = "Unable to find com.biztech.demo.model.MemberModel with id "+model02.getId();
            assertEquals(e.getMessage(), error);
        }

    }

    // find by name
    @Test
    public void findByName() {

        List<MemberModel> models = memberRepository.findByName("ind");

        assertEquals(models.size(), 2);
        assertEquals(models.get(0).toString(), model03.toString());
        assertEquals(models.get(1).toString(), model04.toString());
    }

    // find by surname
    @Test
    public void findBSurname() {

        List<MemberModel> models = memberRepository.findBySurname("ind");

        assertEquals(models.size(), 2);
        assertEquals(models.get(0).toString(), model05.toString());
        assertEquals(models.get(1).toString(), model06.toString());
    }

    // find by id
    @Test
    public void findById() {

        MemberModel model = memberRepository.findById(model07.getId()).get();

        assertEquals(model.toString(), model07.toString());
    }
}
