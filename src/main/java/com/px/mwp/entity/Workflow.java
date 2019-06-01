
package com.px.mwp.entity;

import com.px.share.entity.BaseEntity;
import java.util.Calendar;
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
@Table(name = "PC_WORKFLOW")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WORKFLOW_ID"))
})
public class Workflow extends BaseEntity {

    @Transient
    private static final long serialVersionUID = 8294295331659843349L;

    @Column(name = "LINK_ID", columnDefinition = "int default '0'")
    private Integer linkId;

    @Column(name = "LINK_ID2", columnDefinition = "int default '0'")
    private Integer linkId2;
    
    @Column(name = "LINK_ID3", columnDefinition = "int default '0'")
    private Integer linkId3;

    @Column(name = "WORKFLOW_ACTION_ID", columnDefinition = "int default '0'")
    private Integer workflowActionId;
    
    @Column(name = "WORKFLOW_ACTION_ID_TYPE", columnDefinition = "int default '0'")
    private Integer workflowActionIdType;

    @Column(name = "WORKFLOW_ACTION_NAME", nullable = true, length = 1000)
    private String workflowActionName;

    @Column(name = "WORKFLOW_ACTION_POSITION", nullable = true, length = 1000)
    private String workflowActionPosition;

    @Column(name = "WORKFLOW_ACTION_TYPE", nullable = true, length = 10)
    private String workflowActionType;

    @Column(name = "WORKFLOW_ACTION_DATE", nullable = false)
    private LocalDateTime workflowActionDate = LocalDateTime.now();

    @Column(name = "WORKFLOW_DESCRIPTION", nullable = true, length = 1000)
    private String workflowDescription;

    @Column(name = "WORKFLOW_NOTE", nullable = true, length = 1000)
    private String workflowNote;

    @Column(name = "WORKFLOW_TITLE", nullable = true, length = 1000)
    private String workflowTitle;

    @Column(name = "WORKFLOW_STR01", nullable = true, length = 1000)
    private String workflowStr01;

    @Column(name = "WORKFLOW_STR02", nullable = true, length = 1000)
    private String workflowStr02;

    @Column(name = "WORKFLOW_STR03", nullable = true, length = 1000)
    private String workflowStr03;

    @Column(name = "WORKFLOW_STR04", nullable = true, length = 1000)
    private String workflowStr04;
    
    @Column(name = "WORKFLOW_DATE01", nullable = true)
    private LocalDateTime workflowDate01;

    @Column(name = "WORKFLOW_DATE02", nullable = true)
    private LocalDateTime workflowDate02;
    
    @Column(name = "CONVERT_ID", columnDefinition = "int default '0'")
    private Integer convertId; 

    public Workflow() {
    }

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    public Integer getLinkId2() {
        return linkId2;
    }

    public void setLinkId2(Integer linkId2) {
        this.linkId2 = linkId2;
    }

    public Integer getLinkId3() {
        return linkId3;
    }

    public void setLinkId3(Integer linkId3) {
        this.linkId3 = linkId3;
    }

    public Integer getWorkflowActionId() {
        return workflowActionId;
    }

    public void setWorkflowActionId(Integer workflowActionId) {
        this.workflowActionId = workflowActionId;
    }

    public Integer getWorkflowActionIdType() {
        return workflowActionIdType;
    }

    public void setWorkflowActionIdType(Integer workflowActionIdType) {
        this.workflowActionIdType = workflowActionIdType;
    }

    public String getWorkflowActionName() {
        return workflowActionName;
    }

    public void setWorkflowActionName(String workflowActionName) {
        this.workflowActionName = workflowActionName;
    }

    public String getWorkflowActionPosition() {
        return workflowActionPosition;
    }

    public void setWorkflowActionPosition(String workflowActionPosition) {
        this.workflowActionPosition = workflowActionPosition;
    }

    public String getWorkflowActionType() {
        return workflowActionType;
    }

    public void setWorkflowActionType(String workflowActionType) {
        this.workflowActionType = workflowActionType;
    }

    public LocalDateTime getWorkflowActionDate() {
        return workflowActionDate;
    }

    public void setWorkflowActionDate(LocalDateTime workflowActionDate) {
        this.workflowActionDate = workflowActionDate;
    }

    public String getWorkflowDescription() {
        return workflowDescription;
    }

    public void setWorkflowDescription(String workflowDescription) {
        this.workflowDescription = workflowDescription;
    }

    public String getWorkflowNote() {
        return workflowNote;
    }

    public void setWorkflowNote(String workflowNote) {
        this.workflowNote = workflowNote;
    }

    public String getWorkflowTitle() {
        return workflowTitle;
    }

    public void setWorkflowTitle(String workflowTitle) {
        this.workflowTitle = workflowTitle;
    }

    public String getWorkflowStr01() {
        return workflowStr01;
    }

    public void setWorkflowStr01(String workflowStr01) {
        this.workflowStr01 = workflowStr01;
    }

    public String getWorkflowStr02() {
        return workflowStr02;
    }

    public void setWorkflowStr02(String workflowStr02) {
        this.workflowStr02 = workflowStr02;
    }

    public String getWorkflowStr03() {
        return workflowStr03;
    }

    public void setWorkflowStr03(String workflowStr03) {
        this.workflowStr03 = workflowStr03;
    }

    public String getWorkflowStr04() {
        return workflowStr04;
    }

    public void setWorkflowStr04(String workflowStr04) {
        this.workflowStr04 = workflowStr04;
    }

    public LocalDateTime getWorkflowDate01() {
        return workflowDate01;
    }

    public void setWorkflowDate01(LocalDateTime workflowDate01) {
        this.workflowDate01 = workflowDate01;
    }

    public LocalDateTime getWorkflowDate02() {
        return workflowDate02;
    }

    public void setWorkflowDate02(LocalDateTime workflowDate02) {
        this.workflowDate02 = workflowDate02;
    }

    public Integer getConvertId() {
        return convertId;
    }

    public void setConvertId(Integer convertId) {
        this.convertId = convertId;
    }
   
}
