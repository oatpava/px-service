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
@XmlRootElement(name = "Inbox Search")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ค้นหากล่องหนังสือเข้า")
public class InboxSearchModel extends VersionModel {

    private static final long serialVersionUID = -3420130299419662211L;

    @XmlElement(name = "userId")
    @ApiParam(name = "userId", value = "รหัสผู้ใช้งาน", required = true)
    @Expose
    @Since(1.0)
    private int userId;

    @XmlElement(name = "structureId")
    @ApiParam(name = "structureId", value = "รหัสหน่วยงาน", required = true)
    @Expose
    @Since(1.0)
    private int structureId;

    @XmlElement(name = "inboxFrom")
    @ApiParam(name = "inboxFrom", value = "จาก", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String inboxFrom;

    @XmlElement(name = "inboxTitle")
    @ApiParam(name = "inboxTitle", value = "ถึง", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String inboxTitle;

    @XmlElement(name = "inboxStartDate")
    @ApiParam(name = "inboxStartDate", value = "จากวันที่", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String inboxStartDate;

    @XmlElement(name = "inboxEndDate")
    @ApiParam(name = "inboxEndDate", value = "ถึงวันที่", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String inboxEndDate;

    @XmlElement(name = "inboxNote")
    @ApiParam(name = "inboxNote", value = "บันทึก", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String inboxNote;

    @XmlElement(name = "inboxDescription")
    @ApiParam(name = "inboxDescription", value = "หมายเหตุ", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String inboxDescription;

    @XmlElement(name = "inboxStr04")
    @ApiParam(name = "inboxStr04", value = "เลขที่หนังสือ", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String inboxStr04;

    @XmlElement(name = "inboxStr03")
    @ApiParam(name = "inboxStr03", value = "เลขทะเบียน", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String inboxStr03;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStructureId() {
        return structureId;
    }

    public void setStructureId(int structureId) {
        this.structureId = structureId;
    }

    public String getInboxFrom() {
        return inboxFrom;
    }

    public void setInboxFrom(String inboxFrom) {
        this.inboxFrom = inboxFrom;
    }

    public String getInboxTitle() {
        return inboxTitle;
    }

    public void setInboxTitle(String inboxTitle) {
        this.inboxTitle = inboxTitle;
    }

    public String getInboxStartDate() {
        return inboxStartDate;
    }

    public void setInboxStartDate(String inboxStartDate) {
        this.inboxStartDate = inboxStartDate;
    }

    public String getInboxEndDate() {
        return inboxEndDate;
    }

    public void setInboxEndDate(String inboxEndDate) {
        this.inboxEndDate = inboxEndDate;
    }

    public String getInboxNote() {
        return inboxNote;
    }

    public void setInboxNote(String inboxNote) {
        this.inboxNote = inboxNote;
    }

    public String getInboxDescription() {
        return inboxDescription;
    }

    public void setInboxDescription(String inboxDescription) {
        this.inboxDescription = inboxDescription;
    }

    public String getInboxStr04() {
        return inboxStr04;
    }

    public void setInboxStr04(String inboxStr04) {
        this.inboxStr04 = inboxStr04;
    }

    public String getInboxStr03() {
        return inboxStr03;
    }

    public void setInboxStr03(String inboxStr03) {
        this.inboxStr04 = inboxStr03;
    }
}
