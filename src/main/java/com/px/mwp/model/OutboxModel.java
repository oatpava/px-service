package com.px.mwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "Outbox")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "กล่องหนังสือออก")
public class OutboxModel extends VersionModel {

    private static final long serialVersionUID = -2443067835898627821L;
    @XmlElement(name = "id")
    @Expose
    @Since(1.0)
    private int id;
    @Expose
    @Since(1.0)
    private int outboxReceiveFlag;
    @Expose
    @Since(1.0)
    private int outboxOpenFlag;
    @Expose
    @Since(1.0)
    private int outboxActionFlag;
    @Expose
    @Since(1.0)
    private int outboxFinishFlag;
    @Expose
    @Since(1.0)
    private int letterStatus1;
    @Expose
    @Since(1.0)
    private int letterStatus2;
    @Expose
    @Since(1.0)
    private int letterStatus3;
    @Expose
    @Since(1.0)
    private int createdBy;
    @Expose
    @Since(1.0)
    private String createdDate;
    @Expose
    @Since(1.0)
    private float orderNo;
    @Expose
    @Since(1.0)
    private int removedBy;
    @Expose
    @Since(1.0)
    private String removedDate;
    @Expose
    @Since(1.0)
    private int updatedBy;
    @Expose
    @Since(1.0)
    private String updatedDate;
    @Expose
    @Since(1.0)
    private int workflowId;

    @XmlElement(name = "userProfileFolderId")
    @ApiParam(name = "userProfileFolderId", value = "รหัสแฟ้มของผู้ใช้งาน", required = true)
    @Expose
    @Since(1.0)
    private int userProfileFolderId;

    @XmlElement(name = "structureFolderId")
    @ApiParam(name = "structureFolderId", value = "รหัสแฟ้มของหน่วยงาน", required = true)
    @Expose
    @Since(1.0)
    private int structureFolderId;

    @XmlElement(name = "outboxFrom")
    @ApiParam(name = "outboxFrom", value = "จาก", required = true)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxFrom;

    @XmlElement(name = "outboxTo")
    @ApiParam(name = "outboxTo", value = "ถึง", required = true)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxTo;

    @XmlElement(name = "outboxCc")
    @ApiParam(name = "outboxCc", value = "ถึง(สำเนา)", required = true)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxCc;

    @XmlElement(name = "outboxTitle")
    @ApiParam(name = "outboxTitle", value = "เรื่อง", required = true)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxTitle;

    @XmlElement(name = "outboxSendDate")
    @ApiParam(name = "outboxSendDate", value = "วันที่ส่ง", required = true)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String outboxSendDate;

    @XmlElement(name = "outboxReceiveDate")
    @ApiParam(name = "outboxReceiveDate", value = "วันที่ได้รับ", required = true)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String outboxReceiveDate;

    @XmlElement(name = "outboxReceiveDateDefine")
    @ApiParam(name = "outboxReceiveDateDefine", value = "วันที่กำหนดได้รับ", required = true)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String outboxReceiveDateDefine;

