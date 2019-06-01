/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author TOP
 */
@Entity
@Table(name = "PC_DOCUMENT_TYPE_DETAIL")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "DOCUMENT_TYPE_DETAIL_ID"))
})
public class DocumentTypeDetail extends BaseEntity {

    @Transient
    public static final long serialVersionUID = 0L;

    @Column(name = "DOCUMENT_TYPE_ID", nullable = true, columnDefinition = "int default '0'")
    private int documentTypeId;

    @Column(name = "DMS_FIELD_ID", nullable = true, columnDefinition = "int default '0'")
    private int dmsFieldId;

//    @Column(name="DOCUMENT_TYPE_DETAIL_ORDER", nullable = false, columnDefinition="float default '0.0'")
//    private float documentTypeDetailOrder;
    @Column(name = "TYPE_DETAIL_NAME", nullable = true, length = 255)
    private String documentTypeDetailName;

    @Column(name = "TYPE_DETAIL_VIEW", nullable = true, length = 10)
    private String documentTypeDetailView;

    @Column(name = "TYPE_DETAIL_SEARCH", nullable = true, length = 10)
    private String documentTypeDetailSearch;

    @Column(name = "TYPE_DETAIL_EDIT", nullable = true, length = 10)
    private String documentTypeDetailEdit;

    @Column(name = "TYPE_DETAIL_UNIQUE", nullable = true, length = 10)
    private String documentTypeDetailUnique;

    @Column(name = "TYPE_DETAIL_REQUIRE", nullable = true, length = 10)
    private String documentTypeDetailRequire;

    @Column(name = "TYPE_DETAIL_SHOW_COMMA", nullable = true, length = 10)
    private String documentTypeDetailShowComma;

    @Column(name = "TYPE_DETAIL_COLUMN_COLOR", nullable = true, length = 10)
    private String documentTypeDetailColumnColor;

    @Column(name = "TYPE_DETAIL_FONT_COLOR", nullable = true, length = 50)
    private String documentTypeDetailFontColor;

    @Column(name = "TYPE_DETAIL_FONT_ITALIC", nullable = true, length = 10)
    private String documentTypeDetailFontItalic;

    @Column(name = "TYPE_DETAIL_FONT_BOLD", nullable = true, length = 10)
    private String documentTypeDetailFontBold;

    @Column(name = "TYPE_DETAIL_FONT_UNDERLINE", nullable = true, length = 10)
    private String documentTypeDetailFontUnderline;

    @Column(name = "TYPE_DETAIL_DEFAULT", nullable = true, length = 255)
    private String documentTypeDetailDefault;

    @Column(name = "TYPE_DETAIL_LOOKUP", nullable = true, columnDefinition = "int default '0'")
    private int documentTypeDetailLookup;

    @Column(name = "TYPE_DETAIL_COLUMNE_WIDTH", nullable = true, columnDefinition = "int default '0'")
    private int documentTypeDetailColumnWidth;

    @Column(name = "TYPE_DETAIL_ALIGNMENT", nullable = true, length = 10)
    private String documentTypeDetailAlignment;

    public DocumentTypeDetail() {
    }

    public DocumentTypeDetail(int documentTypeId, int dmsFieldId, String documentTypeDetailName, String documentTypeDetailView, String documentTypeDetailSearch, String documentTypeDetailEdit, String documentTypeDetailUnique, String documentTypeDetailRequire, String documentTypeDetailShowComma, String documentTypeDetailColumnColor, String documentTypeDetailFontColor, String documentTypeDetailFontItalic, String documentTypeDetailFontBold, String documentTypeDetailFontUnderline, String documentTypeDetailDefault, int documentTypeDetailLookup, int documentTypeDetailColumnWidth, String documentTypeDetailAlignment, Integer createdBy) {
        super(createdBy);
        this.documentTypeId = documentTypeId;
        this.dmsFieldId = dmsFieldId;
        this.documentTypeDetailName = documentTypeDetailName;
        this.documentTypeDetailView = documentTypeDetailView;
        this.documentTypeDetailSearch = documentTypeDetailSearch;
        this.documentTypeDetailEdit = documentTypeDetailEdit;
        this.documentTypeDetailUnique = documentTypeDetailUnique;
        this.documentTypeDetailRequire = documentTypeDetailRequire;
        this.documentTypeDetailShowComma = documentTypeDetailShowComma;
        this.documentTypeDetailColumnColor = documentTypeDetailColumnColor;
        this.documentTypeDetailFontColor = documentTypeDetailFontColor;
        this.documentTypeDetailFontItalic = documentTypeDetailFontItalic;
        this.documentTypeDetailFontBold = documentTypeDetailFontBold;
        this.documentTypeDetailFontUnderline = documentTypeDetailFontUnderline;
        this.documentTypeDetailDefault = documentTypeDetailDefault;
        this.documentTypeDetailLookup = documentTypeDetailLookup;
        this.documentTypeDetailColumnWidth = documentTypeDetailColumnWidth;
        this.documentTypeDetailAlignment = documentTypeDetailAlignment;
    }

