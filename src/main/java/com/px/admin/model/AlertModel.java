package com.px.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oat
 */
@XmlRootElement(name = "Alert")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "การแจ้งเตือน")
public class AlertModel extends VersionModel {

    @XmlElement(name = "id")
    @Expose
    @Since(1.0)
    private int id;

    @XmlElement(name = "startDate", required = true)
    @ApiParam(name = "startDate", value = "วันที่เริ่มต้น", required = true)
    @Expose
    @Since(1.0)
    private String startDate;

    @XmlElement(name = "endDate", required = true)
    @ApiParam(name = "endDate", value = "วันที่สิ้นสุด", required = true)
    @Expose
    @Since(1.0)
    private String endDate;

    @XmlElement(name = "message", required = true)
    @ApiParam(name = "message", value = "ข้อความแจ้งเตือน", required = true)
    @Size(max = 2000)
    @Expose
    @Since(1.0)
    private String message;

    @XmlElement(name = "active", required = true)
    @ApiParam(name = "active", value = "การใช้งาน", required = true)
    @Expose
    @Since(1.0)
    private boolean active;

    public AlertModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
