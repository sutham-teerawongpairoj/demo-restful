package com.biztech.demo.service;

import com.biztech.demo.Application;
import com.biztech.demo.model.ActivityModel;
import com.biztech.demo.model.MemberModel;
import com.biztech.demo.object.RequestObject;
import com.biztech.demo.util.BiztechException;
import com.biztech.demo.util.DateUtil;
import com.biztech.demo.util.StringUtil;
import com.google.gson.Gson;
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
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {

    private static Logger logger = LogManager.getLogger(MemberServiceTest.class);

    @Autowired
    private MemberService memberService;

    @PersistenceContext
    private EntityManager entityManager;

    private Gson gson = new Gson();

    @Before
    public void init() {}

    private void checkActivity(RequestObject requestObject) throws Exception {

        // Mapping reqeust body fto request activity
        ActivityModel actRequest = actRequest = (ActivityModel) StringUtil.copy(requestObject.getBody(), new ActivityModel());

        // check activity
        List<ActivityModel> acts = entityManager.createQuery("select a from ActivityModel a where a.activity = :activity")
                .setParameter("activity", actRequest.getActivity())
                .getResultList();
        assertEquals(1, acts.size());
        assertEquals(actRequest.getActivity(), acts.get(0).getActivity());
        assertEquals(DateUtil.DateToString(new Date(), "dd/MM/yyyy", Locale.ENGLISH), DateUtil.DateToString(DateUtil.DefaultStringToDate(acts.get(0).getCreatedDate()), "dd/MM/YYYY", Locale.ENGLISH));
    }

    // abnormalcase00 : Don't set activity
    @Test
    public void abnormalcase00() throws Exception {

        String requestJSON = "{\"header\":{},\"body\":{}}";

        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        // Mapping reqeust body fto request activity
        ActivityModel actRequest = new ActivityModel();
        actRequest = (ActivityModel) StringUtil.copy(requestObject.getBody(), new ActivityModel());

        // check activity
        List<ActivityModel> acts = entityManager.createQuery("select a from ActivityModel a where a.activity = :activity")
                .setParameter("activity", actRequest.getActivity())
                .getResultList();
        assertEquals(0, acts.size());
    }

    // normalcase01 : find all is not found
    @Test
    public void normalcase01() throws Exception {

        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest01\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        // check member
        List<MemberModel> models = memberService.findAllMember(requestObject);
        assertEquals(0, models.size());

        // check Activity
        this.checkActivity(requestObject);
    }

    // normalcase02 : find all is found 2 rec.
    @Test
    public void normalcase02() throws Exception {

        // prepare member for this case
        MemberModel model02_01 = new MemberModel();
        model02_01.setUserId("Normal01_01");
        model02_01.setName("Name01_01");
        model02_01.setSurname("Surname01_01");
        model02_01.setCreatedDate(new Date());
        entityManager.persist(model02_01);
        entityManager.flush();

        MemberModel model02_02 = new MemberModel();
        model02_02.setUserId("Normal01_02");
        model02_02.setName("Name01_02");
        model02_02.setSurname("Surname01_02");
        model02_02.setCreatedDate(new Date());
        entityManager.persist(model02_02);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest02\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        List<MemberModel> models = memberService.findAllMember(requestObject);

        // chcek model
        assertEquals(2, models.size());
        assertEquals(model02_01.toString(), models.get(0).toString());
        assertEquals(model02_02.toString(), models.get(1).toString());

        // check Activity
        this.checkActivity(requestObject);
    }

    // normal03 findByName is not found
    @Test
    public void normalcase03() throws Exception {

        MemberModel model03_01 = new MemberModel();
        model03_01.setUserId("Normal03-01");
        model03_01.setName("Name03-01");
        model03_01.setSurname("Surname03-01");
        model03_01.setCreatedDate(new Date());
        entityManager.persist(model03_01);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest03\",\"name\":\"ind\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        // chcek model
        List<MemberModel> models = memberService.findByName(requestObject);
        assertEquals(0, models.size());

        // Mapping reqeust body fto request activity
        ActivityModel actRequest = new ActivityModel();
        actRequest = (ActivityModel) StringUtil.copy(requestObject.getBody(), new ActivityModel());

        // check Activity
        this.checkActivity(requestObject);
    }

    // normal04 findByName is found 2 rec.
    @Test
    public void normalcase04() throws Exception {

        MemberModel model04_01 = new MemberModel();
        model04_01.setUserId("Normal04-01");
        model04_01.setName("Find04-01");
        model04_01.setSurname("Surname04-01");
        model04_01.setCreatedDate(new Date());
        entityManager.persist(model04_01);
        entityManager.flush();

        MemberModel model04_02 = new MemberModel();
        model04_02.setUserId("Normal04-02");
        model04_02.setName("Find04-02");
        model04_02.setSurname("Surname04-02");
        model04_02.setCreatedDate(new Date());
        entityManager.persist(model04_02);
        entityManager.flush();

        MemberModel model03_02 = new MemberModel();
        model03_02.setUserId("Normal03-02");
        model03_02.setName("Name03-02");
        model03_02.setSurname("Surname03-02");
        model03_02.setCreatedDate(new Date());
        entityManager.persist(model03_02);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest04\",\"name\":\"ind\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        // check member
        List<MemberModel> models = memberService.findByName(requestObject);
        assertEquals(2, models.size());
        assertEquals(model04_01.toString(), models.get(0).toString());
        assertEquals(model04_02.toString(), models.get(1).toString());

        // check Activity
        this.checkActivity(requestObject);
    }

    // normal05 findBySurname is not found
    @Test
    public void normalcase05() throws Exception {

        MemberModel model05_01 = new MemberModel();
        model05_01.setUserId("Normal05-01");
        model05_01.setName("Name05-01");
        model05_01.setSurname("Surname05-01");
        model05_01.setCreatedDate(new Date());
        entityManager.persist(model05_01);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest05\",\"surname\":\"ind\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        MemberModel modelSearch = new MemberModel();
        modelSearch.setSurname("ind");

        List<MemberModel> models = memberService.findBySurname(requestObject);

        // check member
        assertEquals(0, models.size());

        // check Activity
        this.checkActivity(requestObject);
    }

    // normal06 findBySurname is found 2 rec.
    @Test
    public void normalcase06() throws Exception {

        MemberModel model06_01 = new MemberModel();
        model06_01.setUserId("Normal06-01");
        model06_01.setName("Name06-01");
        model06_01.setSurname("Find06-01");
        model06_01.setCreatedDate(new Date());
        entityManager.persist(model06_01);
        entityManager.flush();

        MemberModel model06_02 = new MemberModel();
        model06_02.setUserId("Normal06-02");
        model06_02.setName("Name06-02");
        model06_02.setSurname("Find06-02");
        model06_02.setCreatedDate(new Date());
        entityManager.persist(model06_02);
        entityManager.flush();

        MemberModel model06_03 = new MemberModel();
        model06_03.setUserId("Normal06-03");
        model06_03.setName("Name-03");
        model06_03.setSurname("Surname06-03");
        model06_03.setCreatedDate(new Date());
        entityManager.persist(model06_03);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest06\",\"surname\":\"ind\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        // check member
        List<MemberModel> models = memberService.findBySurname(requestObject);
        assertEquals(2, models.size());
        assertEquals(model06_01.toString(), models.get(0).toString());
        assertEquals(model06_02.toString(), models.get(1).toString());

        // check activity
        this.checkActivity(requestObject);
    }

    // abnormacase 07 : findByIdMember is not found
    @Test
    public void abnormalcase07() throws Exception {

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest07\",\"id\":1000}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        // check member
        try {
            MemberModel model = memberService.findByIdMember(requestObject);
        } catch (BiztechException e) {

            // Mapping reqeust body fto request activity
            MemberModel modelRequest = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            assertEquals("2001E", e.getExceptionCode());
            assertEquals("Data is not exist from db [id:"+ modelRequest.getId() +"]", e.getExceptionMessage());
        }

        // check activity
        this.checkActivity(requestObject);
    }

    // normacase 08 : findByIdMember is found
    @Test
    public void normalcase08() throws Exception {

        MemberModel model08 = new MemberModel();
        model08 .setUserId("UserId08");
        model08.setName("Name08");
        model08.setSurname("Surname08");
        model08.setCreatedDate(new Date());
        entityManager.persist(model08);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest07\",\"id\":"+model08.getId()+"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        // check member
        MemberModel model = memberService.findByIdMember(requestObject);
        assertEquals(model08.toString(), model.toString());

        // check activity
        this.checkActivity(requestObject);

    }

    // normalcase09 : add new member is success
    @Test
    public void normalcase09() throws Exception {

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest09\",\"userId\":\"Normal09\",\"name\":\"Name09\",\"surname\":\"Surname09\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        MemberModel memberAdded = memberService.addMember(requestObject);

        // check member
        MemberModel modelResult = new MemberModel();
        modelResult.setId(memberAdded.getId());
        modelResult = entityManager.getReference(MemberModel.class, memberAdded.getId());

        assertEquals(memberAdded.toString(), modelResult.toString());

        // check activity
        this.checkActivity(requestObject);
    }

    // abnormalcase10 : add new member is exception("Parameter is not found [userId:"+ member.getUserId() +"]"
    @Test
    public void abnormalcase10() throws Exception {

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest10\",\"name\":\"Name10\",\"surname\":\"Surname10\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        try {
            memberService.addMember(requestObject);
        } catch (BiztechException e) {

            // Mapping reqeust body fto request activity
            MemberModel modelRequest = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            assertEquals("1001E", e.getExceptionCode());
            assertEquals("Parameter is not found [userId:"+ modelRequest.getUserId() +"]", e.getExceptionMessage());
        }

        // check activity
        this.checkActivity(requestObject);
    }

    // abnormalcas11 : add new member is exception("Parameter is not found [name:"+ member.getName() +"]"
    @Test
    public void abnormalcase11() throws Exception {

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest11\",\"userId\":\"Abnormal11\",\"surname\":\"Surname11\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        try {
            memberService.addMember(requestObject);
        } catch (BiztechException e) {

            // Mapping reqeust body fto request activity
            MemberModel modelRequest = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            assertEquals("1001E", e.getExceptionCode());
            assertEquals("Parameter is not found [name:"+ modelRequest.getName() +"]", e.getExceptionMessage());
        }

        // check activity
        this.checkActivity(requestObject);
    }

    // abnormalcase12 : add new member is exception("Parameter is not found [surname:"+ member.getSurname() +"]"
    @Test
    public void abnormalcase12() throws Exception {

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest12\",\"userId\":\"Abnormal12\",\"name\":\"Name12\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        try {
            memberService.addMember(requestObject);
        } catch (BiztechException e) {

            // Mapping reqeust body fto request activity
            MemberModel modelRequest = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            assertEquals("1001E", e.getExceptionCode());
            assertEquals("Parameter is not found [surname:"+ modelRequest.getSurname() +"]", e.getExceptionMessage());
        }

        // check activity
        this.checkActivity(requestObject);
    }

    // normalcase13 : update member success
    @Test
    public void normalcase13() throws Exception {

        MemberModel model13_01 = new MemberModel();
        model13_01.setUserId("Normal13_01");
        model13_01.setName("Name13_01");
        model13_01.setSurname("Surname13_01");
        model13_01.setCreatedDate(new Date());
        entityManager.persist(model13_01);
        entityManager.flush();

        MemberModel model13_02 = new MemberModel();
        model13_02.setUserId("Normal13_02");
        model13_02.setName("Name13_02");
        model13_02.setSurname("Surname13_02");
        model13_02.setCreatedDate(new Date());
        entityManager.persist(model13_02);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest13\",\"id\":"+model13_01.getId()+",\"userId\":\"Normal13_01\",\"name\":\"Name13_01\",\"surname\":\"Surname13_01\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        MemberModel memberRequest = memberService.updateMember(requestObject);

        MemberModel modelDB13_01 = new MemberModel();
        modelDB13_01.setId(model13_01.getId());
        modelDB13_01 = entityManager.getReference(MemberModel.class, model13_01.getId());

        MemberModel modelDB13_02 = new MemberModel();
        modelDB13_02.setId(model13_02.getId());
        modelDB13_02 = entityManager.getReference(MemberModel.class, model13_02.getId());

        // check
        assertEquals(memberRequest.toString(), modelDB13_01.toString());
        assertEquals(model13_02.toString(), modelDB13_02.toString());

        // check activity
        this.checkActivity(requestObject);

    }

    // abnormalcase14 : update member is exception("1001E", "Parameter is not found [id:"+ member.getId() +"]"
    @Test
    public void abnormalcase14() throws Exception {

        MemberModel model14 = new MemberModel();
        model14.setUserId("Abnormal14");
        model14.setName("Name14");
        model14.setSurname("Surname14");
        model14.setCreatedDate(new Date());
        entityManager.persist(model14);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest14\",\"id\":0,\"userId\":\"Normal14_UP\",\"name\":\"Name14_UP\",\"surname\":\"Surname14_UP\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        logger.info("Body : "+ requestObject.getBody());

        try {

            memberService.updateMember(requestObject);
        } catch (BiztechException e) {

            // Mapping reqeust body fto request activity
            MemberModel modelRequest = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            assertEquals("1001E", e.getExceptionCode());
            assertEquals("Parameter is not found [id:"+ modelRequest.getId() +"]", e.getExceptionMessage());
        }

        // check data on db doesn't update
        MemberModel modelDB14 = new MemberModel();
        modelDB14.setId(model14.getId());
        modelDB14 = entityManager.getReference(MemberModel.class, model14.getId());
        assertEquals(model14.toString(), modelDB14.toString());

        // check activity
        this.checkActivity(requestObject);
    }

    // abnormalcase15 : update member is exception("1001E", "Parameter is not found [userId:"+ member.getUserId() +"]"
    @Test
    public void abnormalcase15() throws Exception {

        MemberModel model15 = new MemberModel();
        model15.setUserId("Abnormal15");
        model15.setName("Name15");
        model15.setSurname("Surname15");
        model15.setCreatedDate(new Date());
        entityManager.persist(model15);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest15\",\"id\":"+model15.getId()+",\"name\":\"Name15_UP\",\"surname\":\"Surname15_UP\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());

        try {

            memberService.updateMember(requestObject);
        } catch (BiztechException e) {

            // Mapping reqeust body fto request activity
            MemberModel modelRequest = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            assertEquals("1001E", e.getExceptionCode());
            assertEquals("Parameter is not found [userId:"+ modelRequest.getUserId() +"]", e.getExceptionMessage());
        }

        // check data on db doesn't update
        MemberModel modelDB15 = new MemberModel();
        modelDB15.setId(model15.getId());
        modelDB15 = entityManager.getReference(MemberModel.class, model15.getId());
        assertEquals(model15.toString(), modelDB15.toString());

        // check activity
        this.checkActivity(requestObject);
    }

    // abnormalcase16 : update member is exception("1001E", "Parameter is not found [name:"+ member.getName() +"]"
    @Test
    public void abnormalcase16() throws Exception {

        MemberModel model16 = new MemberModel();
        model16.setUserId("Abnormal16");
        model16.setName("Name16");
        model16.setSurname("Surname16");
        model16.setCreatedDate(new Date());
        entityManager.persist(model16);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest16\",\"id\":"+model16.getId()+",\"userId\":\"Abnormal16_UP\",\"surname\":\"Surname16_UP\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());

        try {

            memberService.updateMember(requestObject);
        } catch (BiztechException e) {

            // Mapping reqeust body fto request activity
            MemberModel modelRequest = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            assertEquals("1001E", e.getExceptionCode());
            assertEquals("Parameter is not found [name:"+ modelRequest.getName() +"]", e.getExceptionMessage());
        }

        // check data on db doesn't update
        MemberModel modelDB16 = new MemberModel();
        modelDB16.setId(model16.getId());
        modelDB16 = entityManager.getReference(MemberModel.class, model16.getId());
        assertEquals(model16.toString(), modelDB16.toString());

        // check activity
        this.checkActivity(requestObject);
    }

    // abnormalcase17 : update member is exception("1001E", "Parameter is not found [surname:"+ member.getSurname() +"]"
    @Test
    public void abnormalcase17() throws Exception {

        MemberModel model17 = new MemberModel();
        model17.setUserId("Abnormal17");
        model17.setName("Name17");
        model17.setSurname("Surname17");
        model17.setCreatedDate(new Date());
        entityManager.persist(model17);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest17\",\"id\":"+model17.getId()+",\"userId\":\"Abnormal17_UP\",\"name\":\"Name17_UP\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());

        try {

            memberService.updateMember(requestObject);
        } catch (BiztechException e) {

            // Mapping reqeust body fto request activity
            MemberModel modelRequest = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            assertEquals("1001E", e.getExceptionCode());
            assertEquals("Parameter is not found [surname:"+ modelRequest.getSurname() +"]", e.getExceptionMessage());
        }

        // check data on db doesn't update
        MemberModel modelDB17 = new MemberModel();
        modelDB17.setId(model17.getId());
        modelDB17 = entityManager.getReference(MemberModel.class, model17.getId());
        assertEquals(model17.toString(), modelDB17.toString());

        // check activity
        this.checkActivity(requestObject);
    }

    // normalcase 18 : delete member is success
    @Test
    public void normalcase18() throws Exception {

        MemberModel model18 = new MemberModel();
        model18.setUserId("Normal18");
        model18.setName("Name18");
        model18.setSurname("Surname18");
        model18.setCreatedDate(new Date());
        entityManager.persist(model18);
        entityManager.flush();

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest18\",\"id\":"+model18.getId()+"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());

        memberService.deleteMember(requestObject);

        try {
            MemberModel model = new MemberModel();
            model = entityManager.getReference(MemberModel.class, model18.getId());
        } catch (Exception e) {
            assertEquals("Unable to find com.biztech.demo.model.MemberModel with id "+ model18.getId(), e.getMessage());
        }

        // check activity
        this.checkActivity(requestObject);
    }

    // abnormalcase 19 : delete member  is exception("Parameter is not found [userId:"+ member.getId() +"]"
    @Test
    public void abnormalcase19() throws Exception {

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest19\"}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());

        try {
            memberService.deleteMember(requestObject);
        } catch (BiztechException e) {

            // Mapping reqeust body fto request activity
            MemberModel modelRequest = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            assertEquals("1001E", e.getExceptionCode());
            assertEquals("Parameter is not found [id:"+ modelRequest.getId() +"]", e.getExceptionMessage());
        }

        // check activity
        this.checkActivity(requestObject);
    }

    // abnormalcase 20 : delete member  is exception(
    @Test
    public void abnormalcase20() throws Exception {

        // input
        String requestJSON = "{\"header\":{},\"body\":{\"activity\":\"unittest20\",\"id\":1000}}";

        // Mapping request json to request object
        RequestObject requestObject = gson.fromJson(requestJSON, new RequestObject().getClass());
        try {
            memberService.deleteMember(requestObject);
        } catch (BiztechException e) {

            // Mapping reqeust body fto request activity
            MemberModel modelRequest = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            assertEquals("2001E", e.getExceptionCode());
            assertEquals("Data is not exist from db [id:"+ modelRequest.getId() +"]", e.getExceptionMessage());
        }

        // check activity
        this.checkActivity(requestObject);
    }
}
