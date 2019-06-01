package com.px.wf.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Mali
 */
@Entity
@Table(name = "PC_FOLDER_PROPERTY")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "FOLDER_PROPERTY_ID"))
})
public class FolderProperty extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -1463310997365898140L;

    @Column(name = "FOLDER_PROPERTY_FOLDER_ID", nullable = true, columnDefinition = "int default '0'")
    private int folderPropertyFolderId;
    
    @Column(name = "FOLDER_PROPERTY_FIELD_ID", nullable = true, columnDefinition = "int default '0'")
    private int folderPropertyFieldId;

    @Column(name = "FOLDER_PROPERTY_TYPE", nullable = true, length = 1)
    private String folderPropertyType;

    @Column(name = "FOLDER_PROPERTY_FIELD_NAME", nullable = true, length = 255)
    private String folderPropertyFieldName;

    @Column(name = "FOLDER_PROPERTY_FIELD_DESCRIPTION", nullable = true, length = 255)
    private String folderPropertyFieldDescription;

    @Column(name = "FOLDER_PROPERTY_FIELD_TYPE", nullable = true, length = 50)
    private String folderPropertyFieldType;

    @Column(name = "FOLDER_PROPERTY_FIELD_LENGTH", nullable = true, columnDefinition = "int default '0'")
    private int folderPropertyFieldLength;

    @Column(name = "FOLDER_PROPERTY_P_LIST", nullable = true, length = 1)
    private String folderPropertyPList;

    @Column(name = "FOLDER_PROPERTY_P_LIST_WIDTH", nullable = true, columnDefinition = "int default '0'")
    private int folderPropertyPListWidth;

    @Column(name = "FOLDER_PROPERTY_P_SEARCH", nullable = true, length = 1)
    private String folderPropertyPSearch;

    @Column(name = "FOLDER_PROPERTY_P_VIEW", nullable = true, length = 1)
    private String folderPropertyPView;

    @Column(name = "FOLDER_PROPERTY_P_LOOKUP_ID", nullable = true, columnDefinition = "int default '0'")
    private int folderPropertyPLookupId;

    public int getFolderPropertyFolderId() {
        return folderPropertyFolderId;
    }

    public void setFolderPropertyFolderId(int folderPropertyFolderId) {
        this.folderPropertyFolderId = folderPropertyFolderId;
    }

    public int getFolderPropertyFieldId() {
        return folderPropertyFieldId;
    }

    public void setFolderPropertyFieldId(int folderPropertyFieldId) {
        this.folderPropertyFieldId = folderPropertyFieldId;
    }

    public String getFolderPropertyType() {
        return folderPropertyType;
    }

    public void setFolderPropertyType(String folderPropertyType) {
        this.folderPropertyType = folderPropertyType;
    }

    public String getFolderPropertyFieldName() {
        return folderPropertyFieldName;
    }

    public void setFolderPropertyFieldName(String folderPropertyFieldName) {
        this.folderPropertyFieldName = folderPropertyFieldName;
    }

    public String getFolderPropertyFieldDescription() {
        return folderPropertyFieldDescription;
    }

    public void setFolderPropertyFieldDescription(String folderPropertyFieldDescription) {
        this.folderPropertyFieldDescription = folderPropertyFieldDescription;
    }

    public String getFolderPropertyFieldType() {
        return folderPropertyFieldType;
    }

    public void setFolderPropertyFieldType(String folderPropertyFieldType) {
        this.folderPropertyFieldType = folderPropertyFieldType;
    }

    public int getFolderPropertyFieldLength() {
        return folderPropertyFieldLength;
    }

    public void setFolderPropertyFieldLength(int folderPropertyFieldLength) {
        this.folderPropertyFieldLength = folderPropertyFieldLength;
    }

    public String getFolderPropertyPList() {
        return folderPropertyPList;
    }

    public void setFolderPropertyPList(String folderPropertyPList) {
        this.folderPropertyPList = folderPropertyPList;
    }

    public int getFolderPropertyPListWidth() {
        return folderPropertyPListWidth;
    }

    public void setFolderPropertyPListWidth(int folderPropertyPListWidth) {
        this.folderPropertyPListWidth = folderPropertyPListWidth;
    }

    public String getFolderPropertyPSearch() {
        return folderPropertyPSearch;
    }

    public void setFolderPropertyPSearch(String folderPropertyPSearch) {
        this.folderPropertyPSearch = folderPropertyPSearch;
    }

    public String getFolderPropertyPView() {
        return folderPropertyPView;
    }

    public void setFolderPropertyPView(String folderPropertyPView) {
        this.folderPropertyPView = folderPropertyPView;
    }

    public int getFolderPropertyPLookupId() {
        return folderPropertyPLookupId;
    }

    public void setFolderPropertyPLookupId(int folderPropertyPLookupId) {
        this.folderPropertyPLookupId = folderPropertyPLookupId;
    }

}
