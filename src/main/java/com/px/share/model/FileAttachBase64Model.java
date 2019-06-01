package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OPAS
 */
@XmlRootElement(name = "FileAttachBase64")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ไฟล์เอกสารทั่วไป" )
public class FileAttachBase64Model extends VersionModel{
    
    private static final long serialVersionUID = -2390146749239059714L;
    
    @Since(1.0)
    @XmlElement(name = "fileAttachId")
    @ApiParam(name = "fileAttachId",value = "fileAttachId",required = true)
    @Size(max = 255)
    @Expose
    private int fileAttachId;
    
    @Since(1.0)
    @XmlElement(name = "fileAttachName")
    @ApiParam(name = "fileAttachName",value = "ชื่อไฟล์เอกสาร",required = true)
    @Size(max = 255)
    @Expose
    private String fileAttachName;
    @Since(1.0)
    @XmlElement(name = "fileAttachType")
    @ApiParam(name = "fileAttachType",value = "ประเภทไฟล์เอกสาร",required = true)
    @Size(max = 10)
    @Expose
    private String fileAttachType;
    @Since(1.0)
    @XmlElement(name = "linkType")
    @ApiParam(name = "linkType",value = "module การทำงาน",required = true)
    @Size(max = 12)
    @Expose
    private String linkType;
    @Since(1.0)
    @XmlElement(name = "linkId")
    @ApiParam(name = "linkId",value = "รหัส record id ของการทำงาน",required = true)
    @Expose
    private int linkId;
    @Since(1.0)
    @XmlElement(name = "fileBase64")
    @ApiParam(name = "fileBase64",value = "fileBase64",required = true)
    private String fileBase64;
    
    @Since(1.0)
    @XmlElement(name = "referenceId")
    @ApiParam(name = "referenceId",value = "referenceId",required = true)
    private int referenceId;
    
    @Since(1.0)
    @XmlElement(name = "secrets")
    @ApiParam(name = "secrets",value = "secrets",required = true)
    private int secrets;

    public FileAttachBase64Model() {
    }

    public String getFileAttachName() {
        return fileAttachName;
    }

    public String getFileAttachType() {
        return fileAttachType;
    }

    public String getLinkType() {
        return linkType;
    }

    public int getLinkId() {
        return linkId;
    }

    public String getFileBase64() {
        return fileBase64;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public int getSecrets() {
        return secrets;
    }

    public void setSecrets(int secrets) {
        this.secrets = secrets;
    }

    public int getFileAttachId() {
        return fileAttachId;
    }

    public void setFileAttachId(int fileAttachId) {
        this.fileAttachId = fileAttachId;
    }
    
    
    
    
}
