/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 *
 * @author ken
 */
@Entity
@Table(name = "PC_INBOX")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "INBOX_ID"))
})
public class Inbox extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -2566260439074377440L;

    @Column(name = "USER_PROFILE_FOLDER_ID", columnDefinition = "int default '0'")
    private int userProfileFolderId;

    @Column(name = "STRUCTURE_FOLDER_ID", columnDefinition = "int default '0'")
    private int structureFolderId;

    @Column(name = "INBOX_FROM", length = 1000)
    private String inboxFrom;

    @Column(name = "INBOX_TO", length = 1000)
    private String inboxTo;

    @Column(name = "INBOX_TITLE", length = 1000)
    private String inboxTitle;

    @Column(name = "INBOX_SEND_DATE")
    private LocalDateTime inboxSendDate;

    @Column(name = "INBOX_RECEIVE_DATE")
    private LocalDateTime inboxReceiveDate;

    @Column(name = "INBOX_RECEIVE_DATE_DEFINE")
    private LocalDateTime inboxReceiveDateDefine;

    @Column(name = "INBOX_RECEIVE_FLAG", columnDefinition = "int default '0'")
    private int inboxReceiveFlag;

    @Column(name = "INBOX_OPEN_DATE")
    private LocalDateTime inboxOpenDate;

    @Column(name = "INBOX_OPEN_DATE_DEFINE")
    private LocalDateTime inboxOpenDateDefine;

    @Column(name = "INBOX_OPEN_FLAG", columnDefinition = "int default '0'")
    private int inboxOpenFlag;

    @Column(name = "INBOX_ACTION_DATE")
    private LocalDateTime inboxActionDate;

    @Column(name = "INBOX_ACTION_DATE_DEFINE")
    private LocalDateTime inboxActionDateDefine;

    @Column(name = "INBOX_ACTION_FLAG", columnDefinition = "int default '0'")
    private int inboxActionFlag;

    @Column(name = "INBOX_FINISH_DATE")
    private LocalDateTime inboxFinishDate;

    @Column(name = "INBOX_FINISH_DATE_DEFINE")
    private LocalDateTime inboxFinishDateDefine;

    @Column(name = "INBOX_FINISH_FLAG", columnDefinition = "int default '0'")
    private int inboxFinishFlag;

    @Column(name = "LETTER_STATUS_1", columnDefinition = "int default '0'")
    private int letterStatus1;

    @Column(name = "LETTER_STATUS_2", columnDefinition = "int default '0'")
    private int letterStatus2;

    @Column(name = "LETTER_STATUS_3", columnDefinition = "int default '0'")
    private int letterStatus3;

    @Column(name = "MODULE_ID", columnDefinition = "int default '0'")
    private int moduleId;

    @Column(name = "LINK_ID", columnDefinition = "int default '0'")
    private int linkId;

    @Column(name = "LINK_ID2", columnDefinition = "int default '0'")
    private int linkId2;

    @Column(name = "LINK_TYPE", length = 10)
    private String linkType;

    @Column(name = "INBOX_STR01", length = 255)
    private String inboxStr01;

    @Column(name = "INBOX_STR02", length = 255)
    private String inboxStr02;

    @Column(name = "INBOX_STR03", length = 255)
    private String inboxStr03;

    @Column(name = "INBOX_STR04", length = 255)
    private String inboxStr04;

    @Column(name = "INBOX_DATE01", nullable = true)
    private LocalDateTime inboxDate01;

    @Column(name = "INBOX_DATE02", nullable = true)
    private LocalDateTime inboxDate02;

    @Column(name = "INBOX_DESCRIPTION", nullable = true, length = 1000)
    private String inboxDescription;

    @Column(name = "INBOX_NOTE", nullable = true, length = 1000)
    private String inboxNote;
    
    @Column(name = "WORKFLOW_ID", columnDefinition = "int default '0'")
    private int workflowId;
    
    @Column(name = "INBOX_KEY", nullable = true, length = 255)
    private String inboxKey;

    @Column(name = "INBOX_APPROVE", columnDefinition = "int default '0'")
    private int inboxApprove;
    
    @Column(name = "INBOX_APPROVE_STATUS", columnDefinition = "int default '0'")
    private int inboxApproveStatus;
    
    @Column(name = "INBOX_SPEED", columnDefinition = "int default '0'")
    private int inboxSpeed;//oat-add
    
    public Inbox() {
    }

    public Inbox(Integer createdBy) {
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

    public LocalDateTime getInboxSendDate() {
        return inboxSendDate;
    }

    public void setInboxSendDate(LocalDateTime inboxSendDate) {
        this.inboxSendDate = inboxSendDate;
    }

    public LocalDateTime getInboxReceiveDate() {
        return inboxReceiveDate;
    }

    public void setInboxReceiveDate(LocalDateTime inboxReceiveDate) {
        this.inboxReceiveDate = inboxReceiveDate;
    }

    public LocalDateTime getInboxReceiveDateDefine() {
        return inboxReceiveDateDefine;
    }

    public void setInboxReceiveDateDefine(LocalDateTime inboxReceiveDateDefine) {
        this.inboxReceiveDateDefine = inboxReceiveDateDefine;
    }

    public int getInboxReceiveFlag() {
        return inboxReceiveFlag;
    }

    public void setInboxReceiveFlag(int inboxReceiveFlag) {
        this.inboxReceiveFlag = inboxReceiveFlag;
    }

    public LocalDateTime getInboxOpenDate() {
        return inboxOpenDate;
    }

    public void setInboxOpenDate(LocalDateTime inboxOpenDate) {
        this.inboxOpenDate = inboxOpenDate;
    }

    public LocalDateTime getInboxOpenDateDefine() {
        return inboxOpenDateDefine;
    }

    public void setInboxOpenDateDefine(LocalDateTime inboxOpenDateDefine) {
        this.inboxOpenDateDefine = inboxOpenDateDefine;
    }

    public int getInboxOpenFlag() {
        return inboxOpenFlag;
    }

    public void setInboxOpenFlag(int inboxOpenFlag) {
        this.inboxOpenFlag = inboxOpenFlag;
    }

    public LocalDateTime getInboxActionDate() {
        return inboxActionDate;
    }

    public void setInboxActionDate(LocalDateTime inboxActionDate) {
        this.inboxActionDate = inboxActionDate;
    }

    public LocalDateTime getInboxActionDateDefine() {
        return inboxActionDateDefine;
    }

    public void setInboxActionDateDefine(LocalDateTime inboxActionDateDefine) {
        this.inboxActionDateDefine = inboxActionDateDefine;
    }

    public int getInboxActionFlag() {
        return inboxActionFlag;
    }

    public void setInboxActionFlag(int inboxActionFlag) {
        this.inboxActionFlag = inboxActionFlag;
    }

    public LocalDateTime getInboxFinishDate() {
        return inboxFinishDate;
    }

    public void setInboxFinishDate(LocalDateTime inboxFinishDate) {
        this.inboxFinishDate = inboxFinishDate;
    }

    public LocalDateTime getInboxFinishDateDefine() {
        return inboxFinishDateDefine;
    }

    public void setInboxFinishDateDefine(LocalDateTime inboxFinishDateDefine) {
        this.inboxFinishDateDefine = inboxFinishDateDefine;
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

    public LocalDateTime getInboxDate01() {
        return inboxDate01;
    }

    public void setInboxDate01(LocalDateTime inboxDate01) {
        this.inboxDate01 = inboxDate01;
    }

    public LocalDateTime getInboxDate02() {
        return inboxDate02;
    }

    public void setInboxDate02(LocalDateTime inboxDate02) {
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

    public int getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
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
    
    public int getInboxSpeed() {
        return inboxSpeed;
    }

    public void setInboxSpeed(int inboxSpeed) {
        this.inboxSpeed = inboxSpeed;
    }
    
}
