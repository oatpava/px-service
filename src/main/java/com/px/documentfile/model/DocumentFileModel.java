/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.documentfile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TOP
 */
@XmlRootElement(name = "DocumentFile")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "เอกสารแนบ")
public class DocumentFileModel extends VersionModel {

    public static final long serialVersionUID = 0L;

    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัส", defaultValue = "0", required = false)
    @Expose
    @Since(1.0)
    private int id;

    @ApiParam(name = "moduleId", value = "moduleId", required = true, example = "1")
    @XmlElement(name = "moduleId")
    @Expose
    @Since(1.0)
    private int moduleId;

    @ApiParam(name = "linkId", value = "linkId", required = true, example = "1")
    @XmlElement(name = "linkId")
    @Expose
    @Since(1.0)
    private int linkId;

    @ApiParam(name = "linkId", value = "linkId", required = true, example = "1")
    @XmlElement(name = "linkId2")
    @Expose
    @Since(1.0)
    private int linkId2;

    @XmlElement(name = "linkType")
    @ApiParam(name = "linkType", value = "linkType", required = false)
    @Since(1.0)
    @Expose
    private String linkType;

    @XmlElement(name = "fileOrder")
    @ApiParam(name = "fileOrder", value = "ลำดับเอกสาร", required = false)
    @Since(1.0)
    @Expose
    private int fileOrder;

    @XmlElement(name = "documentFileName")
    @ApiParam(name = "documentFileName", value = "ชื่อเอกสารแนบ", required = false)
    @Since(1.0)
    @Expose
    private String documentFileName;

    @XmlElement(name = "documentFileType")
    @ApiParam(name = "documentFileType", value = "ชนิดเอกสารแนบ", required = false)
    @Since(1.0)
    @Expose
    private String documentFileType;

    @XmlElement(name = "caption")
    @ApiParam(name = "caption", value = "ชื่อหัวข้อเอกสารแนบ", required = false)
    @Since(1.0)
    @Expose
    private String caption;

    @XmlElement(name = "ownerName")
    @ApiParam(name = "ownerName", value = "ชื่อเจ้าของ", required = false)
    @Since(1.0)
    private String ownerName;

    @XmlElement(name = "isText")
    @ApiParam(name = "isText", value = "มีคำบรรยายไหม", required = false)
    @Since(1.0)
    @Expose
    private int isText;

    @XmlElement(name = "imageCount")
    @ApiParam(name = "imageCount", value = "จำนวนรูปภาพ", required = false)
    @FormParam("imageCount")
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private int imageCount;

    @XmlElement(name = "documentFileSec")
    @ApiParam(name = "documentFileSec", value = "ชั้นความลับ", required = false)
    @FormParam("documentFileSec")
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private int documentFileSec;

    @XmlElement(name = "fileSize")
    @ApiParam(name = "fileSize", value = "ขนากเอกสารแนบ", required = false)
    @FormParam("fileSize")
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private int fileSize;

    @XmlElement(name = "md5")
    @ApiParam(name = "md5", value = "md5", required = false)
    @Since(1.0)
    @Expose
    private String md5;

    public DocumentFileModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัส", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public int getLinkId() {
        return linkId;
    }

   @ApiModelProperty(name = "linkId", example = "1", dataType = "int", value = "linkId", required = true)
    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }
    
    public int getModuleId() {
        return moduleId;
    }

    @ApiModelProperty(name = "moduleId", example = "1", dataType = "int", value = "moduleId", required = true)
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getLinkId2() {
        return linkId2;
    }

      @ApiModelProperty(name = "linkId2", example = "1", dataType = "int", value = "linkId2", required = true)
    public void setLinkId2(int linkId2) {
        this.linkId2 = linkId2;
    }

    public String getLinkType() {
        return linkType;
    }

      @ApiModelProperty(name = "linkType", example = "1", dataType = "String", value = "linkType", required = false)
    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public int getFileOrder() {
        return fileOrder;
    }

     @ApiModelProperty(name = "fileOrder", example = "1", dataType = "int", value = "เรียงลำดับเอกสารแนบ", required = false)
    public void setFileOrder(int fileOrder) {
        this.fileOrder = fileOrder;
    }

    public String getDocumentFileName() {
        return documentFileName;
    }

     @ApiModelProperty(name = "documentFileName", example = "เอกสารแนบ 1", dataType = "String", value = "ชื่อเอกสารแนบ", required = false)
    public void setDocumentFileName(String documentFileName) {
        this.documentFileName = documentFileName;
    }

    public String getDocumentFileType() {
        return documentFileType;
    }

     @ApiModelProperty(name = "documentFileType", example = ".RTF", dataType = "String", value = "ประเภทเอกสารแนบ", required = false)
    public void setDocumentFileType(String documentFileType) {
        this.documentFileType = documentFileType;
    }

    public String getCaption() {
        return caption;
    }

    @ApiModelProperty(name = "caption", example = "caption", dataType = "String", value = "caption", required = false)
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getOwnerName() {
        return ownerName;
    }

     @ApiModelProperty(name = "ownerName", example = "นาย ก", dataType = "String", value = "ชื่อเจ้าของ", required = false)
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getIsText() {
        return isText;
    }

     @ApiModelProperty(name = "isText", example = "1", dataType = "int", value = "มีคำบรรยายไหม", required = false)
    public void setIsText(int isText) {
        this.isText = isText;
    }

    public int getImageCount() {
        return imageCount;
    }
 @ApiModelProperty(name = "imageCount", example = "1", dataType = "int", value = "จำนวนรูป", required = false)
    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public int getDocumentFileSec() {
        return documentFileSec;
    }

     @ApiModelProperty(name = "documentFileSec", example = "1", dataType = "int", value = "ชั้นความลับ", required = false)
    public void setDocumentFileSec(int documentFileSec) {
        this.documentFileSec = documentFileSec;
    }

    public int getFileSize() {
        return fileSize;
    }

     @ApiModelProperty(name = "fileSize", example = "255", dataType = "int", value = "ขนาด file", required = false)
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getMd5() {
        return md5;
    }

     @ApiModelProperty(name = "md5",  dataType = "String", value = "md5", required = false)
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    

}
