/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TOP
 */
@XmlRootElement(name = "Document Search")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ค้นหาเอกสาร")
public class FieldSearchModel extends  VersionModel{
     public static final long serialVersionUID = 0L;
    
    @XmlElement(name = "documentName")
    @ApiParam(name = "documentName", example = "dms", value = "ชื่อเอกสาร",   required = false)
    @Expose
    @Since(1.0)
    private String documentName;
    
    @XmlElement(name = "createdDateForm")
    @ApiParam(name = "createdDateForm", example = "1/01/2560", value = "createdDateForm format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String createdDateForm;
    
    @XmlElement(name = "createdDateTo")
    @ApiParam(name = "createdDateTo", example = "1/01/2560", value = "createdDateTo format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String createdDateTo;
    
    @XmlElement(name = "createdBy")
    @ApiParam(name = "createdBy", example = "นาย สมคิด", value = "ชื่อผู้สร้างเอกสาร",   required = false)
    @Expose
    @Since(1.0)
    private String createdBy;
    
    
    @XmlElement(name = "updatedDateForm")
    @ApiParam(name = "updatedDateForm", example = "1/01/2560", value = "updatedDateForm format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String updatedDateForm;
    
    @XmlElement(name = "updatedDateTo")
    @ApiParam(name = "updatedDateTo", example = "1/01/2560", value = "updatedDateTo format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String updatedDateTo;
    
    @XmlElement(name = "updatedBy")
    @ApiParam(name = "updatedBy", example = "นาย สมคิด", value = "ชื่อผู้แก้ไขเอกสาร",   required = false)
    @Expose
    @Since(1.0)
    private String updatedBy;
    
    
    @XmlElement(name = "documentExpireDateForm")
    @ApiParam(name = "documentExpireDateForm", example = "1/01/2560", value = "documentExpireDateForm format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String documentExpireDateForm;
    
    @XmlElement(name = "documentExpireDateTo")
    @ApiParam(name = "documentExpireDateTo", example = "1/01/2560", value = "documentExpireDateTo format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String documentExpireDateTo;
    
    
    @XmlElement(name = "documentDate01Form")
    @ApiParam(name = "documentDate01Form", example = "1/01/2560", value = "documentDate01Form format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String documentDate01Form;
    
    @XmlElement(name = "documentDate01To")
    @ApiParam(name = "documentDate01To", example = "1/01/2560", value = "documentDate01To format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String documentDate01To;
    
    
    @XmlElement(name = "documentDate02Form")
    @ApiParam(name = "documentDate02Form", example = "1/01/2560", value = "documentDate02Form format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String documentDate02Form;
    
    @XmlElement(name = "documentDate02To")
    @ApiParam(name = "documentDate02To", example = "1/01/2560", value = "documentDate02To format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String documentDate02To;
    
    @XmlElement(name = "documentDate03Form")
    @ApiParam(name = "documentDate03Form", example = "1/01/2560", value = "documentDate03Form format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String documentDate03Form;
    
    @XmlElement(name = "documentDate03To")
    @ApiParam(name = "documentDate03To", example = "1/01/2560", value = "documentDate03To format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String documentDate03To;
    
    
    @XmlElement(name = "documentDate04Form")
    @ApiParam(name = "documentDate04Form", example = "1/01/2560", value = "documentDate04Form format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String documentDate04Form;
    
    @XmlElement(name = "documentDate04To")
    @ApiParam(name = "documentDate04To", example = "1/01/2560", value = "documentDate04To format วัน/เดือน/พ.ศ",   required = false)
    @Expose
    @Since(1.0)
    private String documentDate04To;
    
    
    @XmlElement(name = "documentFloat01")
    @ApiParam(name = "documentFloat01", example = "01", value = "documentFloat01",   required = false)
    @Expose
    @Since(1.0)
    private int documentFloat01;
    
    @XmlElement(name = "documentFloat02")
    @ApiParam(name = "documentFloat02", example = "02", value = "documentFloat02",   required = false)
    @Expose
    @Since(1.0)
    private int documentFloat02;
    
    
    @XmlElement(name = "documentInt01")
    @ApiParam(name = "documentInt01", example = "1", value = "documentInt01",   required = false)
    @Expose
    @Since(1.0)
    private int documentInt01;
    
    @XmlElement(name = "documentInt02")
    @ApiParam(name = "documentInt02", example = "3", value = "documentInt02",   required = false)
    @Expose
    @Since(1.0)
    private int documentInt02;
    
    @XmlElement(name = "documentInt03")
    @ApiParam(name = "documentInt03", example = "3", value = "documentInt03",   required = false)
    @Expose
    @Since(1.0)
    private int documentInt03;
    
    @XmlElement(name = "documentInt04")
    @ApiParam(name = "documentInt01", example = "4", value = "documentInt04",   required = false)
    @Expose
    @Since(1.0)
    private int documentInt04;
    
    @XmlElement(name = "documentText01")
    @ApiParam(name = "documentText01", example = "text01", value = "documentText01",   required = false)
    @Expose
    @Since(1.0)
    private String documentText01;
    
    @XmlElement(name = "documentText02")
    @ApiParam(name = "documentText02", example = "text02", value = "documentText02",   required = false)
    @Expose
    @Since(1.0)
    private String documentText02;
    
    @XmlElement(name = "documentText03")
    @ApiParam(name = "documentText03", example = "text03", value = "documentText03",   required = false)
    @Expose
    @Since(1.0)
    private String documentText03;
    
    @XmlElement(name = "documentText04")
    @ApiParam(name = "documentText04", example = "text04", value = "documentText04",   required = false)
    @Expose
    @Since(1.0)
    private String documentText04;
    
     @XmlElement(name = "documentText05")
    @ApiParam(name = "documentText05", example = "text04", value = "documentText05",   required = false)
    @Expose
    @Since(1.0)
    private String documentText05;
    
    
    
    @XmlElement(name = "documentVarchar01")
    @ApiParam(name = "documentVarchar01", example = "varchar01", value = "documentVarchar01",   required = false)
    @Expose
    @Since(1.0)
    private String documentVarchar01;
    
    @XmlElement(name = "documentVarchar02")
    @ApiParam(name = "documentVarchar02", example = "varchar02", value = "documentVarchar02",   required = false)
    @Expose
    @Since(1.0)
    private String documentVarchar02;
    
    @XmlElement(name = "documentVarchar03")
    @ApiParam(name = "documentVarchar03", example = "varchar03", value = "documentVarchar03",   required = false)
    @Expose
    @Since(1.0)
    private String documentVarchar03;
    
    @XmlElement(name = "documentVarchar04")
    @ApiParam(name = "documentVarchar04", example = "varchar04", value = "documentVarchar04",   required = false)
    @Expose
    @Since(1.0)
    private String documentVarchar04;
    
    @XmlElement(name = "documentVarchar05")
    @ApiParam(name = "documentVarchar05", example = "varchar05", value = "documentVarchar05",   required = false)
    @Expose
    @Since(1.0)
    private String documentVarchar05;
    
    
    @XmlElement(name = "documentVarchar06")
    @ApiParam(name = "documentVarchar06", example = "varchar06", value = "documentVarchar06",   required = false)
    @Expose
    @Since(1.0)
    private String documentVarchar06;
    
    @XmlElement(name = "documentVarchar07")
    @ApiParam(name = "documentVarchar07", example = "varchar07", value = "documentVarchar07",   required = false)
    @Expose
    @Since(1.0)
    private String documentVarchar07;
    
    @XmlElement(name = "documentVarchar08")
    @ApiParam(name = "documentVarchar08", example = "varchar08", value = "documentVarchar08",   required = false)
    @Expose
    @Since(1.0)
    private String documentVarchar08;
    
    @XmlElement(name = "documentVarchar09")
    @ApiParam(name = "documentVarchar09", example = "varchar09", value = "documentVarchar09",   required = false)
    @Expose
    @Since(1.0)
    private String documentVarchar09;
    
    @XmlElement(name = "documentVarchar10")
    @ApiParam(name = "documentVarchar10", example = "varchar10", value = "documentVarchar10",   required = false)
    @Expose
    @Since(1.0)
    private String documentVarchar10;
    
    @XmlElement(name = "folderId")
    @ApiParam(name = "folderId", example = "1", value = "folderId",   required = true)
    @Expose
    @Since(1.0)
    private int folderId;
    
    @XmlElement(name = "documentIntComma")
    @ApiParam(name = "documentIntComma", example = "1", value = "documentIntComma",   required = false)
    @Expose
    @Since(1.0)
    private int documentIntComma;
    
    @XmlElement(name = "fullText")
    @ApiParam(name = "fullText", example = "fullText", value = "fullText",   required = false)
    @Expose
    @Since(1.0)
    private String fullText;
    
    public FieldSearchModel() {
    }

    public String getDocumentName() {
        return documentName;
    }

    @ApiModelProperty(name = "documentName", example = "เอกสาร", dataType = "string", value = "documentName", required = false)
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getCreatedDateForm() {
        return createdDateForm;
    }

    @ApiModelProperty(name = "createdDateForm", example = "1/01/2560", dataType = "string", value = "createdDateForm", required = false)
    public void setCreatedDateForm(String createdDateForm) {
        this.createdDateForm = createdDateForm;
    }

    public String getCreatedDateTo() {
        return createdDateTo;
    }

    @ApiModelProperty(name = "createdDateTo", example = "1/01/2560", dataType = "string", value = "createdDateForm", required = false)
    public void setCreatedDateTo(String createdDateTo) {
        this.createdDateTo = createdDateTo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    @ApiModelProperty(name = "createdBy", example = "นาย ฟ", dataType = "string", value = "createdBy", required = false)
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedDateForm() {
        return updatedDateForm;
    }

    @ApiModelProperty(name = "updatedDateForm", example = "1/01/2560", dataType = "string", value = "updatedDateForm", required = false)
    public void setUpdatedDateForm(String updatedDateForm) {
        this.updatedDateForm = updatedDateForm;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    @ApiModelProperty(name = "updatedBy", example = "นาย ฟ", dataType = "string", value = "updatedBy", required = false)
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    

    public String getUpdatedDateTo() {
        return updatedDateTo;
    }

    @ApiModelProperty(name = "updatedDateTo", example = "1/01/2560", dataType = "string", value = "createdDateForm", required = false)
    public void setUpdatedDateTo(String updatedDateTo) {
        this.updatedDateTo = updatedDateTo;
    }

    public String getDocumentExpireDateForm() {
        return documentExpireDateForm;
    }

    @ApiModelProperty(name = "documentExpireDateForm", example = "1/01/2560", dataType = "string", value = "documentExpireDateForm", required = false)
    public void setDocumentExpireDateForm(String documentExpireDateForm) {
        this.documentExpireDateForm = documentExpireDateForm;
    }

    public String getDocumentExpireDateTo() {
        return documentExpireDateTo;
    }

    @ApiModelProperty(name = "documentExpireDateTo", example = "1/01/2560", dataType = "string", value = "documentExpireDateTo", required = false)
    public void setDocumentExpireDateTo(String documentExpireDateTo) {
        this.documentExpireDateTo = documentExpireDateTo;
    }

    public String getDocumentDate01Form() {
        return documentDate01Form;
    }

    @ApiModelProperty(name = "documentDate01Form", example = "1/01/2560", dataType = "string", value = "documentDate01Form", required = false)
    public void setDocumentDate01Form(String documentDate01Form) {
        this.documentDate01Form = documentDate01Form;
    }

    public String getDocumentDate01To() {
        return documentDate01To;
    }

    @ApiModelProperty(name = "documentDate01To", example = "1/01/2560", dataType = "string", value = "documentDate01To", required = false)
    public void setDocumentDate01To(String documentDate01To) {
        this.documentDate01To = documentDate01To;
    }

    public String getDocumentDate02Form() {
        return documentDate02Form;
    }

    @ApiModelProperty(name = "documentDate02Form", example = "1/01/2560", dataType = "string", value = "documentDate02Form", required = false)
    public void setDocumentDate02Form(String documentDate02Form) {
        this.documentDate02Form = documentDate02Form;
    }

    public String getDocumentDate02To() {
        return documentDate02To;
    }

    @ApiModelProperty(name = "documentDate02To", example = "1/01/2560", dataType = "string", value = "documentDate02To", required = false)
    public void setDocumentDate02To(String documentDate02To) {
        this.documentDate02To = documentDate02To;
    }

    public String getDocumentDate03Form() {
        return documentDate03Form;
    }

    @ApiModelProperty(name = "documentDate03Form", example = "1/01/2560", dataType = "string", value = "documentDate03Form", required = false)
    public void setDocumentDate03Form(String documentDate03Form) {
        this.documentDate03Form = documentDate03Form;
    }

    public String getDocumentDate03To() {
        return documentDate03To;
    }

    @ApiModelProperty(name = "documentDate03To", example = "1/01/2560", dataType = "string", value = "documentDate03To", required = false)
    public void setDocumentDate03To(String documentDate03To) {
        this.documentDate03To = documentDate03To;
    }

    public String getDocumentDate04Form() {
        return documentDate04Form;
    }

    @ApiModelProperty(name = "documentDate04Form", example = "1/01/2560", dataType = "string", value = "documentDate04Form", required = false)
    public void setDocumentDate04Form(String documentDate04Form) {
        this.documentDate04Form = documentDate04Form;
    }

    public String getDocumentDate04To() {
        return documentDate04To;
    }

    @ApiModelProperty(name = "documentDate04To", example = "1/01/2560", dataType = "string", value = "documentDate04To", required = false)
    public void setDocumentDate04To(String documentDate04To) {
        this.documentDate04To = documentDate04To;
    }

    public int getDocumentFloat01() {
        return documentFloat01;
    }

    @ApiModelProperty(name = "documentFloat01", example = "1", dataType = "int", value = "documentFloat01", required = false)
    public void setDocumentFloat01(int documentFloat01) {
        this.documentFloat01 = documentFloat01;
    }

    public int getDocumentFloat02() {
        return documentFloat02;
    }

    @ApiModelProperty(name = "documentFloat02", example = "2", dataType = "int", value = "documentFloat02", required = false)
    public void setDocumentFloat02(int documentFloat02) {
        this.documentFloat02 = documentFloat02;
    }

    public int getDocumentInt01() {
        return documentInt01;
    }

    @ApiModelProperty(name = "documentInt01", example = "1", dataType = "int", value = "documentInt01", required = false)
    public void setDocumentInt01(int documentInt01) {
        this.documentInt01 = documentInt01;
    }

    public int getDocumentInt02() {
        return documentInt02;
    }

    @ApiModelProperty(name = "documentInt02", example = "2", dataType = "int", value = "documentInt02", required = false)
    public void setDocumentInt02(int documentInt02) {
        this.documentInt02 = documentInt02;
    }

    public int getDocumentInt03() {
        return documentInt03;
    }

    @ApiModelProperty(name = "documentInt03", example = "3", dataType = "int", value = "documentInt03", required = false)
    public void setDocumentInt03(int documentInt03) {
        this.documentInt03 = documentInt03;
    }

    public int getDocumentInt04() {
        return documentInt04;
    }

    @ApiModelProperty(name = "documentInt04", example = "4", dataType = "int", value = "documentInt04", required = false)
    public void setDocumentInt04(int documentInt04) {
        this.documentInt04 = documentInt04;
    }

    public String getDocumentText01() {
        return documentText01;
    }

    @ApiModelProperty(name = "documentText01", example = "text1", dataType = "String", value = "documentText01", required = false)
    public void setDocumentText01(String documentText01) {
        this.documentText01 = documentText01;
    }

    public String getDocumentText02() {
        return documentText02;
    }

     @ApiModelProperty(name = "documentText02", example = "text2", dataType = "String", value = "documentText02", required = false)
    public void setDocumentText02(String documentText02) {
        this.documentText02 = documentText02;
    }

    public String getDocumentText03() {
        return documentText03;
    }

    @ApiModelProperty(name = "documentText03", example = "text3", dataType = "String", value = "documentText03", required = false)
    public void setDocumentText03(String documentText03) {
        this.documentText03 = documentText03;
    }

    public String getDocumentText04() {
        return documentText04;
    }

    @ApiModelProperty(name = "documentText04", example = "text4", dataType = "String", value = "documentText04", required = false)
    public void setDocumentText04(String documentText04) {
        this.documentText04 = documentText04;
    }
    
    public String getDocumentText05() {
        return documentText05;
    }

    @ApiModelProperty(name = "documentText05", example = "text5", dataType = "String", value = "documentText05", required = false)
    public void setDocumentText05(String documentText05) {
        this.documentText05 = documentText05;
    }

    public String getDocumentVarchar01() {
        return documentVarchar01;
    }

    @ApiModelProperty(name = "documentVarchar01", example = "varchar01", dataType = "String", value = "documentVarchar01", required = false)
    public void setDocumentVarchar01(String documentVarchar01) {
        this.documentVarchar01 = documentVarchar01;
    }

    public String getDocumentVarchar02() {
        return documentVarchar02;
    }

    @ApiModelProperty(name = "documentVarchar02", example = "varchar02", dataType = "String", value = "documentVarchar02", required = false)
    public void setDocumentVarchar02(String documentVarchar02) {
        this.documentVarchar02 = documentVarchar02;
    }

    public String getDocumentVarchar03() {
        return documentVarchar03;
    }

    @ApiModelProperty(name = "documentVarchar03", example = "varchar03", dataType = "String", value = "documentVarchar03", required = false)
    public void setDocumentVarchar03(String documentVarchar03) {
        this.documentVarchar03 = documentVarchar03;
    }

    public String getDocumentVarchar04() {
        return documentVarchar04;
    }

    @ApiModelProperty(name = "documentVarchar04", example = "varchar04", dataType = "String", value = "documentVarchar04", required = false)
    public void setDocumentVarchar04(String documentVarchar04) {
        this.documentVarchar04 = documentVarchar04;
    }

    public String getDocumentVarchar05() {
        return documentVarchar05;
    }

    @ApiModelProperty(name = "documentVarchar05", example = "varchar05", dataType = "String", value = "documentVarchar05", required = false)
    public void setDocumentVarchar05(String documentVarchar05) {
        this.documentVarchar05 = documentVarchar05;
    }

    public String getDocumentVarchar06() {
        return documentVarchar06;
    }

    @ApiModelProperty(name = "documentVarchar06", example = "varchar06", dataType = "String", value = "documentVarchar06", required = false)
    public void setDocumentVarchar06(String documentVarchar06) {
        this.documentVarchar06 = documentVarchar06;
    }

    public String getDocumentVarchar07() {
        return documentVarchar07;
    }

    @ApiModelProperty(name = "documentVarchar07", example = "varchar07", dataType = "String", value = "documentVarchar07", required = false)
    public void setDocumentVarchar07(String documentVarchar07) {
        this.documentVarchar07 = documentVarchar07;
    }

    public String getDocumentVarchar08() {
        return documentVarchar08;
    }

    @ApiModelProperty(name = "documentVarchar08", example = "varchar08", dataType = "String", value = "documentVarchar08", required = false)
    public void setDocumentVarchar08(String documentVarchar08) {
        this.documentVarchar08 = documentVarchar08;
    }

    public String getDocumentVarchar09() {
        return documentVarchar09;
    }

    @ApiModelProperty(name = "documentVarchar09", example = "varchar09", dataType = "String", value = "documentVarchar09", required = false)
    public void setDocumentVarchar09(String documentVarchar09) {
        this.documentVarchar09 = documentVarchar09;
    }

    public String getDocumentVarchar10() {
        return documentVarchar10;
    }

    @ApiModelProperty(name = "documentVarchar10", example = "varchar10", dataType = "String", value = "documentVarchar10", required = false)
    public void setDocumentVarchar10(String documentVarchar10) {
        this.documentVarchar10 = documentVarchar10;
    }

    public int getFolderId() {
        return folderId;
    }

    @ApiModelProperty(name = "folderId", example = "1", dataType = "int", value = "folderId", required = true)
    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    @ApiModelProperty(name = "documentIntComma", example = "1", dataType = "int", value = "documentIntComma", required = false)
    public int getDocumentIntComma() {
        return documentIntComma;
    }

    public void setDocumentIntComma(int documentIntComma) {
        this.documentIntComma = documentIntComma;
    }

    public String getFullText() {
        return fullText;
    }

    @ApiModelProperty(name = "fullText", example = "fullText", dataType = "String", value = "fullText", required = false)
    public void setFullText(String fullText) {
        this.fullText = fullText;
    }
    
    
    
    
    
}
