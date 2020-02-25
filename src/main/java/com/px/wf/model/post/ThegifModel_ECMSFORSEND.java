package com.px.wf.model.post;

import com.px.wf.model.get.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TUMKUNG
 */
@MappedSuperclass
@ApiModel(description = "ข้อมูลจาก ecms")
@XmlRootElement(name = "ThegifModel_ECMSFORSEND")
public class ThegifModel_ECMSFORSEND {
    
    @Expose @Since(1.0) private int wfContentId;
    @Expose @Since(1.0) private String filePath;
    @Expose @Since(1.0) private String thegifDepartmentReceiver;
    @Expose @Since(1.0) private String thegifLetterStatus;
    @Expose @Since(1.0) private int thegifId;


    public ThegifModel_ECMSFORSEND() {
    }

    public int getWfContentId() {
        return wfContentId;
    }

    public void setWfContentId(int wfContentId) {
        this.wfContentId = wfContentId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getThegifDepartmentReceiver() {
        return thegifDepartmentReceiver;
    }

    public void setThegifDepartmentReceiver(String thegifDepartmentReceiver) {
        this.thegifDepartmentReceiver = thegifDepartmentReceiver;
    }

    public String getThegifLetterStatus() {
        return thegifLetterStatus;
    }

    public void setThegifLetterStatus(String thegifLetterStatus) {
        this.thegifLetterStatus = thegifLetterStatus;
    }

    public int getThegifId() {
        return thegifId;
    }

    public void setThegifId(int thegifId) {
        this.thegifId = thegifId;
    }

}
