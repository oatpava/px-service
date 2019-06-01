
package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "WfCommandName")
@ApiModel(description = "รายการชื่อของคำสั่ง/ประกาศ/ระเบียบ")
public class WfCommandNameModel extends VersionModel {

    private static final long serialVersionUID = -3078608968834468100L;
    
    @XmlElement(name = "id")
    @Expose @Since(1.0) private int id;
    @Expose @Since(1.0) private int createdBy;
    @Expose @Since(1.0) private String createdDate;
    @Expose @Since(1.0) private float orderNo;
    @Expose @Since(1.0) private int removedBy;
    @Expose @Since(1.0) private String removedDate;
    @Expose @Since(1.0) private int updatedBy;
    @Expose @Since(1.0) private String updatedDate;
    
    @XmlElement(name = "wfCommandNameName")
    @ApiParam(name = "wfCommandNameName", value = "ชื่อของคำสั่ง/ประกาศ/ระเบียบ", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose private String wfCommandNameName;

    public WfCommandNameModel() {
    }
    
    public WfCommandNameModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสรายการชื่อของคำสั่ง/ประกาศ/ระเบียบ", required = true)
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

    public String getWfCommandNameName() {
        return wfCommandNameName;
    }

    public void setWfCommandNameName(String wfCommandNameName) {
        this.wfCommandNameName = wfCommandNameName;
    }
}
