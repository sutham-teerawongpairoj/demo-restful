package com.biztech.demo.object;

import com.biztech.demo.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "Class response of create member service")
public class CreateMemberResponseObject extends CommonResponseObject {

//    @JsonProperty(required = true)
//    @ApiModelProperty(notes = "Response code of this request", required = true)
//    private String responseCode;
//
//    @JsonProperty(required = true)
//    @ApiModelProperty(notes = "Response detail of this request", required = true)
//    private String responseDesc;

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "id")
    private int id;

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "User id")
    private String userId;

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "User's First name")
    private String name;

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "User's Last name")
    private String surname;

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "User's Created")
    private Date createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCreatedDate() {

        try {
            return DateUtil.DefaultDateToString(createdDate);
        } catch (Exception e) {
            return null;
        }
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {

        String tmpDate;
        try {
            tmpDate = DateUtil.DefaultDateToString(createdDate);
        } catch (Exception e) {
            tmpDate = null;
        }

        return "CreateMemberResponseObject{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", createdDate=" + tmpDate +
                '}';
    }
}
