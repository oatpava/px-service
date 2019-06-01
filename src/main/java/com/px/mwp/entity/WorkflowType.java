package com.px.mwp.entity;


import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Mali
 */
@Entity
@Table(name = "PC_WORKFLOW_TYPE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WORKFLOW_TYPE_ID"))
})
public class WorkflowType extends BaseEntity {

    @Transient
    private static final long serialVersionUID = 5700131879678920298L;

    @Column(name = "WORKFLOW_TYPE_TITLE", nullable = true, length = 255)
    private String workflowTypeTitle;

    @Column(name = "WORKFLOW_TYPE_ACTION_TYPE", nullable = true, length = 50)
    private String workflowTypeActionType;
    
    @Column(name = "WORKFLOW_TYPE_ACTION", columnDefinition = "int default '0'")
    private int workflowTypeAction;

    public WorkflowType() {
    }

    public String getWorkflowTypeTitle() {
        return workflowTypeTitle;
    }

    public void setWorkflowTypeTitle(String workflowTypeTitle) {
        this.workflowTypeTitle = workflowTypeTitle;
    }

    public String getWorkflowTypeActionType() {
        return workflowTypeActionType;
    }

    public void setWorkflowTypeActionType(String workflowTypeActionType) {
        this.workflowTypeActionType = workflowTypeActionType;
    }
    
    public int getWorkflowTypeAction() {
        return workflowTypeAction;
    }

    public void setWorkflowTypeAction(int workflowTypeAction) {
        this.workflowTypeAction = workflowTypeAction;
    }
}
