/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.model;

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
@XmlRootElement(name = "Outbox Search")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ค้นหากล่องข้อมูลเข้า")
public class OutboxSearchModel extends VersionModel {

    private static final long serialVersionUID = 8490492522941937546L;

    @XmlElement(name = "outboxFrom")
    @ApiParam(name = "outboxFrom", value = "จาก", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxFrom;

    @XmlElement(name = "outboxTo")
    @ApiParam(name = "outboxFrom", value = "ถึง", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxTo;

    @XmlElement(name = "outboxTitle")
    @ApiParam(name = "outboxTitle", value = "เรื่อง", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxTitle;

    @XmlElement(name = "outboxStartDate")
    @ApiParam(name = "outboxStartDate", value = "จากวันที่", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String outboxStartDate;

    @XmlElement(name = "outboxEndDate")
    @ApiParam(name = "outboxEndDate", value = "ถึงวันที่", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String outboxEndDate;

    @XmlElement(name = "outboxNote")
    @ApiParam(name = "outboxNote", value = "บันทึก", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxNote;

    @XmlElement(name = "outboxDescription")
    @ApiParam(name = "outboxDescription", value = "หมายเหตุ", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxDescription;

    @XmlElement(name = "outboxStr04")
    @ApiParam(name = "outboxStr04", value = "เลขที่หนังสือ", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxStr04;

    public String getOutboxFrom() {
        return outboxFrom;
    }

    public void setOutboxFrom(String outboxFrom) {
        this.outboxFrom = outboxFrom;
    }

    public String getOutboxTo() {
        return outboxTo;
    }

    public void setOutboxTo(String outboxTo) {
        this.outboxTo = outboxTo;
    }

    public String getOutboxTitle() {
        return outboxTitle;
    }

    public void setOutboxTitle(String outboxTitle) {
        this.outboxTitle = outboxTitle;
    }

    public String getOutboxStartDate() {
        return outboxStartDate;
    }

    public void setOutboxStartDate(String outboxStartDate) {
        this.outboxStartDate = outboxStartDate;
    }

    public String getOutboxEndDate() {
        return outboxEndDate;
    }

    public void setOutboxEndDate(String outboxEndDate) {
        this.outboxEndDate = outboxEndDate;
    }

    public String getOutboxNote() {
        return outboxNote;
    }

    public void setOutboxNote(String outboxNote) {
        this.outboxNote = outboxNote;
    }

    public String getOutboxDescription() {
        return outboxDescription;
    }

    public void setOutboxDescription(String outboxDescription) {
        this.outboxDescription = outboxDescription;
    }

    public String getOutboxStr04() {
        return outboxStr04;
    }

    public void setOutboxStr04(String outboxStr04) {
        this.outboxStr04 = outboxStr04;
    }
}
