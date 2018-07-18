package com.biztech.demo.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class common response")
public class CommonResponseObject {

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Response code of this request", required = true)
    private String responseCode;

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Response detail of this request", required = true)
    private String responseDesc;

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Response project exception")
    private String exceptionProject;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public String getExceptionProject() {
        return exceptionProject;
    }

    public void setExceptionProject(String exceptionProject) {
        this.exceptionProject = exceptionProject;
    }
}
