package com.biztech.demo.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Header class request of update member service")
public class UpdateMemberRequestHeaderObject {

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Username of this session", required = true)
    private String username;

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Token of this session", required = true)
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UpdateMemberRequestHeaderObject{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
