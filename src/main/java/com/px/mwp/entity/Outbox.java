/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.entity;

import com.px.share.entity.BaseEntity;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 *
 * @author ken
 */
@Entity
@Table(name = "PC_OUTBOX")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "OUTBOX_ID"))
})
public class Outbox extends BaseEntity{
    @Transient
    private static final long serialVersionUID = 614999143060475071L;
    
    @Column(name="USER_PROFILE_FOLDER_ID", columnDefinition="int default '0'")
    private int userProfileFolderId;
    
    @Column(name="STRUCTURE_FOLDER_ID", columnDefinition="int default '0'")
    private int structureFolderId;
    
    @Column(name="OUTBOX_FROM",length = 1000)
    private String outboxFrom;
    
    @Column(name="OUTBOX_TO",length = 1000)
    private String outboxTo;
    
    @Column(name="OUTBOX_CC",length = 1000)
    private String outboxCc;
    
    @Column(name="OUTBOX_TITLE",length = 1000)
    private String outboxTitle;
    
    @Column(name="OUTBOX_SEND_DATE")
    private LocalDateTime outboxSendDate;
    
    @Column(name="OUTBOX_RECEIVE_DATE")
    private LocalDateTime outboxReceiveDate;
    
    @Column(name = "OUTBOX_RECEIVE_DATE_DEFINE")
    private LocalDateTime outboxReceiveDateDefine;

    @Column(name = "OUTBOX_RECEIVE_FLAG", columnDefinition = "int default '0'")
    private int outboxReceiveFlag;
    
    @Column(name="OUTBOX_OPEN_DATE")
    private LocalDateTime outboxOpenDate;
    
    @Column(name = "OUTBOX_OPEN_DATE_DEFINE")
    private LocalDateTime outboxOpenDateDefine;

    @Column(name = "OUTBOX_OPEN_FLAG", columnDefinition = "int default '0'")
    private int outboxOpenFlag;
    
    @Column(name="OUTBOX_ACTION_DATE")
    private LocalDateTime outboxActionDate;
    
    @Column(name = "OUTBOX_ACTION_DATE_DEFINE")
    private LocalDateTime outboxActionDateDefine;

    @Column(name = "OUTBOX_ACTION_FLAG", columnDefinition = "int default '0'")
    private int outboxActionFlag;
    
    @Column(name="OUTBOX_FINISH_DATE")
    private LocalDateTime outboxFinishDate;
    
    @Column(name = "OUTBOX_FINISH_DATE_DEFINE")
    private LocalDateTime outboxFinishDateDefine;

    @Column(name = "OUTBOX_FINISH_FLAG", columnDefinition = "int default '0'")
    private int outboxFinishFlag;

    @Column(name = "LETTER_STATUS_1", columnDefinition = "int default '0'")
    private int letterStatus1;

    @Column(name = "LETTER_STATUS_2", columnDefinition = "int default '0'")
    private int letterStatus2;

    @Column(name = "LETTER_STATUS_3", columnDefinition = "int default '0'")
    private int letterStatus3;
    
    @Column(name="MODULE_ID", columnDefinition="int default '0'")
    private int moduleId;
    
    @Column(name="LINK_ID", columnDefinition="int default '0'")
    private int linkId;
    
    @Column(name="LINK_ID2", columnDefinition="int default '0'")
    private int linkId2;
    
    @Column(name="LINK_TYPE",length = 10)
    private String linkType;

    @Column(name = "OUTBOX_STR01", length = 255)
    private String outboxStr01;

    @Column(name = "OUTBOX_STR02", length = 255)
    private String outboxStr02;

    @Column(name = "OUTBOX_STR03", length = 255)
    private String outboxStr03;

    @Column(name = "OUTBOX_STR04", length = 255)
    private String outboxStr04;

    @Column(name = "OUTBOX_DATE01", nullable = true)
    private LocalDateTime outboxDate01;

    @Column(name = "OUTBOX_DATE02", nullable = true)
    private LocalDateTime outboxDate02;
    
    @Column(name = "OUTBOX_DESCRIPTION", nullable = true, length = 1000)
    private String outboxDescription;

    @Column(name = "OUTBOX_NOTE", nullable = true, length = 1000)
    private String outboxNote;
    
