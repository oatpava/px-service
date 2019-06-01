package com.px.share.model;

import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Peach
 */
@XmlRootElement(name = "SearchAllModule")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "SearchAllModule ค้นหาจากทุก module" )
public class SearchAllModuleModel extends VersionModel{
    private static final long serialVersionUID = -4212943789381224388L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสข้อมูล", defaultValue = "0", required = false )
    @Expose
    private String id;
    
    @XmlElement(name = "moduleId")
    @ApiParam(name = "moduleId",value = "รหัส module",required = true)
    @Expose 
    private int moduleId;
    
    @XmlElement(name = "linkType")
    @ApiParam(name = "linkType",value = "ประเภทการเชื่องโยง",required = true)
    @Size(max = 12)
    @Expose 
    private String linkType;
    
    @XmlElement(name = "linkId")
    @ApiParam(name = "linkId",value = "รหัสเชื่อมโยง",required = true)
    @Expose 
    private int linkId;
    
    @XmlElement(name = "title")
    @ApiParam(name = "title",value = "หัวข้อ",required = true)
    @Size(max = 255)
    @Expose 
    private String title;
    
    @XmlElement(name = "content")
    @ApiParam(name = "content",value = "เนื้อหา",required = true)
    @Size(max = 255)
    @Expose 
    private String content;
    
    @XmlElement(name = "contentDate")
    @ApiParam(name = "contentDate",value = "วันที่ของเนื้อหา",required = true)
    @Size(max = 255)
    @Expose 
    private String contentDate;
    
    @XmlElement(name = "searchContent")
    @ApiParam(name = "searchContent", value = "เนื้อหาสำหรับค้นหา", required = true)
    @Expose 
    private String searchContent;
    
    public SearchAllModuleModel() {
    }

    public String getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "รหัส", dataType = "string", value = "รหัสข้อมูล", readOnly = true, required = false)
    public void setId(String id) {
        this.id = id;
    }

    public int getModuleId() {
        return moduleId;
    }

    @ApiModelProperty(name = "moduleId", example = "0", dataType = "int", value = "รหัส module", required = true)
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getLinkType() {
        return linkType;
    }

    @ApiModelProperty(name = "linkType", example = "WF", dataType = "string", value = "ประเภทการเชื่อมโยง", required = true)
    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public int getLinkId() {
        return linkId;
    }

    @ApiModelProperty(name = "linkId", example = "0", dataType = "int", value = "รหัสเชื่อมโยง", required = true)
    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public String getTitle() {
        return title;
    }

    @ApiModelProperty(name = "title", example = "หัวข้อ", dataType = "string", value = "หัวข้อ", required = true)
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    @ApiModelProperty(name = "content", example = "เนื้อหา", dataType = "string", value = "เนื้อหา", required = true)
    public void setContent(String content) {
        this.content = content;
    }

    public String getContentDate() {
        return contentDate;
    }

    @ApiModelProperty(name = "contentDate", example = "31/01/2560", dataType = "string", value = "วันที่ของเนื้อหา", required = true)
    public void setContentDate(String contentDate) {
        this.contentDate = contentDate;
    }

    public String getSearchContent() {
        return searchContent;
    }

    @ApiModelProperty(name = "searchContent", example = "เนื้อหาสำหรับค้นหา", dataType = "string", value = "เนื้อหาสำหรับค้นหา", required = true)
    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
    
    
}
