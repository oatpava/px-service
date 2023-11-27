package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oat
 */
@XmlRootElement(name = "ImportFileAttachModel")
@ApiModel(description = "ข้อมูลเอกสารแนบ")
public class ImportFileAttachModel {

    @XmlElement(name = "name")
    @ApiParam(name = "name", value = "ชื่อไฟล์", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "name", dataType = "string", value = "ชื่อไฟล์", required = true)
    private String name;

    @XmlElement(name = "secretLevel")
    @ApiParam(name = "secretLevel", value = "ชั้นความลับ", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "secretLevel", dataType = "int", value = "ชั้นความลับ", required = true)
    private int secretLevel;

    @XmlElement(name = "fileBase64")
    @ApiParam(name = "fileBase64", value = "ไฟล์", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "fileBase64", dataType = "string", value = "ไฟล์", required = true)
    private String fileBase64;

    public ImportFileAttachModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(int secretLevel) {
        this.secretLevel = secretLevel;
    }

    public String getFileBase64() {
        return fileBase64;
    }

    public void setFileBase64(String fileBase64) {
        this.fileBase64 = fileBase64;
    }

}
