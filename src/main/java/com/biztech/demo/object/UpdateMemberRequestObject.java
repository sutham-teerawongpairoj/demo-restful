package com.biztech.demo.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Wrapper class request of update member service")
public class UpdateMemberRequestObject {

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Header object of update member service", required = true)
    private UpdateMemberRequestHeaderObject header;

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Body object of update member service", required = true)
    private UpdateMemberBodyRequestObject body;

    public UpdateMemberRequestHeaderObject getHeader() {
        return header;
    }

    public void setHeader(UpdateMemberRequestHeaderObject header) {
        this.header = header;
    }

    public UpdateMemberBodyRequestObject getBody() {
        return body;
    }

    public void setBody(UpdateMemberBodyRequestObject body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "UpdateMemberRequestObject{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
