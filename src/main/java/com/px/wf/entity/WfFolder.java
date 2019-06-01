package com.px.wf.entity;

import com.px.share.entity.BaseTreeEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 *
 * @author ken
 */
@Entity
@Table(name = "PC_WF_FOLDER")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WF_FOLDER_ID"))
})
public class WfFolder extends BaseTreeEntity {

    @Transient
    private static final long serialVersionUID = -4568615326273957057L;

    @Column(name = "WF_FOLDER_NAME", length = 255)
    private String wfFolderName;

    @Column(name = "WF_FOLDER_TYPE", length = 10)
    private String wfFolderType;

    @Column(name = "WF_FOLDER_PARENT_TYPE", columnDefinition = "int default '0'")
    private int wfFolderParentType;

    @Column(name = "WF_FOLDER_PARENT_NAME", length = 255)
    private String wfFolderParentName;

    @Column(name = "WF_FOLDER_DETAIL", length = 1000)
    private String wfFolderDetail;

    @ManyToOne()
    @JoinColumn(name = "WF_CONTENT_TYPE_ID", nullable = false)
    private WfContentType wfContentType;

    @ManyToOne()
    @JoinColumn(name = "WF_CONTENT_TYPE2_ID", nullable = false)
    private WfContentType2 wfContentType2;

    @Column(name = "WF_FOLDER_AUTORUN", columnDefinition = "int default '0'")
    private int wfFolderAutorun;

    @Column(name = "WF_FOLDER_BOOK_NO_TYPE", columnDefinition = "int default '0'")
    private int wfFolderBookNoType;

    @Column(name = "WF_FOLDER_PRE_BOOK_NO", length = 1000)
    private String wfFolderPreBookNo;

    @Column(name = "WF_FOLDER_PRE_CONTENT_NO", length = 50)
    private String wfFolderPreContentNo;

    @Column(name = "WF_FOLDER_OWNER_ID", columnDefinition = "int default '0'")
    private int wfFolderOwnerId;

    @Column(name = "WF_FOLDER_LINK_FOLDER_ID", columnDefinition = "int default '0'")
    private int wfFolderLinkFolderId;

    @Column(name = "WF_FOLDER_LINK_ID", columnDefinition = "int default '0'")
    private int wfFolderLinkId;

    @Column(name = "WF_FOLDER_BY_BUDGET_YEAR", columnDefinition = "int default '0'")
    private int wfFolderByBudgetYear;

    @Column(name = "WF_FOLDER_TYPEYEAR_EXPIRE", length = 50)
    private String wfFolderTypeYearExpire;

    @Column(name = "WF_FOLDER_NUMYEAR_EXPIRE", columnDefinition = "int default '0'")
    private int wfFolderNumYearExpire;

    @Column(name = "WF_FOLDER_EXPIRE_DATE", nullable = true)
    private LocalDateTime wfFolderExpireDate;

    @Column(name = "CONVERT_ID", columnDefinition = "int default '0'")
    private Integer convertId;

    //oat-add
    @Column(name = "SEARCH_FIELD", length = 255)
    private String searchField;

    public WfFolder() {
    }

    public WfFolder(Integer createdBy) {
        super(createdBy);
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

    public int getWfFolderParentType() {
        return wfFolderParentType;
    }

    public void setWfFolderParentType(int wfFolderParentType) {
        this.wfFolderParentType = wfFolderParentType;
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

    public WfContentType getWfContentType() {
        return wfContentType;
    }

    public void setWfContentType(WfContentType wfContentType) {
        this.wfContentType = wfContentType;
    }

    public WfContentType2 getWfContentType2() {
        return wfContentType2;
    }

    public void setWfContentType2(WfContentType2 wfContentType2) {
        this.wfContentType2 = wfContentType2;
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

    public LocalDateTime getWfFolderExpireDate() {
        return wfFolderExpireDate;
    }

    public void setWfFolderExpireDate(LocalDateTime wfFolderExpireDate) {
        this.wfFolderExpireDate = wfFolderExpireDate;
    }

    public Integer getConvertId() {
        return convertId;
    }

    public void setConvertId(Integer convertId) {
        this.convertId = convertId;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }
}
