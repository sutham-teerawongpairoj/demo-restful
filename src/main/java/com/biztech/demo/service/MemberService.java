package com.biztech.demo.service;

import com.biztech.demo.constants.GlobalConstant;
import com.biztech.demo.model.ActivityModel;
import com.biztech.demo.model.MemberModel;
import com.biztech.demo.object.CreateMemberRequestObject;
import com.biztech.demo.object.RequestObject;
import com.biztech.demo.object.UpdateMemberRequestObject;
import com.biztech.demo.repository.MemberRepository;
import com.biztech.demo.util.BiztechException;
import com.biztech.demo.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MemberService {

    private static Logger logger = LogManager.getLogger(MemberService.class);

    private ActivityService activityService;
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(ActivityService activityService, MemberRepository memberRepository) {
        this.activityService = activityService;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly=false, rollbackFor = {BiztechException.class}, isolation = Isolation.READ_COMMITTED)
    public MemberModel addMember(CreateMemberRequestObject requestObject) throws BiztechException {

        logger.info("Add Member Service[Start]");
        MemberModel member;

        try {

            logger.info("Add Member Service[Load value to object]");
            member = (MemberModel) StringUtil.copy(requestObject, new MemberModel());
            ActivityModel activityModel = (ActivityModel) StringUtil.copy(requestObject, new ActivityModel());

            logger.info("Add Member Service[member : "+ member.toString()+"]");
            logger.info("Add Member Service[activity : "+ activityModel.toString()+"]");

            logger.info("Add Member Service[Insert activity logs]");
            activityService.addLog(activityModel);

            logger.info("Add Member Service[validate data]");
            if (StringUtil.isEmpty(member.getUserId())) // throw new BiztechException("1001E", "Parameter is not found [userId:"+ member.getUserId() +"]");
                throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_PARAMETER_NOT_FOUND, GlobalConstant.ERROR_MSG_PARAMETER_NOT_FOUND, null, "userId", member.getUserId());
            if (StringUtil.isEmpty(member.getName())) // throw new BiztechException("1001E", "Parameter is not found [name:"+ member.getName() +"]");
                throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_PARAMETER_NOT_FOUND, GlobalConstant.ERROR_MSG_PARAMETER_NOT_FOUND, null, "name", member.getName());
            if (StringUtil.isEmpty(member.getSurname())) //throw new BiztechException("1001E", "Parameter is not found [surname:"+ member.getSurname() +"]");
                throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_PARAMETER_NOT_FOUND, GlobalConstant.ERROR_MSG_PARAMETER_NOT_FOUND, null, "surname", member.getSurname());

            member.setCreatedDate(new Date());
            memberRepository.save(member);
            memberRepository.flush();
        } catch (BiztechException be) {
            throw be;
        } catch (Exception e) {
            throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_OTHER_EXCEPTION, GlobalConstant.ERROR_MSG_OTHER_EXCEPTION, e);
        }
        logger.info("Add Member Service[End]");
        return member;
    }

    @Transactional(readOnly=false, rollbackFor = {BiztechException.class}, isolation = Isolation.READ_COMMITTED)
    public MemberModel updateMember(UpdateMemberRequestObject requestObject) throws BiztechException {

        logger.info("Update Member Service[Start]");
        MemberModel member;

        try {

            logger.info("Update Member Service[Load value to object]");
            member = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            ActivityModel activityModel = (ActivityModel) StringUtil.copy(requestObject.getBody(), new ActivityModel());

            logger.info("Update Member Service[member : "+ member.toString()+"]");
            logger.info("Update Member Service[activity : "+ activityModel.toString()+"]");

            logger.info("Update Member Service[Insert activity logs]");
            activityService.addLog(activityModel);

            logger.info("Update Member Service[validate data]");
            if (member.getId()==0) // throw new BiztechException("1001E", "Parameter is not found [id:"+ member.getId() +"]");
                throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_PARAMETER_NOT_FOUND, GlobalConstant.ERROR_MSG_PARAMETER_NOT_FOUND, null, "id", String.valueOf(member.getId()));
            if (StringUtil.isEmpty(member.getUserId())) // throw new BiztechException("1001E", "Parameter is not found [userId:"+ member.getUserId() +"]");
                throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_PARAMETER_NOT_FOUND, GlobalConstant.ERROR_MSG_PARAMETER_NOT_FOUND, null, "userId", member.getUserId());
            if (StringUtil.isEmpty(member.getName())) // throw new BiztechException("1001E", "Parameter is not found [name:"+ member.getName() +"]");
                throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_PARAMETER_NOT_FOUND, GlobalConstant.ERROR_MSG_PARAMETER_NOT_FOUND, null, "name", member.getName());
            if (StringUtil.isEmpty(member.getSurname())) //throw new BiztechException("1001E", "Parameter is not found [surname:"+ member.getSurname() +"]");
                throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_PARAMETER_NOT_FOUND, GlobalConstant.ERROR_MSG_PARAMETER_NOT_FOUND, null, "surname", member.getSurname());

            logger.info("Update Member Service[validate id with db]");
            if (memberRepository.existsById(member.getId())) {
                member.setCreatedDate(new Date());
                memberRepository.save(member);
                memberRepository.flush();
            } else {
                throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_DATA_NOT_FOUND, GlobalConstant.ERROR_MSG_DATA_NOT_FOUND, null, "id", String.valueOf(member.getId()));
            }
        } catch (BiztechException be) {
            throw be;
        } catch (Exception e) {
            memberRepository.flush();
            throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_OTHER_EXCEPTION, GlobalConstant.ERROR_MSG_OTHER_EXCEPTION, e);
        }

        logger.info("Update Member Service[End]");
        return member;
    }

    @Transactional(readOnly=false, rollbackFor = {BiztechException.class}, isolation = Isolation.READ_COMMITTED)
    public MemberModel deleteMember(RequestObject requestObject) throws BiztechException {

        logger.info("Delete Member Service[Start]");
        MemberModel member;

        try {

            logger.info("Delete Member Service[Load value to object]");
            member = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            ActivityModel activityModel = (ActivityModel) StringUtil.copy(requestObject.getBody(), new ActivityModel());
            logger.info("Delete Member Service[member : "+ member.toString()+"]");
            logger.info("Delete Member Service[activity : "+ activityModel.toString()+"]");

            logger.info("Delete Member Service[Insert activity logs]");
            activityService.addLog(activityModel);

            logger.info("Delete Member Service[validate id]");
            if (member.getId()==0) // throw new BiztechException("1001E", "Parameter is not found [id:"+ member.getId() +"]");
                throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_PARAMETER_NOT_FOUND, GlobalConstant.ERROR_MSG_PARAMETER_NOT_FOUND, null, "id", String.valueOf(member.getId()));

            logger.info("Delete Member Service[validate id with db]");
            if (memberRepository.existsById(member.getId())) {
                memberRepository.delete(member);
                memberRepository.flush();
            } else {
                throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_DATA_NOT_FOUND, GlobalConstant.ERROR_MSG_DATA_NOT_FOUND, null, "id", String.valueOf(member.getId()));
            }
        } catch (BiztechException be) {
            throw be;
        } catch (Exception e) {
            throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_OTHER_EXCEPTION, GlobalConstant.ERROR_MSG_OTHER_EXCEPTION, e);
        }

        logger.info("Delete Member Service[End]");
        return member;
    }

    public List<MemberModel> findAllMember(RequestObject requestObject) throws BiztechException {

        logger.info("Find All Member Service[Start]");
        List<MemberModel> models;
        try {

            logger.info("Find All Member Service[Load value to object]");
            ActivityModel activityModel = (ActivityModel) StringUtil.copy(requestObject.getBody(), new ActivityModel());
            logger.info("Find All Member Service[activity : "+ activityModel.toString()+"]");

            logger.info("Find All Member Service[Insert activity logs]");
            activityService.addLog(activityModel);

            models = memberRepository.findAll();
        } catch (BiztechException be) {
            throw be;
        } catch (Exception e) {
            throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_OTHER_EXCEPTION, GlobalConstant.ERROR_MSG_OTHER_EXCEPTION, e);
        }

        logger.info("Find All Member Service[End]");
        return models;
    }

    public List<MemberModel> findByName(RequestObject requestObject) throws BiztechException {

        logger.info("Find Member By Name Service[Start]");
        List<MemberModel> models;

        try {

            logger.info("Find Member By Name Service[Load value to object]");
            MemberModel member = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            ActivityModel activityModel = (ActivityModel) StringUtil.copy(requestObject.getBody(), new ActivityModel());
            logger.info("Find Member By Name Service[member : "+ member.toString()+"]");
            logger.info("Find Member By Name Service[activity : "+ activityModel.toString()+"]");

            logger.info("Find Member By Name[Insert activity logs]");
            activityService.addLog(activityModel);

            models = memberRepository.findByName(member.getName());
        } catch (BiztechException be) {
            throw be;
        } catch (Exception e) {
            throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_OTHER_EXCEPTION, GlobalConstant.ERROR_MSG_OTHER_EXCEPTION, e);
        }

        logger.info("Find Member By Name Service[End]");
        return models;
    }

    public List<MemberModel> findBySurname(RequestObject requestObject) throws BiztechException {

        logger.info("Find Member By Surname Service[Start]");
        List<MemberModel> models;

        try {

            logger.info("Find Member By Surname Service[Load value to object]");
            MemberModel member = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            ActivityModel activityModel = (ActivityModel) StringUtil.copy(requestObject.getBody(), new ActivityModel());
            logger.info("Find Member By Surname Service[member : "+ member.toString()+"]");
            logger.info("Find Member By Surname Service[activity : "+ activityModel.toString()+"]");

            logger.info("Find Member By Surname[Insert activity logs]");
            activityService.addLog(activityModel);

            models = memberRepository.findBySurname(member.getSurname());
        } catch (BiztechException be) {

            throw be;
        } catch (Exception e) {
            throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_OTHER_EXCEPTION, GlobalConstant.ERROR_MSG_OTHER_EXCEPTION, e);
        }

        logger.info("Find Member By Surname Service[End]");
        return models;
    }

    public MemberModel findByIdMember(RequestObject requestObject) throws BiztechException {

        logger.info("Find By Id Member Service[Start]");
        MemberModel model;

        try {

            logger.info("Find Member By Id Service[Load value to object]");
            model = (MemberModel) StringUtil.copy(requestObject.getBody(), new MemberModel());
            ActivityModel activityModel = (ActivityModel) StringUtil.copy(requestObject.getBody(), new ActivityModel());
            logger.info("Find Member By Id Service[member : "+ model.toString()+"]");
            logger.info("Find Member By Id Service[activity : "+ activityModel.toString()+"]");

            logger.info("Find By Id Member Service[Insert activity logs]");
            activityService.addLog(activityModel);

            logger.info("Find By Id Member Service[validate id with db]");
            if (memberRepository.existsById(model.getId())) {
                model = memberRepository.findById(model.getId()).get();
            } else {
                throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_DATA_NOT_FOUND, GlobalConstant.ERROR_MSG_DATA_NOT_FOUND, null, "id", String.valueOf(model.getId()));
            }
        } catch (BiztechException be) {
            throw be;
        } catch (Exception e) {
            throw new BiztechException(GlobalConstant.PROJECT_NAME, GlobalConstant.ERROR_CODE_OTHER_EXCEPTION, GlobalConstant.ERROR_MSG_OTHER_EXCEPTION, e);
        }

        logger.info("Find By Id Member Service[End]");
        return model;
    }
}
