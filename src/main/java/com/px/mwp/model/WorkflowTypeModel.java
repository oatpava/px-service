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
@XmlRootElement(name = "WorkflowType")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ประเภทของการดำเนินการ")
public class WorkflowTypeModel extends VersionModel {

    private static final long serialVersionUID = 2002692372278830805L;

    @XmlElement(name = "id")
    @Expose
    @Since(1.0)
    private int id;
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

    @XmlElement(name = "workflowTypeTitle")
    @ApiParam(name = "workflowTypeTitle", value = "ชื่อประเภทของการดำเนินการ", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String workflowTypeTitle;

    @XmlElement(name = "workflowTypeActionType")
    @ApiParam(name = "workflowTypeActionType", value = "รายละเอียดประเภทของการใช้งาน", required = false)
    @Size(max = 50)
    @Since(1.0)
    @Expose
    private String workflowTypeActionType;
    
    @XmlElement(name = "workflowTypeAction")
//    @ApiParam(name = "workflowTypeAction", value = "ประเภทของการใช้งาน", required = true)
    @ApiParam(name = "workflowTypeAction", value = "ประเภทของการใช้งาน", required = false)//oat-edit
    @Expose 
    @Since(1.0)
    private int workflowTypeAction;

    public WorkflowTypeModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getWorkflowTypeTitle() {
        return workflowTypeTitle;
    }

    @ApiModelProperty(name = "workflowTypeTitle", example = "ทดสอบ", dataType = "string", value = "ทดสอบ", required = false)
    public void setWorkflowTypeTitle(String workflowTypeTitle) {
        this.workflowTypeTitle = workflowTypeTitle;
    }

    public String getWorkflowTypeActionType() {
        return workflowTypeActionType;
    }

    @ApiModelProperty(name = "workflowTypeActionType", example = " ", dataType = "string", value = "รายละเอียดประเภทของการใช้งาน", required = false)
    public void setWorkflowTypeActionType(String workflowTypeActionType) {
        this.workflowTypeActionType = workflowTypeActionType;
    }
    
    public int getWorkflowTypeAction() {
        return workflowTypeAction;
    }

    @ApiModelProperty(name = "workflowTypeAction", example = "0", dataType = "int", value = "ประเภทของการใช้งาน", required = true)
    public void setWorkflowTypeAction(int workflowTypeAction) {
        this.workflowTypeAction = workflowTypeAction;
    }
}