    @XmlElement(name = "outboxOpenDate")
    @ApiParam(name = "outboxOpenDate", value = "วันที่เปิด", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String outboxOpenDate;

    @XmlElement(name = "outboxOpenDateDefine")
    @ApiParam(name = "outboxOpenDateDefine", value = "วันที่กำหนดเปิด", required = true)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String outboxOpenDateDefine;

    @XmlElement(name = "outboxActionDate")
    @ApiParam(name = "outboxActionDate", value = "วันที่กระทำ", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String outboxActionDate;

    @XmlElement(name = "outboxActionDateDefine")
    @ApiParam(name = "outboxActionDateDefine", value = "วันที่กำหนดการกระทำ", required = true)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String outboxActionDateDefine;

    @XmlElement(name = "outboxFinishDate")
    @ApiParam(name = "outboxFinishDate", value = "วันที่เสร็จ", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String outboxFinishDate;

    @XmlElement(name = "outboxFinishDateDefine")
    @ApiParam(name = "outboxFinishDateDefine", value = "วันที่กำหนดเสร็จ", required = true)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String outboxFinishDateDefine;

    @XmlElement(name = "moduleId")
    @ApiParam(name = "moduleId", value = "รหัสของระบบ", required = true)
    @Expose
    @Since(1.0)
    private int moduleId;

    @XmlElement(name = "linkId")
    @ApiParam(name = "linkId", value = "รหัสเชื่อมโยงเอกสาร1", required = true)
    @Expose
    @Since(1.0)
    private int linkId;

    @XmlElement(name = "linkId2")
    @ApiParam(name = "linkId2", value = "รหัสเชื่อมโยงเอกสาร2", required = true)
    @Expose
    @Since(1.0)
    private int linkId2;

    @XmlElement(name = "linkType")
    @ApiParam(name = "linkType", value = "ประเภทการเชื่อมโยงเอกสาร", required = true)
    @Size(max = 10)
    @Expose
    @Since(1.0)
    private String linkType;

    @XmlElement(name = "outboxStr01")
    @ApiParam(name = "outboxStr01", value = "", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String outboxStr01;

    @XmlElement(name = "outboxStr02")
    @ApiParam(name = "outboxStr02", value = "", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String outboxStr02;

    @XmlElement(name = "outboxStr03")
    @ApiParam(name = "outboxStr03", value = "", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String outboxStr03;

    @XmlElement(name = "outboxStr04")
    @ApiParam(name = "outboxStr04", value = "", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String outboxStr04;

    @XmlElement(name = "outboxDate01")
    @ApiParam(name = "outboxDate01", value = "", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String outboxDate01;

    @XmlElement(name = "outboxDate02")
    @ApiParam(name = "outboxDate02", value = "", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String outboxDate02;

    @XmlElement(name = "outboxDescription")
    @ApiParam(name = "outboxDescription", value = "หมายเหตุ", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxDescription;

    @XmlElement(name = "outboxNote")
    @ApiParam(name = "outboxNote", value = "บันทึก", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String outboxNote;

    @XmlElement(name = "outboxApprove")
    @ApiParam(name = "outboxApprove", value = "มีการขออนุมัติหรือไม่ 0=ปกติ,1=มีการขออนุมัติ", required = false)
    @Expose
    @Since(1.0)
    private int outboxApprove;

    @XmlElement(name = "isCanceled")
    @Expose
    private boolean isCanceled;//oat-add
    
    @XmlElement(name = "outboxSpeed")
    @Expose
    private int outboxSpeed;//oat-add

    public OutboxModel() {
    }

    public OutboxModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสกล่องหนังสือเข้า", required = true)
    public void setId(int id) {
        this.id = id;
    }

    public int getOutboxReceiveFlag() {
        return outboxReceiveFlag;
    }

    public void setOutboxReceiveFlag(int outboxReceiveFlag) {
        this.outboxReceiveFlag = outboxReceiveFlag;
    }

    public int getOutboxOpenFlag() {
        return outboxOpenFlag;
    }

    public void setOutboxOpenFlag(int outboxOpenFlag) {
        this.outboxOpenFlag = outboxOpenFlag;
    }

    public int getOutboxActionFlag() {
        return outboxActionFlag;
    }

    public void setOutboxActionFlag(int outboxActionFlag) {
        this.outboxActionFlag = outboxActionFlag;
    }

    public int getOutboxFinishFlag() {
        return outboxFinishFlag;
    }

    public void setOutboxFinishFlag(int outboxFinishFlag) {
        this.outboxFinishFlag = outboxFinishFlag;
    }

    public int getLetterStatus1() {
        return letterStatus1;
    }

    public void setLetterStatus1(int letterStatus1) {
        this.letterStatus1 = letterStatus1;
    }

    public int getLetterStatus2() {
        return letterStatus2;
    }

    public void setLetterStatus2(int letterStatus2) {
        this.letterStatus2 = letterStatus2;
    }

    public int getLetterStatus3() {
        return letterStatus3;
    }

    public void setLetterStatus3(int letterStatus3) {
        this.letterStatus3 = letterStatus3;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public float getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(float orderNo) {
        this.orderNo = orderNo;
    }

    public int getRemovedBy() {
        return removedBy;
    }

    public void setRemovedBy(int removedBy) {
        this.removedBy = removedBy;
    }

    public String getRemovedDate() {
        return removedDate;
    }

    public void setRemovedDate(String removedDate) {
        this.removedDate = removedDate;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
    }

    public int getUserProfileFolderId() {
        return userProfileFolderId;
    }

    public void setUserProfileFolderId(int userProfileFolderId) {
        this.userProfileFolderId = userProfileFolderId;
    }

    public int getStructureFolderId() {
        return structureFolderId;
    }

    public void setStructureFolderId(int structureFolderId) {
        this.structureFolderId = structureFolderId;
    }

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

    public String getOutboxCc() {
        return outboxCc;
    }

    public void setOutboxCc(String outboxCc) {
        this.outboxCc = outboxCc;
    }

    public String getOutboxTitle() {
        return outboxTitle;
    }

    public void setOutboxTitle(String outboxTitle) {
        this.outboxTitle = outboxTitle;
    }

    public String getOutboxSendDate() {
        return outboxSendDate;
    }

    public void setOutboxSendDate(String outboxSendDate) {
        this.outboxSendDate = outboxSendDate;
    }

    public String getOutboxReceiveDate() {
        return outboxReceiveDate;
    }

    public void setOutboxReceiveDate(String outboxReceiveDate) {
        this.outboxReceiveDate = outboxReceiveDate;
    }

    public String getOutboxReceiveDateDefine() {
        return outboxReceiveDateDefine;
    }

    public void setOutboxReceiveDateDefine(String outboxReceiveDateDefine) {
        this.outboxReceiveDateDefine = outboxReceiveDateDefine;
    }

    public String getOutboxOpenDate() {
        return outboxOpenDate;
    }

    public void setOutboxOpenDate(String outboxOpenDate) {
        this.outboxOpenDate = outboxOpenDate;
    }

    public String getOutboxOpenDateDefine() {
        return outboxOpenDateDefine;
    }

    public void setOutboxOpenDateDefine(String outboxOpenDateDefine) {
        this.outboxOpenDateDefine = outboxOpenDateDefine;
    }

    public String getOutboxActionDate() {
        return outboxActionDate;
    }

    public void setOutboxActionDate(String outboxActionDate) {
        this.outboxActionDate = outboxActionDate;
    }

    public String getOutboxActionDateDefine() {
        return outboxActionDateDefine;
    }

    public void setOutboxActionDateDefine(String outboxActionDateDefine) {
        this.outboxActionDateDefine = outboxActionDateDefine;
    }

    public String getOutboxFinishDate() {
        return outboxFinishDate;
    }

    public void setOutboxFinishDate(String outboxFinishDate) {
        this.outboxFinishDate = outboxFinishDate;
    }

    public String getOutboxFinishDateDefine() {
        return outboxFinishDateDefine;
    }

    public void setOutboxFinishDateDefine(String outboxFinishDateDefine) {
        this.outboxFinishDateDefine = outboxFinishDateDefine;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public int getLinkId2() {
        return linkId2;
    }

    public void setLinkId2(int linkId2) {
        this.linkId2 = linkId2;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getOutboxStr01() {
        return outboxStr01;
    }

    public void setOutboxStr01(String outboxStr01) {
        this.outboxStr01 = outboxStr01;
    }

    public String getOutboxStr02() {
        return outboxStr02;
    }

    public void setOutboxStr02(String outboxStr02) {
        this.outboxStr02 = outboxStr02;
    }

    public String getOutboxStr03() {
        return outboxStr03;
    }

    public void setOutboxStr03(String outboxStr03) {
        this.outboxStr03 = outboxStr03;
    }

    public String getOutboxStr04() {
        return outboxStr04;
    }

    public void setOutboxStr04(String outboxStr04) {
        this.outboxStr04 = outboxStr04;
    }

    public String getOutboxDate01() {
        return outboxDate01;
    }

    public void setOutboxDate01(String outboxDate01) {
        this.outboxDate01 = outboxDate01;
    }

    public String getOutboxDate02() {
        return outboxDate02;
    }

    public void setOutboxDate02(String outboxDate02) {
        this.outboxDate02 = outboxDate02;
    }

    public String getOutboxDescription() {
        return outboxDescription;
    }

    public void setOutboxDescription(String outboxDescription) {
        this.outboxDescription = outboxDescription;
    }

    public String getOutboxNote() {
        return outboxNote;
    }

    public void setOutboxNote(String outboxNote) {
        this.outboxNote = outboxNote;
    }

    public int getOutboxApprove() {
        return outboxApprove;
    }

    public void setOutboxApprove(int outboxApprove) {
        this.outboxApprove = outboxApprove;
    }

    public boolean getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    public int getOutboxSpeed() {
        return outboxSpeed;
    }

    public void setOutboxSpeed(int outboxSpeed) {
        this.outboxSpeed = outboxSpeed;
    }
}
