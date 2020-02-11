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
@XmlRootElement(name = "Inbox")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "กล่องหนังสือเข้า")
public class InboxModel extends VersionModel {

    private static final long serialVersionUID = -2443067835898627821L;
    @XmlElement(name = "id")
    @Expose
    @Since(1.0)
    private int id;
    @Expose
    @Since(1.0)
    private int inboxReceiveFlag;
    @Expose
    @Since(1.0)
    private int inboxOpenFlag;
    @Expose
    @Since(1.0)
    private int inboxActionFlag;
    @Expose
    @Since(1.0)
    private int inboxFinishFlag;
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
    @ApiParam(name = "userProfileFolderId", value = "รหัสแฟ้มของผู้ใช้งาน", required = false)
    @Expose
    @Since(1.0)
    private int userProfileFolderId;

    @XmlElement(name = "structureFolderId")
    @ApiParam(name = "structureFolderId", value = "รหัสแฟ้มของหน่วยงาน", required = false)
    @Expose
    @Since(1.0)
    private int structureFolderId;

    @XmlElement(name = "inboxFrom")
    @ApiParam(name = "inboxFrom", value = "จาก", required = true)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String inboxFrom;

    @XmlElement(name = "inboxTo")
    @ApiParam(name = "inboxTo", value = "ถึง", required = true)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String inboxTo;

    @XmlElement(name = "inboxTitle")
    @ApiParam(name = "inboxTitle", value = "เรื่อง", required = true)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String inboxTitle;

    @XmlElement(name = "inboxSendDate")
    @ApiParam(name = "inboxSendDate", value = "วันที่ส่ง", required = true)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String inboxSendDate;

    @XmlElement(name = "inboxReceiveDate")
    @ApiParam(name = "inboxReceiveDate", value = "วันที่ได้รับ", required = true)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String inboxReceiveDate;

    @XmlElement(name = "inboxReceiveDateDefine")
    @ApiParam(name = "inboxReceiveDateDefine", value = "กำหนดรับภายในวันที่", required = true)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String inboxReceiveDateDefine;

