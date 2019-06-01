/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.model;

//import com.px.dms.model.post.DocumentTypeDetailPostModel;
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
@XmlRootElement(name = "DocumentTypeDetail")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "รายละเอียดประเภทเอกสาร")
public class DocumentTypeDetailModel extends VersionModel {

    public static final long serialVersionUID = 0L;

    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสรายละเอียดประเภทเอกสาร", defaultValue = "0", required = false)
    @Expose
    @Since(1.0)
    private int id;

    @XmlElement(name = "documentTypeId")
    @ApiParam(name = "documentTypeId", example = "1", value = "เลขที่ประเภทเอกสาร", required = true)
    @Expose
    @Since(1.0)
    private int documentTypeId;

    @XmlElement(name = "dmsFieldId")
    @ApiParam(name = "dmsFieldId", value = "เลบที่Field", example = "1", required = true)
    @Expose
    @Since(1.0)
    private int dmsFieldId;

    @XmlElement(name = "documentTypeDetailName")
    @ApiParam(name = "documentTypeDetailName", value = "ชื่อ", example = "ชื่อเรื่อง",required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailName;

    @XmlElement(name = "documentTypeDetailView")
    @ApiParam(name = "documentTypeDetailView", value = " can view Y = yes, N = no", example = "Y",required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailView;

    @XmlElement(name = "documentTypeDetailSearch")
    @ApiParam(name = "documentTypeDetailSearch", value = " can search Y = yes, N = no", example = "Y",required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailSearch;

    @XmlElement(name = "documentTypeDetailEdit")
    @ApiParam(name = "documentTypeDetailEdit", value = "edit Y = yes, N = no", example = "Y",required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailEdit;

    @XmlElement(name = "documentTypeDetailUnique")
    @ApiParam(name = "documentTypeDetailUnique", value = "is unique Y = yes, N = no", example = "Y",required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailUnique;

    @XmlElement(name = "documentTypeDetailRequire")
    @ApiParam(name = "documentTypeDetailRequire", value = "is require Y = yes, N = no", example = "Y",required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailRequire;

    @XmlElement(name = "documentTypeDetailShowComma")
    @ApiParam(name = "documentTypeDetailShowComma", value = "have comma for int type Y = yes, N = no", example = "Y",required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailShowComma;

    @XmlElement(name = "documentTypeDetailColumnColor")
    @ApiParam(name = "documentTypeDetailColumnColor", value = "color hex #000000", example = "#000000",required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailColumnColor;

    @XmlElement(name = "documentTypeDetailFontColor")
    @ApiParam(name = "documentTypeDetailFontColor", value = "color hex #000000", example = "#000000",required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailFontColor;

    @XmlElement(name = "documentTypeDetailFontItalic")
    @ApiParam(name = "documentTypeDetailFontItalic", value = "how font italic Y = yes, N = no",example = "Y", required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailFontItalic;

    @XmlElement(name = "documentTypeDetailFontBold")
    @ApiParam(name = "documentTypeDetailFontBold", value = "show font bold Y = yes, N = no",example = "Y", required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailFontBold;

    @XmlElement(name = "documentTypeDetailFontUnderline")
    @ApiParam(name = "documentTypeDetailFontUnderline", value = "show font underline Y = yes, N = no", example = "Y",required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailFontUnderline;

    @XmlElement(name = "documentTypeDetailDefault")
    @ApiParam(name = "documentTypeDetailDefault", value = "set default value", required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailDefault;

    @XmlElement(name = "documentTypeDetailLookup")
    @ApiParam(name = "documentTypeDetailLookup", value = "Lookup", required = false)
    @Expose
    @Since(1.0)
    private int documentTypeDetailLookup;

    @XmlElement(name = "documentTypeDetailColumnWidth")
    @ApiParam(name = "documentTypeDetailColumnWidth", value = "ColumnWidth", example = "200",required = false)
    @Expose
    @Since(1.0)
    private int documentTypeDetailColumnWidth;

    @XmlElement(name = "documentTypeDetailAlignment")
    @ApiParam(name = "documentTypeDetailAlignment", value = "0 = align left, 1= align center, 2 = align right", example = "0",required = false)
    @Expose
    @Since(1.0)
    private String documentTypeDetailAlignment;

    public DocumentTypeDetailModel() {
    }

    public int getId() {
        return id;
    }

     @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัส", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public int getDocumentTypeId() {
        return documentTypeId;
    }

     @ApiModelProperty(name = "documentTypeId", example = "1", dataType = "int", value = "เลขที่ประเภทเอกสาร", required = true)
    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public int getDmsFieldId() {
        return dmsFieldId;
    }

     @ApiModelProperty(name = "dmsFieldId", example = "1", dataType = "int", value = "เลบที่Field", required = true)
    public void setDmsFieldId(int dmsFieldId) {
        this.dmsFieldId = dmsFieldId;
    }

    public String getDocumentTypeDetailName() {
        return documentTypeDetailName;
    }

     @ApiModelProperty(name = "documentTypeDetailName", example = "ชื่อเรื่อง", dataType = "string", value = "ชื่อเรื่อง", required = false)
    public void setDocumentTypeDetailName(String documentTypeDetailName) {
        this.documentTypeDetailName = documentTypeDetailName;
    }

    public String getDocumentTypeDetailView() {
        return documentTypeDetailView;
    }

     @ApiModelProperty(name = "documentTypeDetailView", example = "Y", dataType = "string", value = "view ได้ไหม", required = false)
    public void setDocumentTypeDetailView(String documentTypeDetailView) {
        this.documentTypeDetailView = documentTypeDetailView;
    }

    public String getDocumentTypeDetailSearch() {
        return documentTypeDetailSearch;
    }

     @ApiModelProperty(name = "documentTypeDetailSearch", example = "Y", dataType = "string", value = "Search ได้ไหม", required = false)
    public void setDocumentTypeDetailSearch(String documentTypeDetailSearch) {
        this.documentTypeDetailSearch = documentTypeDetailSearch;
    }

    public String getDocumentTypeDetailEdit() {
        return documentTypeDetailEdit;
    }

     @ApiModelProperty(name = "documentTypeDetailEdit", example = "Y", dataType = "string", value = "แก้ไขได้ไหม", required = false)
    public void setDocumentTypeDetailEdit(String documentTypeDetailEdit) {
        this.documentTypeDetailEdit = documentTypeDetailEdit;
    }

    public String getDocumentTypeDetailUnique() {
        return documentTypeDetailUnique;
    }

     @ApiModelProperty(name = "documentTypeDetailUnique", example = "Y", dataType = "string", value = "Unique", required = false)
    public void setDocumentTypeDetailUnique(String documentTypeDetailUnique) {
        this.documentTypeDetailUnique = documentTypeDetailUnique;
    }

    
    public String getDocumentTypeDetailRequire() {
        return documentTypeDetailRequire;
    }

     @ApiModelProperty(name = "documentTypeDetailRequire", example = "Y", dataType = "string", value = "Require", required = false)
    public void setDocumentTypeDetailRequire(String documentTypeDetailRequire) {
        this.documentTypeDetailRequire = documentTypeDetailRequire;
    }

    public String getDocumentTypeDetailShowComma() {
        return documentTypeDetailShowComma;
    }

     @ApiModelProperty(name = "documentTypeDetailShowComma", example = "Y", dataType = "string", value = "ShowComma?", required = false)
    public void setDocumentTypeDetailShowComma(String documentTypeDetailShowComma) {
        this.documentTypeDetailShowComma = documentTypeDetailShowComma;
    }

    public String getDocumentTypeDetailColumnColor() {
        return documentTypeDetailColumnColor;
    }

     @ApiModelProperty(name = "documentTypeDetailColumnColor", example = "#000000", dataType = "string", value = "ColumnColor", required = false)
    public void setDocumentTypeDetailColumnColor(String documentTypeDetailColumnColor) {
        this.documentTypeDetailColumnColor = documentTypeDetailColumnColor;
    }

    public String getDocumentTypeDetailFontColor() {
        return documentTypeDetailFontColor;
    }
    

     @ApiModelProperty(name = "documentTypeDetailFontColor", example = "#000000", dataType = "string", value = "FontColor", required = false)
    public void setDocumentTypeDetailFontColor(String documentTypeDetailFontColor) {
        this.documentTypeDetailFontColor = documentTypeDetailFontColor;
    }

    public String getDocumentTypeDetailFontItalic() {
        return documentTypeDetailFontItalic;
    }

     @ApiModelProperty(name = "documentTypeDetailFontItalic", example = "Y", dataType = "string", value = "ตัวเอียง", required = false)
    public void setDocumentTypeDetailFontItalic(String documentTypeDetailFontItalic) {
        this.documentTypeDetailFontItalic = documentTypeDetailFontItalic;
    }

    public String getDocumentTypeDetailFontBold() {
        return documentTypeDetailFontBold;
    }

     @ApiModelProperty(name = "documentTypeDetailFontBold", example = "Y", dataType = "string", value = "ตัวหนา", required = false)
    public void setDocumentTypeDetailFontBold(String documentTypeDetailFontBold) {
        this.documentTypeDetailFontBold = documentTypeDetailFontBold;
    }

    public String getDocumentTypeDetailFontUnderline() {
        return documentTypeDetailFontUnderline;
    }

     @ApiModelProperty(name = "documentTypeDetailFontUnderline", example = "Y", dataType = "string", value = "เส้นใต้", required = false)
    public void setDocumentTypeDetailFontUnderline(String documentTypeDetailFontUnderline) {
        this.documentTypeDetailFontUnderline = documentTypeDetailFontUnderline;
    }

    public String getDocumentTypeDetailDefault() {
        return documentTypeDetailDefault;
    }

     @ApiModelProperty(name = "documentTypeDetailDefault", example = "DetailDefault", dataType = "string", value = "DetailDefault", required = false)
    public void setDocumentTypeDetailDefault(String documentTypeDetailDefault) {
        this.documentTypeDetailDefault = documentTypeDetailDefault;
    }

    public int getDocumentTypeDetailLookup() {
        return documentTypeDetailLookup;
    }

     @ApiModelProperty(name = "documentTypeDetailLookup", example = "0", dataType = "int", value = "ค้นหาได้ไหม", required = false)
    public void setDocumentTypeDetailLookup(int documentTypeDetailLookup) {
        this.documentTypeDetailLookup = documentTypeDetailLookup;
    }

    public int getDocumentTypeDetailColumnWidth() {
        return documentTypeDetailColumnWidth;
    }

     @ApiModelProperty(name = "documentTypeDetailColumnWidth", example = "200", dataType = "int", value = "ColumnWidth", required = false)
    public void setDocumentTypeDetailColumnWidth(int documentTypeDetailColumnWidth) {
        this.documentTypeDetailColumnWidth = documentTypeDetailColumnWidth;
    }

    public String getDocumentTypeDetailAlignment() {
        return documentTypeDetailAlignment;
    }

     @ApiModelProperty(name = "documentTypeDetailAlignment", example = "Y", dataType = "string", value = "Alignment", required = false)
    public void setDocumentTypeDetailAlignment(String documentTypeDetailAlignment) {
        this.documentTypeDetailAlignment = documentTypeDetailAlignment;
    }

}