    @Column(name = "WORKFLOW_ID", columnDefinition = "int default '0'")
    private int workflowId;
    
    @Column(name = "OUTBOX_APPROVE", columnDefinition = "int default '0'")
    private int outboxApprove;
    
    @Column(name = "OUTBOX_SPEED", columnDefinition = "int default '0'")
    private int outboxSpeed;//pat-add
    
    public Outbox() {
    }

    public Outbox(Integer createdBy) {
        super(createdBy);
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

    public LocalDateTime getOutboxSendDate() {
        return outboxSendDate;
    }

    public void setOutboxSendDate(LocalDateTime outboxSendDate) {
        this.outboxSendDate = outboxSendDate;
    }

    public LocalDateTime getOutboxReceiveDate() {
        return outboxReceiveDate;
    }

    public void setOutboxReceiveDate(LocalDateTime outboxReceiveDate) {
        this.outboxReceiveDate = outboxReceiveDate;
    }

    public LocalDateTime getOutboxReceiveDateDefine() {
        return outboxReceiveDateDefine;
    }

    public void setOutboxReceiveDateDefine(LocalDateTime outboxReceiveDateDefine) {
        this.outboxReceiveDateDefine = outboxReceiveDateDefine;
    }

    public int getOutboxReceiveFlag() {
        return outboxReceiveFlag;
    }

    public void setOutboxReceiveFlag(int outboxReceiveFlag) {
        this.outboxReceiveFlag = outboxReceiveFlag;
    }

    public LocalDateTime getOutboxOpenDate() {
        return outboxOpenDate;
    }

    public void setOutboxOpenDate(LocalDateTime outboxOpenDate) {
        this.outboxOpenDate = outboxOpenDate;
    }

    public LocalDateTime getOutboxOpenDateDefine() {
        return outboxOpenDateDefine;
    }

    public void setOutboxOpenDateDefine(LocalDateTime outboxOpenDateDefine) {
        this.outboxOpenDateDefine = outboxOpenDateDefine;
    }

    public int getOutboxOpenFlag() {
        return outboxOpenFlag;
    }

    public void setOutboxOpenFlag(int outboxOpenFlag) {
        this.outboxOpenFlag = outboxOpenFlag;
    }

    public LocalDateTime getOutboxActionDate() {
        return outboxActionDate;
    }

    public void setOutboxActionDate(LocalDateTime outboxActionDate) {
        this.outboxActionDate = outboxActionDate;
    }

    public LocalDateTime getOutboxActionDateDefine() {
        return outboxActionDateDefine;
    }

    public void setOutboxActionDateDefine(LocalDateTime outboxActionDateDefine) {
        this.outboxActionDateDefine = outboxActionDateDefine;
    }

    public int getOutboxActionFlag() {
        return outboxActionFlag;
    }

    public void setOutboxActionFlag(int outboxActionFlag) {
        this.outboxActionFlag = outboxActionFlag;
    }

    public LocalDateTime getOutboxFinishDate() {
        return outboxFinishDate;
    }

    public void setOutboxFinishDate(LocalDateTime outboxFinishDate) {
        this.outboxFinishDate = outboxFinishDate;
    }

    public LocalDateTime getOutboxFinishDateDefine() {
        return outboxFinishDateDefine;
    }

    public void setOutboxFinishDateDefine(LocalDateTime outboxFinishDateDefine) {
        this.outboxFinishDateDefine = outboxFinishDateDefine;
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

    public LocalDateTime getOutboxDate01() {
        return outboxDate01;
    }

    public void setOutboxDate01(LocalDateTime outboxDate01) {
        this.outboxDate01 = outboxDate01;
    }

    public LocalDateTime getOutboxDate02() {
        return outboxDate02;
    }

    public void setOutboxDate02(LocalDateTime outboxDate02) {
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

    public int getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
    }

    public int getOutboxApprove() {
        return outboxApprove;
    }

    public void setOutboxApprove(int outboxApprove) {
        this.outboxApprove = outboxApprove;
    }
    
    public int getOutboxSpeed() {
        return outboxSpeed;
    }

    public void setOutboxSpeed(int outboxSpeed) {
        this.outboxSpeed = outboxSpeed;
    }

}
