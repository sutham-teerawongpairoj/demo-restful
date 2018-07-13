package com.biztech.demo.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Wrapper class response of create member service")
public class UpdateMemberResponseObject extends CommonResponseObject {

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Response body of this request")
    private UpdateMemberResponseBodyObject responseBody;

    public UpdateMemberResponseBodyObject getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(UpdateMemberResponseBodyObject responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "UpdateMemberResponseObject{" +
                "responseCode='" + super.getResponseCode() + '\'' +
                ", responseDesc='" + super.getResponseDesc() + '\'' +
                ", responseBody=" + responseBody +
                '}';
    }
}
