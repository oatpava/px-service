package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
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
 * @author Oat
 */
@XmlRootElement(name = "FileAttachWfSearch")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ไฟล์เอกสารแนบ")
public class FileAttachWfSearchModel extends VersionModel {

    private static final long serialVersionUID = -4396014127984217718L;

    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสไฟล์เอกสารแนบ", defaultValue = "0", required = false)
    @Expose
    @Since(1.0)
    private int id;

//    @XmlElement(name = "fileAttachName")
//    @ApiParam(name = "fileAttachName", example = "ทดสอบชื่อไฟล์", value = "ชื่อไฟล์เอกสาร", defaultValue = "ทดสอบชื่อไฟล์", required = false)
//    @Size(max = 255)
//    @Since(1.0)
//    @Expose
//    private String fileAttachName;
//
//    @XmlElement(name = "fileAttachType")
//    @ApiParam(name = "fileAttachType", example = ".TXT", value = "ประเภทไฟล์เอกสาร", defaultValue = ".px", required = false)
//    @Size(max = 10)
//    @Since(1.0)
//    @Expose
//    private String fileAttachType;
//
//    @XmlElement(name = "fileSize")
//    @ApiParam(name = "fileSize", example = "0", value = "ขนาดไฟล์", defaultValue = "0", required = false)
//    @Expose
//    @Since(1.0)
//    private long fileAttachSize;
//
//    @XmlElement(name = "linkType")
//    @ApiParam(name = "linkType", example = "admin,dms.workflow", value = "module การทำงาน", defaultValue = "admin", required = true)
//    @Size(max = 12)
//    @Since(1.0)
//    @Expose
//    private String linkType;
//
//    @XmlElement(name = "linkId")
//    @ApiParam(name = "linkId", example = "0", value = "รหัส record id ของการทำงาน", defaultValue = "0", required = true)
//    @Since(1.0)
//    @Expose
//    private int linkId;
//
//    @XmlElement(name = "referenceId")
//    @ApiParam(name = "referenceId", example = "0", value = "รหัส reference id ของการไฟล์ต้นฉบับ", defaultValue = "0", required = false)
//    @Since(1.0)
//    @Expose
//    private int referenceId;
//
//    @XmlElement(name = "secrets")
//    @ApiParam(name = "secrets", example = "1", value = "ชั้นความลับ", defaultValue = "1", required = false)
//    @Since(1.0)
//    @Expose
//    private int secrets;

    @XmlElement(name = "fullText")
    @ApiParam(name = "fullText", example = "xxxx", value = "fullText", required = false)
    @Since(1.0)
    @Expose
    private String fullText;

    public FileAttachWfSearchModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสไฟล์เอกสารแนบ", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

//    public String getFileAttachName() {
//        return fileAttachName;
//    }
//
//    @ApiModelProperty(name = "fileAttachName", example = "ทดสอบชื่อไฟล์", dataType = "string", value = "ทดสอบชื่อไฟล์", required = true)
//    public void setFileAttachName(String fileAttachName) {
//        this.fileAttachName = fileAttachName;
//    }
//
//    public String getFileAttachType() {
//        return fileAttachType;
//    }
//
//    @ApiModelProperty(name = "fileAttachType", example = ".TXT", dataType = "string", value = "ประเภทไฟล์เอกสาร", required = true)
//    public void setFileAttachType(String fileAttachType) {
//        this.fileAttachType = fileAttachType;
//    }
//
//    public String getLinkType() {
//        return linkType;
//    }
//
//    @ApiModelProperty(name = "linkType", example = "admin,dms.workflow", dataType = "string", value = "module การทำงาน", required = true)
//    public void setLinkType(String linkType) {
//        this.linkType = linkType;
//    }
//
//    public int getLinkId() {
//        return linkId;
//    }
//
//    @ApiModelProperty(name = "linkId", example = "0", dataType = "int", value = "รหัส record id ของการทำงาน", required = true)
//    public void setLinkId(int linkId) {
//        this.linkId = linkId;
//    }
//
//    public long getFileAttachSize() {
//        return fileAttachSize;
//    }
//
//    @ApiModelProperty(name = "fileAttachSize", example = "0", dataType = "float", value = "ขนาดไฟล์", required = false)
//    public void setFileAttachSize(long fileAttachSize) {
//        this.fileAttachSize = fileAttachSize;
//    }
//
//    public int getReferenceId() {
//        return this.referenceId;
//    }
//
//    @ApiModelProperty(name = "referenceId", example = "0", dataType = "int", value = "รหัส reference id ของการทำงาน", required = true)
//    public void setReferenceId(int referenceId) {
//        this.referenceId = referenceId;
//    }
//
//    public int getSecrets() {
//        return secrets;
//    }
//
//    @ApiModelProperty(name = "secrets", example = "1", dataType = "int", value = "ชั้นความลับ", required = true)
//    public void setSecrets(int secrets) {
//        this.secrets = secrets;
//    }
    
    public String getFulltext() {
        return fullText;
    }

    @ApiModelProperty(name = "fullText", example = "xxx", dataType = "string", value = "fullText", required = false)
    public void setFulltext(String fullText) {
        this.fullText = fullText;
    }

}
