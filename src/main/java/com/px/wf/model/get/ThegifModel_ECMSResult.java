package com.px.wf.model.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@MappedSuperclass
@ApiModel(description = "ข้อมูลจาก ecms")
@XmlRootElement(name = "ThegifModel_ECMSResult")
public class ThegifModel_ECMSResult {
    
    @Expose @Since(1.0) private String result;
    @Expose @Since(1.0) private String errorCode;
    @Expose @Since(1.0) private String errorDescription;
    @Expose @Since(1.0) private String depCode;
    @Expose @Since(1.0) private int wfContentId;

    public ThegifModel_ECMSResult() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
 
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public void setWfContentId(int wfContentId) {
        this.wfContentId = wfContentId;
    }
}
