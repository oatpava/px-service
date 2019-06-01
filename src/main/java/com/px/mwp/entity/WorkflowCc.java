
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
@Table(name = "PC_WORKFLOW_CC")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WORKFLOW_CC_ID"))
})
public class WorkflowCc extends BaseEntity{
    @Transient
    private static final long serialVersionUID = 4501854752801104140L;
    
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
    
    @Column(name = "WORKFLOW_CC_RECEIVE_DATE")
    private LocalDateTime workflowCcReceiveDate;

    @Column(name = "WORKFLOW_CC_RECEIVE_FLAG", columnDefinition = "int default '0'")
    private int workflowCcReceiveFlag;

    @Column(name = "WORKFLOW_CC_OPEN_DATE")
    private LocalDateTime workflowCcOpenDate;

    @Column(name = "WORKFLOW_CC_OPEN_FLAG", columnDefinition = "int default '0'")
    private int workflowCcOpenFlag;

    @Column(name = "WORKFLOW_CC_ACTION_DATE")
    private LocalDateTime workflowCcActionDate;

    @Column(name = "WORKFLOW_CC_ACTION_FLAG", columnDefinition = "int default '0'")
    private int workflowCcActionFlag;

    @Column(name = "WORKFLOW_CC_FINISH_DATE")
    private LocalDateTime workflowCcFinishDate;

    @Column(name = "WORKFLOW_CC_FINISH_FLAG", columnDefinition = "int default '0'")
    private int workflowCcFinishFlag;

    public WorkflowCc() {
    }

    public WorkflowCc(Workflow workflow, Integer userProfileId, String userProfileFullName, String userProfilePosition, Integer createdBy) {
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

    public LocalDateTime getWorkflowCcReceiveDate() {
        return workflowCcReceiveDate;
    }

    public void setWorkflowCcReceiveDate(LocalDateTime workflowCcReceiveDate) {
        this.workflowCcReceiveDate = workflowCcReceiveDate;
    }

    public int getWorkflowCcReceiveFlag() {
        return workflowCcReceiveFlag;
    }

    public void setWorkflowCcReceiveFlag(int workflowCcReceiveFlag) {
        this.workflowCcReceiveFlag = workflowCcReceiveFlag;
    }

    public LocalDateTime getWorkflowCcOpenDate() {
        return workflowCcOpenDate;
    }

    public void setWorkflowCcOpenDate(LocalDateTime workflowCcOpenDate) {
        this.workflowCcOpenDate = workflowCcOpenDate;
    }

    public int getWorkflowCcOpenFlag() {
        return workflowCcOpenFlag;
    }

    public void setWorkflowCcOpenFlag(int workflowCcOpenFlag) {
        this.workflowCcOpenFlag = workflowCcOpenFlag;
    }

    public LocalDateTime getWorkflowCcActionDate() {
        return workflowCcActionDate;
    }

    public void setWorkflowCcActionDate(LocalDateTime workflowCcActionDate) {
        this.workflowCcActionDate = workflowCcActionDate;
    }

    public int getWorkflowCcActionFlag() {
        return workflowCcActionFlag;
    }

    public void setWorkflowCcActionFlag(int workflowCcActionFlag) {
        this.workflowCcActionFlag = workflowCcActionFlag;
    }

    public LocalDateTime getWorkflowCcFinishDate() {
        return workflowCcFinishDate;
    }

    public void setWorkflowCcFinishDate(LocalDateTime workflowCcFinishDate) {
        this.workflowCcFinishDate = workflowCcFinishDate;
    }

    public int getWorkflowCcFinishFlag() {
        return workflowCcFinishFlag;
    }

    public void setWorkflowCcFinishFlag(int workflowCcFinishFlag) {
        this.workflowCcFinishFlag = workflowCcFinishFlag;
    }

}
