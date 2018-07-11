package com.biztech.demo.apicontroller;

import com.biztech.demo.model.ActivityModel;
import com.biztech.demo.model.MemberModel;
import com.biztech.demo.object.RequestObject;
import com.biztech.demo.object.ResponseObject;
import com.biztech.demo.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.when;
import static org.mockito.BDDMockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberController memberController;

    @Test
    public void createMember() throws Exception {

        MemberModel memModel = new MemberModel();
        memModel.setId(1);
        memModel.setName("Name01");
        memModel.setSurname("Surname01");
        memModel.setUserId("UserId01");
        memModel.setCreatedDate(DateUtil.DefaultStringToDate("01/07/2018 08:00:00"));

        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponseCode("0000I");
        responseObject.setResponseDesc("SUCCESS");
        responseObject.setResponseBody(memModel);

        when(memberController.createMember(any(RequestObject.class))).thenReturn(new ResponseEntity<>(responseObject, HttpStatus.CREATED));

        mvc.perform(post("/api/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"header\":{},\"body\":{\"activity\":\"createMember\",\"userId\":\"Normal01\",\"name\":\"Name01\",\"surname\":\"Surname01\"}}")
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responseCode", is("0000I")))
                .andExpect(jsonPath("$.responseDesc", is("SUCCESS")))
                .andExpect(jsonPath("$.responseBody.id", is(1)))
                .andExpect(jsonPath("$.responseBody.name", is("Name01")))
                .andExpect(jsonPath("$.responseBody.surname", is("Surname01")))
                .andExpect(jsonPath("$.responseBody.userId", is("UserId01")))
                .andExpect(jsonPath("$.responseBody.createdDate", is("01/07/2018 08:00:00")))
                ;

        ArgumentCaptor<RequestObject> requestCaptor = ArgumentCaptor.forClass(RequestObject.class);
        verify(memberController, times(1)).createMember(requestCaptor.capture());
        verifyNoMoreInteractions(memberController);
    }

    @Test
    public void updateMember() throws Exception {

        MemberModel memModel = new MemberModel();
        memModel.setId(1);
        memModel.setName("Name01_UP");
        memModel.setSurname("Surname01_UP");
        memModel.setUserId("UserId01_UP");
        memModel.setCreatedDate(DateUtil.DefaultStringToDate("01/07/2018 08:00:00"));

        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponseCode("0000I");
        responseObject.setResponseDesc("SUCCESS");
        responseObject.setResponseBody(memModel);

        when(memberController.updateMember(any(RequestObject.class))).thenReturn(new ResponseEntity<>(responseObject, HttpStatus.OK));

        mvc.perform(put("/api/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"header\":{},\"body\":{\"activity\":\"updateMember\",\"id\":1,\"userId\":\"UserId01_UP\",\"name\":\"Name01_UP\",\"surname\":\"Surname01_UP\"}}")
               )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responseCode", is("0000I")))
                .andExpect(jsonPath("$.responseDesc", is("SUCCESS")))
                .andExpect(jsonPath("$.responseBody.id", is(1)))
                .andExpect(jsonPath("$.responseBody.name", is("Name01_UP")))
                .andExpect(jsonPath("$.responseBody.surname", is("Surname01_UP")))
                .andExpect(jsonPath("$.responseBody.userId", is("UserId01_UP")))
                .andExpect(jsonPath("$.responseBody.createdDate", is("01/07/2018 08:00:00")))
        ;

        ArgumentCaptor<RequestObject> requestCaptor = ArgumentCaptor.forClass(RequestObject.class);
        verify(memberController, times(1)).updateMember(requestCaptor.capture());
        verifyNoMoreInteractions(memberController);
    }

    @Test
    public void deleteMember() throws Exception {

        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponseCode("0000I");
        responseObject.setResponseDesc("SUCCESS");

        when(memberController.deleteMember(any(RequestObject.class))).thenReturn(new ResponseEntity<>(responseObject, HttpStatus.OK));

        mvc.perform(delete("/api/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"header\":{},\"body\":{\"activity\":\"deleteMember\",\"id\":1}}")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responseCode", is("0000I")))
                .andExpect(jsonPath("$.responseDesc", is("SUCCESS")))
                .andExpect(jsonPath("$.responseBody", nullValue()))
        ;

        ArgumentCaptor<RequestObject> requestCaptor = ArgumentCaptor.forClass(RequestObject.class);
        verify(memberController, times(1)).deleteMember(requestCaptor.capture());
        verifyNoMoreInteractions(memberController);
    }

    @Test
    public void findAllMember() throws Exception {

        ActivityModel actSearch = new ActivityModel();
        actSearch.setActivity("find all member");

        List<MemberModel> memModels = new ArrayList<>();
        MemberModel memModel = new MemberModel();
        memModel.setId(1);
        memModel.setName("Name01");
        memModel.setSurname("Surname01");
        memModel.setUserId("UserId01");
        memModel.setCreatedDate(DateUtil.DefaultStringToDate("01/07/2018 08:00:00"));
        memModels.add(memModel);

        memModel = new MemberModel();
        memModel.setId(2);
        memModel.setName("Name02");
        memModel.setSurname("Surname02");
        memModel.setUserId("UserId02");
        memModel.setCreatedDate(DateUtil.DefaultStringToDate("02/07/2018 08:00:00"));
        memModels.add(memModel);

        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponseCode("0000I");
        responseObject.setResponseDesc("SUCCESS");
        responseObject.setResponseBody(memModels);

        when(memberController.findAllMember(any(RequestObject.class))).thenReturn(new ResponseEntity<>(responseObject, HttpStatus.OK));

        mvc.perform(post("/api/findAll").accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"header\":{},\"body\":{\"activity\":\"findAllMember\"}}")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responseCode", is("0000I")))
                .andExpect(jsonPath("$.responseDesc", is("SUCCESS")))
                .andExpect(jsonPath("$.responseBody", hasSize(2)))
                .andExpect(jsonPath("$.responseBody[0].id", is(1)))
                .andExpect(jsonPath("$.responseBody[0].name", is("Name01")))
                .andExpect(jsonPath("$.responseBody[0].surname", is("Surname01")))
                .andExpect(jsonPath("$.responseBody[0].userId", is("UserId01")))
                .andExpect(jsonPath("$.responseBody[0].createdDate", is("01/07/2018 08:00:00")))
                .andExpect(jsonPath("$.responseBody[1].id", is(2)))
                .andExpect(jsonPath("$.responseBody[1].name", is("Name02")))
                .andExpect(jsonPath("$.responseBody[1].surname", is("Surname02")))
                .andExpect(jsonPath("$.responseBody[1].userId", is("UserId02")))
                .andExpect(jsonPath("$.responseBody[1].createdDate", is("02/07/2018 08:00:00")))
        ;

        ArgumentCaptor<RequestObject> requestCaptor = ArgumentCaptor.forClass(RequestObject.class);
        verify(memberController, times(1)).findAllMember(requestCaptor.capture());
        verifyNoMoreInteractions(memberController);

    }

    @Test
    public void findByNameMember() throws Exception {

        ActivityModel actSearch = new ActivityModel();
        actSearch.setActivity("find all member");

        List<MemberModel> memModels = new ArrayList<>();
        MemberModel memModel = new MemberModel();
        memModel.setId(1);
        memModel.setName("Find01");
        memModel.setSurname("Surname01");
        memModel.setUserId("UserId01");
        memModel.setCreatedDate(DateUtil.DefaultStringToDate("01/07/2018 08:00:00"));
        memModels.add(memModel);

        memModel = new MemberModel();
        memModel.setId(2);
        memModel.setName("Find02");
        memModel.setSurname("Surname02");
        memModel.setUserId("UserId02");
        memModel.setCreatedDate(DateUtil.DefaultStringToDate("02/07/2018 08:00:00"));
        memModels.add(memModel);

        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponseCode("0000I");
        responseObject.setResponseDesc("SUCCESS");
        responseObject.setResponseBody(memModels);

        when(memberController.findByNameMember(any(RequestObject.class))).thenReturn(new ResponseEntity<>(responseObject, HttpStatus.OK));

        mvc.perform(post("/api/findByName").accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"header\":{},\"body\":{\"activity\":\"findByNameMember\",\"name\":\"ind\"}}")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responseCode", is("0000I")))
                .andExpect(jsonPath("$.responseDesc", is("SUCCESS")))
                .andExpect(jsonPath("$.responseBody", hasSize(2)))
                .andExpect(jsonPath("$.responseBody[0].id", is(1)))
                .andExpect(jsonPath("$.responseBody[0].name", is("Find01")))
                .andExpect(jsonPath("$.responseBody[0].surname", is("Surname01")))
                .andExpect(jsonPath("$.responseBody[0].userId", is("UserId01")))
                .andExpect(jsonPath("$.responseBody[0].createdDate", is("01/07/2018 08:00:00")))
                .andExpect(jsonPath("$.responseBody[1].id", is(2)))
                .andExpect(jsonPath("$.responseBody[1].name", is("Find02")))
                .andExpect(jsonPath("$.responseBody[1].surname", is("Surname02")))
                .andExpect(jsonPath("$.responseBody[1].userId", is("UserId02")))
                .andExpect(jsonPath("$.responseBody[1].createdDate", is("02/07/2018 08:00:00")))
        ;

        ArgumentCaptor<RequestObject> requestCaptor = ArgumentCaptor.forClass(RequestObject.class);
        verify(memberController, times(1)).findByNameMember(requestCaptor.capture());
        verifyNoMoreInteractions(memberController);

    }

    @Test
    public void findBySurnameMember() throws Exception {

        ActivityModel actSearch = new ActivityModel();
        actSearch.setActivity("find all member");

        List<MemberModel> memModels = new ArrayList<>();
        MemberModel memModel = new MemberModel();
        memModel.setId(1);
        memModel.setName("Name01");
        memModel.setSurname("Find01");
        memModel.setUserId("UserId01");
        memModel.setCreatedDate(DateUtil.DefaultStringToDate("01/07/2018 08:00:00"));
        memModels.add(memModel);

        memModel = new MemberModel();
        memModel.setId(2);
        memModel.setName("Name02");
        memModel.setSurname("Find02");
        memModel.setUserId("UserId02");
        memModel.setCreatedDate(DateUtil.DefaultStringToDate("02/07/2018 08:00:00"));
        memModels.add(memModel);

        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponseCode("0000I");
        responseObject.setResponseDesc("SUCCESS");
        responseObject.setResponseBody(memModels);

        when(memberController.findBySurnameMember(any(RequestObject.class))).thenReturn(new ResponseEntity<>(responseObject, HttpStatus.OK));

        mvc.perform(post("/api/findBySurname").accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"header\":{},\"body\":{\"activity\":\"findBySurnameMember\",\"surname\":\"ind\"}}")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responseCode", is("0000I")))
                .andExpect(jsonPath("$.responseDesc", is("SUCCESS")))
                .andExpect(jsonPath("$.responseBody", hasSize(2)))
                .andExpect(jsonPath("$.responseBody[0].id", is(1)))
                .andExpect(jsonPath("$.responseBody[0].name", is("Name01")))
                .andExpect(jsonPath("$.responseBody[0].surname", is("Find01")))
                .andExpect(jsonPath("$.responseBody[0].userId", is("UserId01")))
                .andExpect(jsonPath("$.responseBody[0].createdDate", is("01/07/2018 08:00:00")))
                .andExpect(jsonPath("$.responseBody[1].id", is(2)))
                .andExpect(jsonPath("$.responseBody[1].name", is("Name02")))
                .andExpect(jsonPath("$.responseBody[1].surname", is("Find02")))
                .andExpect(jsonPath("$.responseBody[1].userId", is("UserId02")))
                .andExpect(jsonPath("$.responseBody[1].createdDate", is("02/07/2018 08:00:00")))
        ;

        ArgumentCaptor<RequestObject> requestCaptor = ArgumentCaptor.forClass(RequestObject.class);
        verify(memberController, times(1)).findBySurnameMember(requestCaptor.capture());
        verifyNoMoreInteractions(memberController);

    }

    @Test
    public void findById() throws Exception {

        ActivityModel actSearch = new ActivityModel();
        actSearch.setActivity("find all member");

        MemberModel memModel = new MemberModel();
        memModel.setId(1);
        memModel.setName("Name01");
        memModel.setSurname("Surname01");
        memModel.setUserId("UserId01");
        memModel.setCreatedDate(DateUtil.DefaultStringToDate("01/07/2018 08:00:00"));

        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponseCode("0000I");
        responseObject.setResponseDesc("SUCCESS");
        responseObject.setResponseBody(memModel);

        when(memberController.findById(any(RequestObject.class))).thenReturn(new ResponseEntity<>(responseObject, HttpStatus.OK));

        mvc.perform(post("/api/findById").accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"header\":{},\"body\":{\"activity\":\"findById\",\"id\":1}}")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responseCode", is("0000I")))
                .andExpect(jsonPath("$.responseDesc", is("SUCCESS")))
                .andExpect(jsonPath("$.responseBody.id", is(1)))
                .andExpect(jsonPath("$.responseBody.name", is("Name01")))
                .andExpect(jsonPath("$.responseBody.surname", is("Surname01")))
                .andExpect(jsonPath("$.responseBody.userId", is("UserId01")))
                .andExpect(jsonPath("$.responseBody.createdDate", is("01/07/2018 08:00:00")))
        ;

        ArgumentCaptor<RequestObject> requestCaptor = ArgumentCaptor.forClass(RequestObject.class);
        verify(memberController, times(1)).findById(requestCaptor.capture());
        verifyNoMoreInteractions(memberController);

    }


}
