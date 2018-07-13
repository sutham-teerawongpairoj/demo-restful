package com.biztech.demo.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Body class request of update member service")
public class UpdateMemberBodyRequestObject {

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Service operation", required = true)
    private String activity;

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

    @JsonProperty
    @ApiModelProperty(notes = "Comment for this request")
    private String notes;

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "UpdateMemberBodyRequestObject{" +
                "activity='" + activity + '\'' +
                ", id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