    @XmlElement(name = "inboxOpenDate")
    @ApiParam(name = "inboxOpenDate", value = "วันที่เปิด", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String inboxOpenDate;

    @XmlElement(name = "inboxOpenDateDefine")
    @ApiParam(name = "inboxOpenDateDefine", value = "กำหนดเปิดอ่านภายในวันที่", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String inboxOpenDateDefine;

    @XmlElement(name = "inboxActionDate")
    @ApiParam(name = "inboxActionDate", value = "วันที่กระทำ", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String inboxActionDate;

    @XmlElement(name = "inboxActionDateDefine")
    @ApiParam(name = "inboxActionDateDefine", value = "กำหนดความเคลื่อนไหวภายในวันที่", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String inboxActionDateDefine;

    @XmlElement(name = "inboxFinishDate")
    @ApiParam(name = "inboxFinishDate", value = "วันที่เสร็จ", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String inboxFinishDate;

    @XmlElement(name = "inboxFinishDateDefine")
    @ApiParam(name = "inboxFinishDateDefine", value = "กำหนดเรื่องเสร็จภายในวันที่", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String inboxFinishDateDefine;

    @XmlElement(name = "moduleId")
    @ApiParam(name = "moduleId", value = "รหัสของระบบ", required = true)
    @Expose
    @Since(1.0)
    private int moduleId;

    @XmlElement(name = "linkId")
    @ApiParam(name = "linkId", value = "รหัสเชื่อมโยงเอกสาร1", required = false)
    @Expose
    @Since(1.0)
    private int linkId;

    @XmlElement(name = "linkId2")
    @ApiParam(name = "linkId2", value = "รหัสเชื่อมโยงเอกสาร2", required = false)
    @Expose
    @Since(1.0)
    private int linkId2;

    @XmlElement(name = "linkType")
    @ApiParam(name = "linkType", value = "ประเภทการเชื่อมโยงเอกสาร", required = true)
    @Size(max = 10)
    @Expose
    @Since(1.0)
    private String linkType;

    @XmlElement(name = "inboxStr01")
    @ApiParam(name = "inboxStr01", value = "ข้อมูลการส่ง1", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String inboxStr01;

    @XmlElement(name = "inboxStr02")
    @ApiParam(name = "inboxStr02", value = "ข้อมูลการส่ง2", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String inboxStr02;

    @XmlElement(name = "inboxStr03")
    @ApiParam(name = "inboxStr03", value = "ข้อมูลการส่ง3", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String inboxStr03;

    @XmlElement(name = "inboxStr04")
    @ApiParam(name = "inboxStr04", value = "ข้อมูลการส่ง4", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String inboxStr04;

    @XmlElement(name = "inboxDate01")
    @ApiParam(name = "inboxDate01", value = "วันที่ข้อมูลการส่ง1", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String inboxDate01;

    @XmlElement(name = "inboxDate02")
    @ApiParam(name = "inboxDate02", value = "วันที่ข้อมูลการส่ง2", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String inboxDate02;

    @XmlElement(name = "inboxDescription")
    @ApiParam(name = "inboxDescription", value = "หมายเหตุ", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String inboxDescription;

    @XmlElement(name = "inboxNote")
    @ApiParam(name = "inboxNote", value = "บันทึก", required = false)
    @Size(max = 1000)
    @Expose
    @Since(1.0)
    private String inboxNote;

    @XmlElement(name = "inboxKey")
    @ApiParam(name = "inboxKey", value = "คีย์ของหนังสือเข้า", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String inboxKey;

    @XmlElement(name = "inboxApprove")
    @ApiParam(name = "inboxApprove", value = "มีการขออนุมัติหรือไม่ 0=ปกติ,1=มีการขออนุมัติ", required = true)
    @Expose
    @Since(1.0)
    private int inboxApprove;

    @XmlElement(name = "inboxApproveStatus")
    @ApiParam(name = "inboxApproveStatus", value = "สถานะการอนุมัติ 0=ปกติ,1=อนุมัติ,2=ไม่อนุมัติ", required = true)
    @Expose
    @Since(1.0)
    private int inboxApproveStatus;

    @XmlElement(name = "isFinished")
    @Expose
    private boolean isFinished;//oat-add

    @XmlElement(name = "isCanceled")
    @Expose
    private boolean isCanceled;//oat-add

    @XmlElement(name = "inboxSpeed")
    @Expose
    private int inboxSpeed;//oat-add

    @XmlElement(name = "openDAteDefineStatus")
    @Expose
    private int openDateDefineStatus;

    public InboxModel() {
    }

    public InboxModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสกล่องหนังสือเข้า", required = true)
    public void setId(int id) {
        this.id = id;
    }

    public int getInboxReceiveFlag() {
        return inboxReceiveFlag;
    }

    public void setInboxReceiveFlag(int inboxReceiveFlag) {
        this.inboxReceiveFlag = inboxReceiveFlag;
    }

    public int getInboxOpenFlag() {
        return inboxOpenFlag;
    }

    public void setInboxOpenFlag(int inboxOpenFlag) {
        this.inboxOpenFlag = inboxOpenFlag;
    }

    public int getInboxActionFlag() {
        return inboxActionFlag;
    }

    public void setInboxActionFlag(int inboxActionFlag) {
        this.inboxActionFlag = inboxActionFlag;
    }

    public int getInboxFinishFlag() {
        return inboxFinishFlag;
    }

    public void setInboxFinishFlag(int inboxFinishFlag) {
        this.inboxFinishFlag = inboxFinishFlag;
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

    public String getInboxFrom() {
        return inboxFrom;
    }

    public void setInboxFrom(String inboxFrom) {
        this.inboxFrom = inboxFrom;
    }

    public String getInboxTo() {
        return inboxTo;
    }

    public void setInboxTo(String inboxTo) {
        this.inboxTo = inboxTo;
    }

    public String getInboxTitle() {
        return inboxTitle;
    }

    public void setInboxTitle(String inboxTitle) {
        this.inboxTitle = inboxTitle;
    }

    public String getInboxSendDate() {
        return inboxSendDate;
    }

    public void setInboxSendDate(String inboxSendDate) {
        this.inboxSendDate = inboxSendDate;
    }

    public String getInboxReceiveDate() {
        return inboxReceiveDate;
    }

    public void setInboxReceiveDate(String inboxReceiveDate) {
        this.inboxReceiveDate = inboxReceiveDate;
    }

    public String getInboxReceiveDateDefine() {
        return inboxReceiveDateDefine;
    }

    public void setInboxReceiveDateDefine(String inboxReceiveDateDefine) {
        this.inboxReceiveDateDefine = inboxReceiveDateDefine;
    }

    public String getInboxOpenDate() {
        return inboxOpenDate;
    }

    public void setInboxOpenDate(String inboxOpenDate) {
        this.inboxOpenDate = inboxOpenDate;
    }

    public String getInboxOpenDateDefine() {
        return inboxOpenDateDefine;
    }

    public void setInboxOpenDateDefine(String inboxOpenDateDefine) {
        this.inboxOpenDateDefine = inboxOpenDateDefine;
    }

    public String getInboxActionDate() {
        return inboxActionDate;
    }

    public void setInboxActionDate(String inboxActionDate) {
        this.inboxActionDate = inboxActionDate;
    }

    public String getInboxActionDateDefine() {
        return inboxActionDateDefine;
    }

    public void setInboxActionDateDefine(String inboxActionDateDefine) {
        this.inboxActionDateDefine = inboxActionDateDefine;
    }

    public String getInboxFinishDate() {
        return inboxFinishDate;
    }

    public void setInboxFinishDate(String inboxFinishDate) {
        this.inboxFinishDate = inboxFinishDate;
    }

    public String getInboxFinishDateDefine() {
        return inboxFinishDateDefine;
    }

    public void setInboxFinishDateDefine(String inboxFinishDateDefine) {
        this.inboxFinishDateDefine = inboxFinishDateDefine;
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

    public String getInboxStr01() {
        return inboxStr01;
    }

    public void setInboxStr01(String inboxStr01) {
        this.inboxStr01 = inboxStr01;
    }

    public String getInboxStr02() {
        return inboxStr02;
    }

    public void setInboxStr02(String inboxStr02) {
        this.inboxStr02 = inboxStr02;
    }

    public String getInboxStr03() {
        return inboxStr03;
    }

    public void setInboxStr03(String inboxStr03) {
        this.inboxStr03 = inboxStr03;
    }

    public String getInboxStr04() {
        return inboxStr04;
    }

    public void setInboxStr04(String inboxStr04) {
        this.inboxStr04 = inboxStr04;
    }

    public String getInboxDate01() {
        return inboxDate01;
    }

    public void setInboxDate01(String inboxDate01) {
        this.inboxDate01 = inboxDate01;
    }

    public String getInboxDate02() {
        return inboxDate02;
    }

    public void setInboxDate02(String inboxDate02) {
        this.inboxDate02 = inboxDate02;
    }

    public String getInboxDescription() {
        return inboxDescription;
    }

    public void setInboxDescription(String inboxDescription) {
        this.inboxDescription = inboxDescription;
    }

    public String getInboxNote() {
        return inboxNote;
    }

    public void setInboxNote(String inboxNote) {
        this.inboxNote = inboxNote;
    }

    public String getInboxKey() {
        return inboxKey;
    }

    public void setInboxKey(String inboxKey) {
        this.inboxKey = inboxKey;
    }

    public int getInboxApprove() {
        return inboxApprove;
    }

    public void setInboxApprove(int inboxApprove) {
        this.inboxApprove = inboxApprove;
    }

    public int getInboxApproveStatus() {
        return inboxApproveStatus;
    }

    public void setInboxApproveStatus(int inboxApproveStatus) {
        this.inboxApproveStatus = inboxApproveStatus;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public boolean getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    public int getInboxSpeed() {
        return inboxSpeed;
    }

    public void setInboxSpeed(int inboxSpeed) {
        this.inboxSpeed = inboxSpeed;
    }

    public int getOpenDateDefineStatus() {
        return openDateDefineStatus;
    }

    public void setOpenDateDefineStatus(int openDateDefineStatus) {
        this.openDateDefineStatus = openDateDefineStatus;
    }

}
