package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author oat
 */
@XmlRootElement(name = "wfRecord")
@ApiModel(description = "บันทึกปฏิบัติงาน")
public class WfRecordModel extends VersionModel {

    private static final long serialVersionUID = -2323L;

    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสบันทึกปฏิบัติงาน", defaultValue = "0", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสบันทึกปฏิบัติงาน", required = true)
    private Integer id;

    @XmlElement(name = "createdDate")
    @ApiParam(name = "createdDate", value = "วันเวลาที่บันทึก", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "createdDate", example = "31/12/2562", dataType = "String", value = "วันเวลาที่บันทึก", required = true)
    private String createdDate;

    @XmlElement(name = "contentId")
    @ApiParam(name = "contentId", example = "0", value = "รหัสหนังสือ", defaultValue = "0", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "contentId", example = "0", dataType = "int", value = "รหัสหนังสือ", required = true)
    private Integer contentId;

    @XmlElement(name = "documentId")
    @ApiParam(name = "documentId", example = "0", value = "รหัส flow", defaultValue = "0", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "documentId", example = "0", dataType = "int", value = "รหัส flow", required = true)
    private Integer documentId;

    @XmlElement(name = "description")
    @ApiParam(name = "description", value = "รายละเอียดบันทึกปฏิบัติงาน", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "description", example = "ทดสอบ", dataType = "String", value = "รายละเอียดบันทึกปฏิบัติงาน", required = false)
    private String description;

    @XmlElement(name = "creator")
    @ApiParam(name = "creator", value = "ผุู้สร้างบันทึกปฏิบัติงาน", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "creator", example = "ทดสอบ", dataType = "String", value = "ผุู้สร้างบันทึกปฏิบัติงาน", required = false)
    private String creator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