    public int getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public int getDmsFieldId() {
        return dmsFieldId;
    }

    public void setDmsFieldId(int dmsFieldId) {
        this.dmsFieldId = dmsFieldId;
    }

    public String getDocumentTypeDetailName() {
        return documentTypeDetailName;
    }

    public void setDocumentTypeDetailName(String documentTypeDetailName) {
        this.documentTypeDetailName = documentTypeDetailName;
    }

    public String getDocumentTypeDetailView() {
        return documentTypeDetailView;
    }

    public void setDocumentTypeDetailView(String documentTypeDetailView) {
        this.documentTypeDetailView = documentTypeDetailView;
    }

    public String getDocumentTypeDetailSearch() {
        return documentTypeDetailSearch;
    }

    public void setDocumentTypeDetailSearch(String documentTypeDetailSearch) {
        this.documentTypeDetailSearch = documentTypeDetailSearch;
    }

    public String getDocumentTypeDetailEdit() {
        return documentTypeDetailEdit;
    }

    public void setDocumentTypeDetailEdit(String documentTypeDetailEdit) {
        this.documentTypeDetailEdit = documentTypeDetailEdit;
    }

    public String getDocumentTypeDetailUnique() {
        return documentTypeDetailUnique;
    }

    public void setDocumentTypeDetailUnique(String documentTypeDetailUnique) {
        this.documentTypeDetailUnique = documentTypeDetailUnique;
    }

    public String getDocumentTypeDetailRequire() {
        return documentTypeDetailRequire;
    }

    public void setDocumentTypeDetailRequire(String documentTypeDetailRequire) {
        this.documentTypeDetailRequire = documentTypeDetailRequire;
    }

    public String getDocumentTypeDetailShowComma() {
        return documentTypeDetailShowComma;
    }

    public void setDocumentTypeDetailShowComma(String documentTypeDetailShowComma) {
        this.documentTypeDetailShowComma = documentTypeDetailShowComma;
    }

    public String getDocumentTypeDetailColumnColor() {
        return documentTypeDetailColumnColor;
    }

    public void setDocumentTypeDetailColumnColor(String documentTypeDetailColumnColor) {
        this.documentTypeDetailColumnColor = documentTypeDetailColumnColor;
    }

    public String getDocumentTypeDetailFontColor() {
        return documentTypeDetailFontColor;
    }

    public void setDocumentTypeDetailFontColor(String documentTypeDetailFontColor) {
        this.documentTypeDetailFontColor = documentTypeDetailFontColor;
    }

    public String getDocumentTypeDetailFontItalic() {
        return documentTypeDetailFontItalic;
    }

    public void setDocumentTypeDetailFontItalic(String documentTypeDetailFontItalic) {
        this.documentTypeDetailFontItalic = documentTypeDetailFontItalic;
    }

    public String getDocumentTypeDetailFontBold() {
        return documentTypeDetailFontBold;
    }

    public void setDocumentTypeDetailFontBold(String documentTypeDetailFontBold) {
        this.documentTypeDetailFontBold = documentTypeDetailFontBold;
    }

    public String getDocumentTypeDetailFontUnderline() {
        return documentTypeDetailFontUnderline;
    }

    public void setDocumentTypeDetailFontUnderline(String documentTypeDetailFontUnderline) {
        this.documentTypeDetailFontUnderline = documentTypeDetailFontUnderline;
    }

    public String getDocumentTypeDetailDefault() {
        return documentTypeDetailDefault;
    }

    public void setDocumentTypeDetailDefault(String documentTypeDetailDefault) {
        this.documentTypeDetailDefault = documentTypeDetailDefault;
    }

    public int getDocumentTypeDetailLookup() {
        return documentTypeDetailLookup;
    }

    public void setDocumentTypeDetailLookup(int documentTypeDetailLookup) {
        this.documentTypeDetailLookup = documentTypeDetailLookup;
    }

    public int getDocumentTypeDetailColumnWidth() {
        return documentTypeDetailColumnWidth;
    }

    public void setDocumentTypeDetailColumnWidth(int documentTypeDetailColumnWidth) {
        this.documentTypeDetailColumnWidth = documentTypeDetailColumnWidth;
    }

    public String getDocumentTypeDetailAlignment() {
        return documentTypeDetailAlignment;
    }

    public void setDocumentTypeDetailAlignment(String documentTypeDetailAlignment) {
        this.documentTypeDetailAlignment = documentTypeDetailAlignment;
    }

}
