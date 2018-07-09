package com.biztech.demo.apicontroller;

import com.biztech.demo.model.MemberModel;
import com.biztech.demo.object.RequestObject;
import com.biztech.demo.object.ResponseObject;
import com.biztech.demo.service.MemberService;
import com.biztech.demo.util.BiztechException;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Member.")
public class MemberController {

    private static Logger logger = LogManager.getLogger(MemberController.class);

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation(value = "Create New Member", notes = "Create New Member With Member userId, name, surname and insert activity logs by activity")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created Successful", response = ResponseObject.class, responseContainer = "{\"responseCode\":\"0000I\",\"responseDesc\":\"SUCCESS\",\"responseBody\":{\"id\":39,\"userId\":\"Normal01\",\"name\":\"Name01\",\"surname\":\"Surname01\",\"createdDate\":\"05/07/2018 16:30:49\"}}"),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseObject.class),
        @ApiResponse(code = 400, message = "Parameter not found", response = ResponseObject.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "requestObject",
                    dataType = "RequestObject",
                    value = "{<br>\"header\":{},<br>\"body\":{<br>\"activity\":\"createMember\",<br>\"userId\":\"Normal01\",<br>\"name\":\"Name01\",<br>\"surname\":\"Surname01\"}<br>}",
                    examples = @io.swagger.annotations.Example(
                            value = {
                                    @ExampleProperty(value = "{\"header\":{},\"body\":{\"activity\":\"createMember\",\"userId\":\"Normal01\",\"name\":\"Name01\",\"surname\":\"Surname01\"}}", mediaType = "application/json")
                            }))
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> createMember(
            @ApiParam(value = "{\"header\":{},\"body\":{\"activity\":\"createMember\",\"userId\":\"Normal01\",\"name\":\"Name01\",\"surname\":\"Surname01\"}}", required = true)
            @RequestBody RequestObject requestObject) {

        logger.info("requestObject : "+ requestObject);
        logger.info("Add New Member header : "+ requestObject.getHeader() +", body : "+ requestObject.getBody());
        ResponseObject responseObject = new ResponseObject();
        try {

            logger.info("Insert new member");
            MemberModel member = memberService.addMember(requestObject);

            responseObject.setResponseCode("0000I");
            responseObject.setResponseDesc("SUCCESS");
            responseObject.setResponseBody(member);
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BiztechException bx) {

            logger.error(bx.getExceptionCode()+" : "+ bx.getExceptionMessage());
            logger.error("Error ", bx);
            responseObject.setResponseCode(bx.getExceptionCode());
            responseObject.setResponseDesc(bx.getExceptionMessage());
            return new ResponseEntity<>(responseObject, bx.getHttpStatus());
        } catch (Exception e) {

            logger.error("9999E"+" : "+ e.getMessage());
            logger.error("Error ", e);
            responseObject.setResponseCode("9999E");
            responseObject.setResponseDesc("Other Error Exception "+ e.getMessage());
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Update Member Data", notes = "Update Member With Member id, userId, name, surname and insert activity logs by activity", response = ResponseObject.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "requestObject",
                    dataType = "RequestObject",
                    value = "{<br>\"header\":{},<br>\"body\":{<br>\"activity\":\"updateMember\",<br>\"id\":1,<br>\"userId\":\"UserId01_UP\",<br>\"name\":\"Name01_UP\",<br>\"surname\":\"Surname01_UP\"}<br>}",
                    examples = @io.swagger.annotations.Example(
                            value = {
                                    @ExampleProperty(value = "{\"header\":{},\"body\":{\"activity\":\"updateMember\",\"id\":1,\"userId\":\"UserId01_UP\",\"name\":\"Name01_UP\",\"surname\":\"Surname01_UP\"}}", mediaType = "application/json")
                            }))
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created Successful", response = ResponseObject.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseObject.class),
            @ApiResponse(code = 400, message = "Parameter not found", response = ResponseObject.class)
    })
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> updateMember(
            @ApiParam(value = "{\"header\":{},\"body\":{\"activity\":\"updateMember\",\"id\":1,\"userId\":\"UserId01_UP\",\"name\":\"Name01_UP\",\"surname\":\"Surname01_UP\"}}", required = true)
            @RequestBody RequestObject requestObject) {

        logger.info("Update Member header : "+ requestObject.getHeader() +", body : "+ requestObject.getBody());
        ResponseObject responseObject = new ResponseObject();
        try {

            logger.info("Update member");
            MemberModel memberModel = memberService.updateMember(requestObject);

            responseObject.setResponseCode("0000I");
            responseObject.setResponseDesc("SUCCESS");
            responseObject.setResponseBody(memberModel);
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BiztechException bx) {

            logger.error(bx.getExceptionCode()+" : "+ bx.getExceptionMessage());
            logger.error("Error ", bx);
            responseObject.setResponseCode(bx.getExceptionCode());
            responseObject.setResponseDesc(bx.getExceptionMessage());
            return new ResponseEntity<>(responseObject, bx.getHttpStatus());
        } catch (Exception e) {

            logger.error("9999E"+" : "+ e.getMessage());
            logger.error("Error ", e);
            responseObject.setResponseCode("9999E");
            responseObject.setResponseDesc("Other Error Exception "+ e.getMessage());
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Delete Member", notes = "Delete Member With Member id and insert activity logs by activity", response = ResponseObject.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "requestObject",
                    dataType = "RequestObject",
                    value = "{<br>\"header\":{},<br>\"body\":{<br>\"activity\":\"deleteMember\",<br>\"id\":1}<br>}",
                    examples = @io.swagger.annotations.Example(
                            value = {
                                    @ExampleProperty(value = "{\"header\":{},\"body\":{\"activity\":\"deleteMember\",\"id\":1}}", mediaType = "application/json")
                            }))
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created Successful", response = ResponseObject.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseObject.class),
            @ApiResponse(code = 400, message = "Parameter not found", response = ResponseObject.class)
    })
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> deleteMember(
            @ApiParam(value = "{\"header\":{},\"body\":{\"activity\":\"deleteMember\",\"id\":1}}", required = true)
            @RequestBody RequestObject requestObject) {

        logger.info("Delete Member header : "+ requestObject.getHeader() +", body : "+ requestObject.getBody());
        ResponseObject responseObject = new ResponseObject();
        try {

            logger.info("Delete member");
            memberService.deleteMember(requestObject);

            responseObject.setResponseCode("0000I");
            responseObject.setResponseDesc("SUCCESS");
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BiztechException bx) {

            logger.error(bx.getExceptionCode()+" : "+ bx.getExceptionMessage());
            logger.error("Error ", bx);
            responseObject.setResponseCode(bx.getExceptionCode());
            responseObject.setResponseDesc(bx.getExceptionMessage());
            return new ResponseEntity<>(responseObject, bx.getHttpStatus());
        } catch (Exception e) {

            logger.error("9999E"+" : "+ e.getMessage());
            logger.error("Error ", e);
            responseObject.setResponseCode("9999E");
            responseObject.setResponseDesc("Other Error Exception "+ e.getMessage());
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Find All Member", notes = "Find All Member and insert activity logs by activity", response = ResponseObject.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "requestObject",
                    dataType = "RequestObject",
                    value = "{<br>\"header\":{},<br>\"body\":{<br>\"activity\":\"findAllMember\"}<br>}",
                    examples = @io.swagger.annotations.Example(
                            value = {
                                    @ExampleProperty(value = "{\"header\":{},\"body\":{\"activity\":\"findAllMember\"}}", mediaType = "application/json")
                            }))
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created Successful", response = ResponseObject.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseObject.class),
            @ApiResponse(code = 400, message = "Parameter not found", response = ResponseObject.class)
    })
    @RequestMapping(value = "/findAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> findAllMember(
            @ApiParam(value = "{\"header\":{},\"body\":{\"activity\":\"findAllMember\"}}", required = true)
            @RequestBody RequestObject requestObject) {

        logger.info("Find All Member header : "+ requestObject.getHeader() +", body : "+ requestObject.getBody());
        ResponseObject responseObject = new ResponseObject();
        try {

            List<MemberModel> models = memberService.findAllMember(requestObject);

            responseObject.setResponseCode("0000I");
            responseObject.setResponseDesc("SUCCESS");
            responseObject.setResponseBody(models);
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BiztechException bx) {

            logger.error(bx.getExceptionCode()+" : "+ bx.getExceptionMessage());
            logger.error("Error ", bx);
            responseObject.setResponseCode(bx.getExceptionCode());
            responseObject.setResponseDesc(bx.getExceptionMessage());
            return new ResponseEntity<>(responseObject, bx.getHttpStatus());
        } catch (Exception e) {

            logger.error("9999E"+" : "+ e.getMessage());
            logger.error("Error ", e);
            responseObject.setResponseCode("9999E");
            responseObject.setResponseDesc("Other Error Exception "+ e.getMessage());
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Find Member by name", notes = "Find Member by name and insert activity logs by activity", response = ResponseObject.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "requestObject",
                    dataType = "RequestObject",
                    value = "{<br>\"header\":{},<br>\"body\":{<br>\"activity\":\"findByNameMember\",<br>\"name\":\"ind\"}<br>}",
                    examples = @io.swagger.annotations.Example(
                            value = {
                                    @ExampleProperty(value = "{\"header\":{},\"body\":{\"activity\":\"findByNameMember\",\"name\":\"ind\"}}", mediaType = "application/json")
                            }))
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created Successful", response = ResponseObject.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseObject.class),
            @ApiResponse(code = 400, message = "Parameter not found", response = ResponseObject.class)
    })
    @RequestMapping(value = "/findByName", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> findByNameMember(
            @ApiParam(value = "{\"header\":{},\"body\":{\"activity\":\"findByNameMember\",\"name\":\"ind\"}}", required = true)
            @RequestBody RequestObject requestObject) {

        logger.info("Find By Name Member header : "+ requestObject.getHeader() +", body : "+ requestObject.getBody());
        ResponseObject responseObject = new ResponseObject();
        try {

            List<MemberModel> models = memberService.findByName(requestObject);

            responseObject.setResponseCode("0000I");
            responseObject.setResponseDesc("SUCCESS");
            responseObject.setResponseBody(models);
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BiztechException bx) {

            logger.error(bx.getExceptionCode()+" : "+ bx.getExceptionMessage());
            logger.error("Error ", bx);
            responseObject.setResponseCode(bx.getExceptionCode());
            responseObject.setResponseDesc(bx.getExceptionMessage());
            return new ResponseEntity<>(responseObject, bx.getHttpStatus());
        } catch (Exception e) {

            logger.error("9999E"+" : "+ e.getMessage());
            logger.error("Error ", e);
            responseObject.setResponseCode("9999E");
            responseObject.setResponseDesc("Other Error Exception "+ e.getMessage());
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Find Member By Surname", notes = "Find member By surname and insert activity logs by activity", response = ResponseObject.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "requestObject",
                    dataType = "RequestObject",
                    value = "{<br>\"header\":{},<br>\"body\":{<br>\"activity\":\"findBySurnameMember\",<br>\"surname\":\"ind\"}<br>}",
                    examples = @io.swagger.annotations.Example(
                            value = {
                                    @ExampleProperty(value = "{\"header\":{},\"body\":{\"activity\":\"findBySurnameMember\",\"surname\":\"ind\"}}", mediaType = "application/json")
                            }))
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created Successful", response = ResponseObject.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseObject.class),
            @ApiResponse(code = 400, message = "Parameter not found", response = ResponseObject.class)
    })
    @RequestMapping(value = "/findBySurname", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> findBySurnameMember(
            @ApiParam(value = "{\"header\":{},\"body\":{\"activity\":\"findBySurnameMember\",\"surname\":\"ind\"}}", required = true)
            @RequestBody RequestObject requestObject) {

        logger.info("Find By Surname Member header : "+ requestObject.getHeader() +", body : "+ requestObject.getBody());
        ResponseObject responseObject = new ResponseObject();
        try {

            List<MemberModel> models = memberService.findBySurname(requestObject);

            responseObject.setResponseCode("0000I");
            responseObject.setResponseDesc("SUCCESS");
            responseObject.setResponseBody(models);
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BiztechException bx) {

            logger.error(bx.getExceptionCode()+" : "+ bx.getExceptionMessage());
            logger.error("Error ", bx);
            responseObject.setResponseCode(bx.getExceptionCode());
            responseObject.setResponseDesc(bx.getExceptionMessage());
            return new ResponseEntity<>(responseObject, bx.getHttpStatus());
        } catch (Exception e) {

            logger.error("9999E"+" : "+ e.getMessage());
            logger.error("Error ", e);
            responseObject.setResponseCode("9999E");
            responseObject.setResponseDesc("Other Error Exception "+ e.getMessage());
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Find Member By id", notes = "Find Member by id and insert activity logs by activity", response = ResponseObject.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "requestObject",
                    dataType = "RequestObject",
                    value = "{<br>\"header\":{},<br>\"body\":{<br>\"activity\":\"findById\",<br>\"id\":1}<br>}",
                    examples = @io.swagger.annotations.Example(
                            value = {
                                    @ExampleProperty(value = "{\"header\":{},\"body\":{\"activity\":\"findById\",\"id\":1}}", mediaType = "application/json")
                            }))
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created Successful", response = ResponseObject.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseObject.class),
            @ApiResponse(code = 400, message = "Parameter not found", response = ResponseObject.class)
    })
    @RequestMapping(value = "/findById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> findById(
            @ApiParam(value = "{\"header\":{},\"body\":{\"activity\":\"findById\",\"id\":1}}", required = true)
            @RequestBody RequestObject requestObject) {

        logger.info("Find By Id Member header : "+ requestObject.getHeader() +", body : "+ requestObject.getBody());
        ResponseObject responseObject = new ResponseObject();
        try {

            MemberModel memberModel = memberService.findByIdMember(requestObject);

            responseObject.setResponseCode("0000I");
            responseObject.setResponseDesc("SUCCESS");
            responseObject.setResponseBody(memberModel);
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BiztechException bx) {

            logger.error(bx.getExceptionCode()+" : "+ bx.getExceptionMessage());
            logger.error("Error ", bx);
            responseObject.setResponseCode(bx.getExceptionCode());
            responseObject.setResponseDesc(bx.getExceptionMessage());
            return new ResponseEntity<>(responseObject, bx.getHttpStatus());
        } catch (Exception e) {

            logger.error("9999E"+" : "+ e.getMessage());
            logger.error("Error ", e);
            responseObject.setResponseCode("9999E");
            responseObject.setResponseDesc("Other Error Exception "+ e.getMessage());
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
