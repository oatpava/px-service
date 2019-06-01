
package com.px.mwp.entity;


import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 *
 * @author ken
 */
@Entity
@Table(name = "PC_WORKFLOW_TO")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WORKFLOW_TO_ID"))
})
public class WorkflowTo extends BaseEntity{
    @Transient
    private static final long serialVersionUID = -7221487255342630627L;
    
    @ManyToOne(cascade={CascadeType.DETACH})
    @JoinColumn(name="WORKFLOW_ID", nullable = false)
    private Workflow workflow;
    
    @Column(name = "USER_PROFILE_ID", columnDefinition = "int default '0'")
    private Integer userProfileId;
    
    @Column(name = "STRUCTURE_ID", columnDefinition = "int default '0'")
    private Integer structureId;
    
    @Column(name = "USER_PROFILE_FULL_NAME", nullable = true, length = 255)
    private String userProfileFullName;
    
    @Column(name = "USER_PROFILE_POSITION", nullable = true, length = 255)
    private String userProfilePosition;
    
    @Column(name = "WORKFLOW_TO_RECEIVE_DATE")
    private LocalDateTime workflowToReceiveDate;

    @Column(name = "WORKFLOW_TO_RECEIVE_FLAG", columnDefinition = "int default '0'")
    private int workflowToReceiveFlag;

    @Column(name = "WORKFLOW_TO_OPEN_DATE")
    private LocalDateTime workflowToOpenDate;

    @Column(name = "WORKFLOW_TO_OPEN_FLAG", columnDefinition = "int default '0'")
    private int workflowToOpenFlag;

    @Column(name = "WORKFLOW_TO_ACTION_DATE")
    private LocalDateTime workflowToActionDate;

    @Column(name = "WORKFLOW_TO_ACTION_FLAG", columnDefinition = "int default '0'")
    private int workflowToActionFlag;

    @Column(name = "WORKFLOW_TO_FINISH_DATE")
    private LocalDateTime workflowToFinishDate;

    @Column(name = "WORKFLOW_TO_FINISH_FLAG", columnDefinition = "int default '0'")
    private int workflowToFinishFlag;
    
    public WorkflowTo() {
    }

    public WorkflowTo(Workflow workflow, Integer userProfileId, String userProfileFullName, String userProfilePosition, Integer createdBy) {
        super(createdBy);
        this.workflow = workflow;
        this.userProfileId = userProfileId;
        this.userProfileFullName = userProfileFullName;
        this.userProfilePosition = userProfilePosition;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Integer getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Integer userProfileId) {
        this.userProfileId = userProfileId;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public String getUserProfileFullName() {
        return userProfileFullName;
    }

    public void setUserProfileFullName(String userProfileFullName) {
        this.userProfileFullName = userProfileFullName;
    }

    public String getUserProfilePosition() {
        return userProfilePosition;
    }

    public void setUserProfilePosition(String userProfilePosition) {
        this.userProfilePosition = userProfilePosition;
    }

    public LocalDateTime getWorkflowToReceiveDate() {
        return workflowToReceiveDate;
    }

    public void setWorkflowToReceiveDate(LocalDateTime workflowToReceiveDate) {
        this.workflowToReceiveDate = workflowToReceiveDate;
    }

    public int getWorkflowToReceiveFlag() {
        return workflowToReceiveFlag;
    }

    public void setWorkflowToReceiveFlag(int workflowToReceiveFlag) {
        this.workflowToReceiveFlag = workflowToReceiveFlag;
    }

    public LocalDateTime getWorkflowToOpenDate() {
        return workflowToOpenDate;
    }

    public void setWorkflowToOpenDate(LocalDateTime workflowToOpenDate) {
        this.workflowToOpenDate = workflowToOpenDate;
    }

    public int getWorkflowToOpenFlag() {
        return workflowToOpenFlag;
    }

    public void setWorkflowToOpenFlag(int workflowToOpenFlag) {
        this.workflowToOpenFlag = workflowToOpenFlag;
    }

    public LocalDateTime getWorkflowToActionDate() {
        return workflowToActionDate;
    }

    public void setWorkflowToActionDate(LocalDateTime workflowToActionDate) {
        this.workflowToActionDate = workflowToActionDate;
    }

    public int getWorkflowToActionFlag() {
        return workflowToActionFlag;
    }

    public void setWorkflowToActionFlag(int workflowToActionFlag) {
        this.workflowToActionFlag = workflowToActionFlag;
    }

    public LocalDateTime getWorkflowToFinishDate() {
        return workflowToFinishDate;
    }

    public void setWorkflowToFinishDate(LocalDateTime workflowToFinishDate) {
        this.workflowToFinishDate = workflowToFinishDate;
    }

    public int getWorkflowToFinishFlag() {
        return workflowToFinishFlag;
    }

    public void setWorkflowToFinishFlag(int workflowToFinishFlag) {
        this.workflowToFinishFlag = workflowToFinishFlag;
    }

}
