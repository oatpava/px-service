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
@XmlRootElement(name = "wfFolder")
@ApiModel(description = "ทะเบียนสารบรรณ")
public class WfFolderModel extends VersionModel {

    private static final long serialVersionUID = -2443067835898627821L;

    @XmlElement(name = "id")
    @Expose
    @Since(1.0)
    private int id;
    @Expose
    @Since(1.0)
    private int wfFolderParentType;
    @Expose
    @Since(1.0)
    private String parentKey;
    @Expose
    @Since(1.0)
    private int nodeLevel;
    @XmlElement(name = "wfContentType")
    @Expose
    @Since(1.0)
    private WfContentTypeModel wfContentType;
    @XmlElement(name = "wfContentType2")
    @Expose
    @Since(1.0)
    private WfContentType2Model wfContentType2;
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
    @Expose
    @Since(1.0)
    private String wfFolderExpireDate;
    @Expose
    @Since(1.0)
    private String wfFolderOwnerName;
    @Expose
    @Since(1.0)
    private int convertId;

    @XmlElement(name = "wfFolderName")
    @ApiParam(name = "wfFolderName", value = "ชื่อแฟ้มทะเบียน", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfFolderName;

    @XmlElement(name = "wfFolderType")
    @ApiParam(name = "wfFolderType", value = "ประเภททะเบียน", required = true)
    @Size(max = 10)
    @Since(1.0)
    @Expose
    private String wfFolderType;

    @XmlElement(name = "parentId")
    @ApiParam(name = "parentId", value = "รหัสแม่ของทะเบียน", required = true)
    @Since(1.0)
    @Expose
    private int parentId;

    @XmlElement(name = "wfFolderParentName")
    @ApiParam(name = "wfFolderParentName", value = "ชื่อแม่ของทะเบียน", required = false)
    @Since(1.0)
    @Expose
    private String wfFolderParentName;

    @XmlElement(name = "wfFolderDetail")
    @ApiParam(name = "wfFolderDetail", value = "รายละเอียด", required = true)
    @Size(max = 1000)
    @Since(1.0)
    @Expose
    private String wfFolderDetail;

    @XmlElement(name = "wfFolderAutorun")
    @ApiParam(name = "wfFolderAutorun", value = "ออกเลขที่หนังสืออัตโนมัติ 0=ไม่ออก,1=ออก", required = true)
    @Since(1.0)
    @Expose
    private int wfFolderAutorun;

    @XmlElement(name = "wfFolderBookNoType")
    @ApiParam(name = "wfFolderBookNoType", value = "ประเภทเลขที่หนังสือ 1=ตัวอักษร/ตัวเลข 2=ตัวอักษร/ตัวเลข/ปี 3=free style", required = true)
    @Since(1.0)
    @Expose
    private int wfFolderBookNoType;

    @XmlElement(name = "wfFolderPreBookNo")
    @ApiParam(name = "wfFolderPreBookNo", value = "เลขที่หนังสือขึ้นต้นด้วย", required = false)
    @Size(max = 1000)
    @Since(1.0)
    @Expose
    private String wfFolderPreBookNo;

    @XmlElement(name = "wfFolderPreContentNo")
    @ApiParam(name = "wfFolderPreContentNo", value = "เลขทะเบียนขึ้นต้นด้วย", required = false)
    @Size(max = 50)
    @Since(1.0)
    @Expose
    private String wfFolderPreContentNo;

    @XmlElement(name = "wfFolderOwnerId")
    @ApiParam(name = "wfFolderOwnerId", value = "รหัสผู้สร้าง", required = true)
    @Since(1.0)
    @Expose
    private int wfFolderOwnerId;

    @XmlElement(name = "wfFolderLinkFolderId")
    @ApiParam(name = "wfFolderLinkFolderId", value = "เชื่อมโยงรหัสทะเบียนสารบรรณหลัก", required = false)
    @Since(1.0)
    @Expose
    private int wfFolderLinkFolderId;

    @XmlElement(name = "wfFolderLinkId")
    @ApiParam(name = "wfFolderLinkId", value = "รหัสเชื่อมโยงทั่วไป", required = true)
    @Since(1.0)
    @Expose
    private int wfFolderLinkId;

    @XmlElement(name = "wfFolderByBudgetYear")
    @ApiParam(name = "wfFolderByBudgetYear", value = "รูปแบบปีของเลขทะเบียน 0=ไม่ใช้ ,1=ใช้", required = false)
    @Since(1.0)
    @Expose
    private int wfFolderByBudgetYear;

    @XmlElement(name = "wfFolderTypeYearExpire")
    @ApiParam(name = "wfFolderTypeYearExpire", value = "กำหนดวันหมดอายุ ประเภท", required = false)
    @Size(max = 50)
    @Since(1.0)
    @Expose
    private String wfFolderTypeYearExpire;

    @XmlElement(name = "wfFolderNumYearExpire")
    @ApiParam(name = "wfFolderNumYearExpire", value = "กำหนดวันหมดอายุ จำนวน", required = false)
    @Since(1.0)
    @Expose
    private int wfFolderNumYearExpire;

    //oat-add
    @XmlElement(name = "auth")
    @Expose
    private boolean[] auth;

    //oat-add
    @XmlElement(name = "searchField")
    @Expose
    private boolean[] searchField;

    public WfFolderModel() {
    }

    public WfFolderModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสทะเบียนสารบรรณ", required = true)
    public void setId(int id) {
        this.id = id;
    }

    public int getWfFolderParentType() {
        return wfFolderParentType;
    }

    public void setWfFolderParentType(int wfFolderParentType) {
        this.wfFolderParentType = wfFolderParentType;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public int getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public WfContentTypeModel getWfContentType() {
        return wfContentType;
    }

    public void setWfContentType(WfContentTypeModel wfContentType) {
        this.wfContentType = wfContentType;
    }

    public WfContentType2Model getWfContentType2() {
        return wfContentType2;
    }

    public void setWfContentType2(WfContentType2Model wfContentType2) {
        this.wfContentType2 = wfContentType2;
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

    public String getWfFolderExpireDate() {
        return wfFolderExpireDate;
    }

    public void setWfFolderExpireDate(String wfFolderExpireDate) {
        this.wfFolderExpireDate = wfFolderExpireDate;
    }

    public String getWfFolderOwnerName() {
        return wfFolderOwnerName;
    }

    public void setWfFolderOwnerName(String wfFolderOwnerName) {
        this.wfFolderOwnerName = wfFolderOwnerName;
    }

    public int getConvertId() {
        return convertId;
    }

    public void setConvertId(int convertId) {
        this.convertId = convertId;
    }

    public String getWfFolderName() {
        return wfFolderName;
    }

    public void setWfFolderName(String wfFolderName) {
        this.wfFolderName = wfFolderName;
    }

    public String getWfFolderType() {
        return wfFolderType;
    }

    public void setWfFolderType(String wfFolderType) {
        this.wfFolderType = wfFolderType;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getWfFolderParentName() {
        return wfFolderParentName;
    }

    public void setWfFolderParentName(String wfFolderParentName) {
        this.wfFolderParentName = wfFolderParentName;
    }

    public String getWfFolderDetail() {
        return wfFolderDetail;
    }

    public void setWfFolderDetail(String wfFolderDetail) {
        this.wfFolderDetail = wfFolderDetail;
    }

    public int getWfFolderAutorun() {
        return wfFolderAutorun;
    }

    public void setWfFolderAutorun(int wfFolderAutorun) {
        this.wfFolderAutorun = wfFolderAutorun;
    }

    public int getWfFolderBookNoType() {
        return wfFolderBookNoType;
    }

    public void setWfFolderBookNoType(int wfFolderBookNoType) {
        this.wfFolderBookNoType = wfFolderBookNoType;
    }

    public String getWfFolderPreBookNo() {
        return wfFolderPreBookNo;
    }

    public void setWfFolderPreBookNo(String wfFolderPreBookNo) {
        this.wfFolderPreBookNo = wfFolderPreBookNo;
    }

    public String getWfFolderPreContentNo() {
        return wfFolderPreContentNo;
    }

    public void setWfFolderPreContentNo(String wfFolderPreContentNo) {
        this.wfFolderPreContentNo = wfFolderPreContentNo;
    }

    public int getWfFolderOwnerId() {
        return wfFolderOwnerId;
    }

    public void setWfFolderOwnerId(int wfFolderOwnerId) {
        this.wfFolderOwnerId = wfFolderOwnerId;
    }

    public int getWfFolderLinkFolderId() {
        return wfFolderLinkFolderId;
    }

    public void setWfFolderLinkFolderId(int wfFolderLinkFolderId) {
        this.wfFolderLinkFolderId = wfFolderLinkFolderId;
    }

    public int getWfFolderLinkId() {
        return wfFolderLinkId;
    }

    public void setWfFolderLinkId(int wfFolderLinkId) {
        this.wfFolderLinkId = wfFolderLinkId;
    }

    public int getWfFolderByBudgetYear() {
        return wfFolderByBudgetYear;
    }

    public void setWfFolderByBudgetYear(int wfFolderByBudgetYear) {
        this.wfFolderByBudgetYear = wfFolderByBudgetYear;
    }

    public String getWfFolderTypeYearExpire() {
        return wfFolderTypeYearExpire;
    }

    public void setWfFolderTypeYearExpire(String wfFolderTypeYearExpire) {
        this.wfFolderTypeYearExpire = wfFolderTypeYearExpire;
    }

    public int getWfFolderNumYearExpire() {
        return wfFolderNumYearExpire;
    }

    public void setWfFolderNumYearExpire(int wfFolderNumYearExpire) {
        this.wfFolderNumYearExpire = wfFolderNumYearExpire;
    }

    public boolean[] getAuth() {
        return auth;
    }

    public void setAuth(boolean[] auth) {
        this.auth = auth;
    }

    public boolean[] getSearchField() {
        return searchField;
    }

    public void setSearchField(boolean[] searchField) {
        this.searchField = searchField;
    }

}
