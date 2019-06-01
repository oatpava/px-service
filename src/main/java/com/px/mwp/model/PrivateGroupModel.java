/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oat
 */
@XmlRootElement(name = "privateGroup")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "กลุ่มส่วนตัว")
public class PrivateGroupModel extends VersionModel {

    private static final long serialVersionUID = 1930250970195364772L;

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

    @XmlElement(name = "ownerId")
    @ApiParam(name = "ownerId", value = "รหัสเจ้าของกลุ่มส่วนตัว", required = false)
    @Since(1.0)
    @Expose
    private int ownerId;

    @XmlElement(name = "groupName")
    @ApiParam(name = "groupName", value = "ชื่อกลุ่ม", required = false)
    @Since(1.0)
    @Expose
    private String groupName;

    @XmlElement(name = "type")
    @ApiParam(name = "type", value = "ประเภท (pg/fav)", required = false)
    @Since(1.0)
    @Expose
    private int type;

    @XmlElement(name = "listUser")
    @Expose
    private List<PrivateGroupUserModel> listUser;

    public PrivateGroupModel() {
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

    public int getOwnerId() {
        return ownerId;
    }

    //@ApiModelProperty(name = "ownerId", example = "1", dataType = "int", value = "รหัสเจ้าของกลุ่มส่วนตัว", required = true)
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<PrivateGroupUserModel> getListUser() {
        return listUser;
    }

    public void setListUser(List<PrivateGroupUserModel> listUser) {
        this.listUser = listUser;
    }
}
