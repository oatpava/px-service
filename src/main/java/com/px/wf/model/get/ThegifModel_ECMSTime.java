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
@ApiModel(description = "ข้อมูลการตรวจสอบเวลา")
@XmlRootElement(name = "ThegifModel_ECMSTime")
public class ThegifModel_ECMSTime {

    @Expose @Since(1.0) private String ECMSTimeCheck;

    public ThegifModel_ECMSTime() {
    }

    public String getECMSTimeCheck() {
        return ECMSTimeCheck;
    }

    public void setECMSTimeCheck(String ECMSTimeCheck) {
        this.ECMSTimeCheck = ECMSTimeCheck;
    }
    
}
