package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oat
 */
@XmlRootElement(name = "ImportWfContentModel")
@ApiModel(description = "ข้อมูลหนังสือ")
public class ImportWfContentModel {

    @XmlElement(name = "bookDate")
    @ApiParam(name = "bookDate", example = "31/01/2566", value = "ลงวันที่", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "bookDate", example = "31/01/2566", dataType = "String", value = "ลงวันที่", required = true)
    private String bookDate;

    @XmlElement(name = "speedLevel")
    @ApiParam(name = "speedLevel", value = "ชั้นเร่งด่วน", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "speedLevel", dataType = "Integer", value = "ชั้นเร่งด่วน", required = true)
    private Integer speedLevel;

    @XmlElement(name = "secretLevel")
    @ApiParam(name = "secretLevel", value = "ชั้นความลับ", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "secretLevel", dataType = "Integer", value = "ชั้นความลับ", required = true)
    private Integer secretLevel;

    @XmlElement(name = "from")
    @ApiParam(name = "from", value = "จาก", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "from", dataType = "String", value = "จาก", required = true)
    @Size(max = 1000)
    private String from;

    @XmlElement(name = "to")
    @ApiParam(name = "to", value = "ถึง", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "to", dataType = "String[]", value = "ถึง", required = true)
    @Size(max = 4000)
    private String[] to;

    @XmlElement(name = "thru")
    @ApiParam(name = "thru", value = "ผ่าน", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "thru", dataType = "String[]", value = "ผ่าน", required = false)
    @Size(max = 4000)
    private String[] thru;

    @XmlElement(name = "title")
    @ApiParam(name = "title", value = "เรื่อง", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "title", dataType = "String", value = "เรื่อง", required = true)
    @Size(max = 4000)
    private String title;

    @XmlElement(name = "referTo")
    @ApiParam(name = "referTo", value = "อ้างถึง", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "referTo", dataType = "String", value = "อ้างถึง", required = false)
    @Size(max = 4000)
    private String referTo;

    @XmlElement(name = "attachWith")
    @ApiParam(name = "attachWith", value = "สิ่งที่ส่งมาด้วย", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "attachWith", dataType = "String", value = "สิ่งที่ส่งมาด้วย", required = false)
    @Size(max = 4000)
    private String attachWith;

    @XmlElement(name = "procedure")
    @ApiParam(name = "procedure", value = "การปฏิบัติ", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "procedure", dataType = "String", value = "การปฏิบัติ", required = false)
    @Size(max = 4000)
    private String procedure;

    @XmlElement(name = "remark")
    @ApiParam(name = "remark", value = "หมายเหตุ", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "remark", dataType = "String", value = "หมายเหตุ", required = false)
    @Size(max = 4000)
    private String remark;

    @XmlElement(name = "hardCopyReceiveDateTime")
    @ApiParam(name = "hardCopyReceiveDateTime", example = "31/01/2566 10:30", value = "วันเวลาที่ได้รับเอกสารตัวจริง", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "hardCopyReceiveDateTime", example = "31/01/2566  10:30", dataType = "String", value = "วันเวลาที่ได้รับเอกสารตัวจริง", required = false)
    private String hardCopyReceiveDateTime;
   

    @XmlElement(name = "postNo")
    @ApiParam(name = "postNo", value = "ไปรษณีย์ลงทะเบียน", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "postNo", dataType = "String", value = "ไปรษณีย์ลงทะเบียน", required = false)
    @Size(max = 255)
    private String postNo;

    @XmlElement(name = "fileAttach")
    @ApiParam(name = "fileAttach", value = "เอกสารแนบ", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "fileAttach", dataType = "JSON", value = "เอกสารแนบ", required = false)
    private ImportFileAttachModel fileAttach;

    public ImportWfContentModel() {
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public Integer getSecretLevel() {
        return secretLevel;
    }

    public void setSecretLevel(Integer secretLevel) {
        this.secretLevel = secretLevel;
    }

    public Integer getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(Integer speedLevel) {
        this.speedLevel = speedLevel;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getThru() {
        return thru;
    }

    public void setThru(String[] thru) {
        this.thru = thru;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReferTo() {
        return referTo;
    }

    public void setReferTo(String referTo) {
        this.referTo = referTo;
    }

    public String getAttachWith() {
        return attachWith;
    }

    public void setAttachWith(String attachWith) {
        this.attachWith = attachWith;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHardCopyReceiveDateTime() {
        return hardCopyReceiveDateTime;
    }

    public void setHardCopyReceiveDateTime(String hardCopyReceiveDateTime) {
        this.hardCopyReceiveDateTime = hardCopyReceiveDateTime;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public ImportFileAttachModel getFileAttach() {
        return fileAttach;
    }

    public void setFileAttach(ImportFileAttachModel fileAttach) {
        this.fileAttach = fileAttach;
    }

}
