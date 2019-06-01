
package com.px.wf.model.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "ThegifDocFile")
@ApiModel(description = "เอกสารของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ")
public class ThegifDocFilePostModel extends VersionModel {

    private static final long serialVersionUID = 3595966468975158327L;
    
    @XmlElement(name = "thegifId")
    @ApiParam(name = "thegifId", value = "รหัสการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ", required = true)
    @FormParam("thegifId")
    @Since(1.0)
    @Expose private Integer thegifId;
    
    @XmlElement(name = "thegifDocFileFileId")
    @ApiParam(name = "thegifDocFileFileId", value = "รหัสเอกสารที่", required = true)
    @FormParam("thegifDocFileFileId")
    @Since(1.0)
    @Expose private Integer thegifDocFileFileId;
    
    @XmlElement(name = "thegifDocFileLetter")
    @ApiParam(name = "thegifDocFileLetter", value = "จดหมาย", required = false)
    @FormParam("thegifDocFileLetter")
    @Size(max = 4000)
    @Since(1.0)
    @Expose private String thegifDocFileLetter;
    
    @XmlElement(name = "thegifDocFileLetterType")
    @ApiParam(name = "thegifDocFileLetterType", value = "ประเภทจดหมาย", required = false)
    @FormParam("thegifDocFileLetterType")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifDocFileLetterType;
    
    @XmlElement(name = "dmsDocumentId")
    @ApiParam(name = "dmsDocumentId", value = "รหัสเอกสาร", required = true)
    @FormParam("dmsDocumentId")
    @Since(1.0)
    @Expose private Integer dmsDocumentId;
    
    public ThegifDocFilePostModel() {
    }

    public Integer getThegifId() {
        return thegifId;
    }

    public void setThegifId(Integer thegifId) {
        this.thegifId = thegifId;
    }

    public Integer getThegifDocFileFileId() {
        return thegifDocFileFileId;
    }

    public void setThegifDocFileFileId(Integer thegifDocFileFileId) {
        this.thegifDocFileFileId = thegifDocFileFileId;
    }

    public String getThegifDocFileLetter() {
        return thegifDocFileLetter;
    }

    public void setThegifDocFileLetter(String thegifDocFileLetter) {
        this.thegifDocFileLetter = thegifDocFileLetter;
    }

    public String getThegifDocFileLetterType() {
        return thegifDocFileLetterType;
    }

    public void setThegifDocFileLetterType(String thegifDocFileLetterType) {
        this.thegifDocFileLetterType = thegifDocFileLetterType;
    }

    public Integer getDmsDocumentId() {
        return dmsDocumentId;
    }

    public void setDmsDocumentId(Integer dmsDocumentId) {
        this.dmsDocumentId = dmsDocumentId;
    }
    
}
