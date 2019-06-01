
package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "WfContentModel_MaxContentNoAndBookNo")
@ApiModel(description = "รายการเลขที่สามารถใช้ได้ล่าสุดของเลขทะเบียน และ เลขที่หนังสือ")
public class WfContentModel_groupListContentData implements Serializable {

    private static final long serialVersionUID = 817680997160814895L;
    
    @Expose @Since(1.0) private String data;
    @Expose @Since(1.0) private int folderPropertyFolderId;
    @Expose @Since(1.0) private int folderPropertyFieldId;
    @Expose @Since(1.0) private String folderPropertyType;
    @Expose @Since(1.0) private String folderPropertyFieldName;
    @Expose @Since(1.0) private String folderPropertyFieldDescription;
    @Expose @Since(1.0) private String folderPropertyFieldType;
    @Expose @Since(1.0) private int folderPropertyFieldLength;
    @Expose @Since(1.0) private String folderPropertyPList;
    @Expose @Since(1.0) private int folderPropertyPListWidth;
    @Expose @Since(1.0) private String folderPropertyPSearch;
    @Expose @Since(1.0) private String folderPropertyPView;
    @Expose @Since(1.0) private int folderPropertyPLookupId;

    public WfContentModel_groupListContentData() {
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setFolderPropertyFolderId(int folderPropertyFolderId) {
        this.folderPropertyFolderId = folderPropertyFolderId;
    }

    public void setFolderPropertyFieldId(int folderPropertyFieldId) {
        this.folderPropertyFieldId = folderPropertyFieldId;
    }

    public void setFolderPropertyType(String folderPropertyType) {
        this.folderPropertyType = folderPropertyType;
    }

    public void setFolderPropertyFieldName(String folderPropertyFieldName) {
        this.folderPropertyFieldName = folderPropertyFieldName;
    }

    public void setFolderPropertyFieldDescription(String folderPropertyFieldDescription) {
        this.folderPropertyFieldDescription = folderPropertyFieldDescription;
    }

    public void setFolderPropertyFieldType(String folderPropertyFieldType) {
        this.folderPropertyFieldType = folderPropertyFieldType;
    }

    public void setFolderPropertyFieldLength(int folderPropertyFieldLength) {
        this.folderPropertyFieldLength = folderPropertyFieldLength;
    }

    public void setFolderPropertyPList(String folderPropertyPList) {
        this.folderPropertyPList = folderPropertyPList;
    }

    public void setFolderPropertyPListWidth(int folderPropertyPListWidth) {
        this.folderPropertyPListWidth = folderPropertyPListWidth;
    }

    public void setFolderPropertyPSearch(String folderPropertyPSearch) {
        this.folderPropertyPSearch = folderPropertyPSearch;
    }

    public void setFolderPropertyPView(String folderPropertyPView) {
        this.folderPropertyPView = folderPropertyPView;
    }

    public void setFolderPropertyPLookupId(int folderPropertyPLookupId) {
        this.folderPropertyPLookupId = folderPropertyPLookupId;
    }

}
