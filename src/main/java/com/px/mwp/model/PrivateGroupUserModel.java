/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.admin.model.StructureModel;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oat
 */
@XmlRootElement(name = "privateGroupUser")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ผู้ใช้ในกลุ่มส่วนตัว")
public class PrivateGroupUserModel extends VersionModel {

    private static final long serialVersionUID = -5659519110803187929L;

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

    @XmlElement(name = "privateGroupId")
    @ApiParam(name = "privateGroupId", value = "รหัสกลุ่มส่วนตัว", required = true)
    @Since(1.0)
    @Expose
    private int privateGroupId;

    @XmlElement(name = "userId")
    @ApiParam(name = "userId", value = "รหัสผู้ใช้/หน่วยงาน", required = true)
    @Since(1.0)
    @Expose
    private int userId;

    @XmlElement(name = "userName")
    @ApiParam(name = "userName", value = "ชื่อผู้ใช้/หน่วยงาน", required = true)
    @Since(1.0)
    @Expose
    private String userName;

    @XmlElement(name = "userType")
    @ApiParam(name = "userType", value = "ประเภทผู้ใช้/หน่วยงาน", required = true)
    @Since(1.0)
    @Expose
    private int userType;

    @XmlElement(name = "email")
    @ApiParam(name = "email", value = "email", required = false)
    @Since(1.0)
    @Expose
    private String email;

    @XmlElement(name = "structure")
    @ApiParam(name = "structure", value = "structure", required = false)
    @Since(1.0)
    @Expose
    private StructureModel structure;

    public PrivateGroupUserModel() {
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

    public int getPrivateGroupId() {
        return privateGroupId;
    }

    public void setPrivateGroupId(int privateGroupId) {
        this.privateGroupId = privateGroupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {//cant override getUserType
        return userType;
    }

    public void setType(int userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StructureModel getStructure() {
        return structure;
    }

    public void setStructure(StructureModel structureModel) {
        this.structure = structureModel;
    }

}
