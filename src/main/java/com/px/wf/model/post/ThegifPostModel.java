
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
@XmlRootElement(name = "thegif")
@ApiModel(description = "การสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ")
public class ThegifPostModel extends VersionModel {

    private static final long serialVersionUID = 8800755075806465383L;
    
    @XmlElement(name = "thegifElementType")
    @ApiParam(name = "thegifElementType", value = "ประเภท", required = true)
    @FormParam("thegifElementType")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifElementType;
    
    @XmlElement(name = "thegifProcessId")
    @ApiParam(name = "thegifProcessId", value = "รหัสการดำเนินการ", required = false)
    @FormParam("thegifProcessId")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifProcessId;
    
    @XmlElement(name = "thegifSendDate")
    @ApiParam(name = "thegifSendDate", value = "วันที่ส่ง", required = false)
    @FormParam("thegifSendDate")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifSendDate;
    
    @XmlElement(name = "thegifBookNo")
    @ApiParam(name = "thegifBookNo", value = "เลขที่หนังสือ", required = false)
    @FormParam("thegifBookNo")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifBookNo;
    
    @XmlElement(name = "thegifBookDate")
    @ApiParam(name = "thegifBookDate", value = "ลงวันที่", required = false)
    @FormParam("thegifBookDate")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifBookDate;
    
    @XmlElement(name = "thegifSpeed")
    @ApiParam(name = "thegifSpeed", value = "ชั้นความเร็ว", required = false)
    @FormParam("thegifSpeed")
    @Size(max = 10)
    @Since(1.0)
    @Expose private String thegifSpeed;
    
    @XmlElement(name = "thegifSecret")
    @ApiParam(name = "thegifSecret", value = "ชั้นความลับ", required = false)
    @FormParam("thegifSecret")
    @Size(max = 10)
    @Since(1.0)
    @Expose private String thegifSecret;
    
    @XmlElement(name = "thegifSenderDepartmentCode")
    @ApiParam(name = "thegifSenderDepartmentCode", value = "รหัสโครงสร้างผู้ส่ง", required = false)
    @FormParam("thegifSenderDepartmentCode")
    @Size(max = 10)
    @Since(1.0)
    @Expose private String thegifSenderDepartmentCode;
    
    @XmlElement(name = "thegifReceiverDepartmentCode")
    @ApiParam(name = "thegifReceiverDepartmentCode", value = "รหัสโครงสร้างผู้รับ", required = false)
    @FormParam("thegifReceiverDepartmentCode")
    @Size(max = 10)
    @Since(1.0)
    @Expose private String thegifReceiverDepartmentCode;
     
    @XmlElement(name = "thegifAcceptDate")
    @ApiParam(name = "thegifAcceptDate", value = "วันที่ลงรับ", required = false)
    @FormParam("thegifAcceptDate")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifAcceptDate;

    @XmlElement(name = "thegifAcceptId")
    @ApiParam(name = "thegifAcceptId", value = "เลขที่รับ", required = false)
    @FormParam("thegifAcceptId")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifAcceptId;
    
    @XmlElement(name = "thegifAcceptDepartmentCode")
    @ApiParam(name = "thegifAcceptDepartmentCode", value = "รหัสโครงสร้างผู้ลงรับ", required = false)//เอาไปลงทะเบียน
    @FormParam("thegifAcceptDepartmentCode")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifAcceptDepartmentCode;
    
    @XmlElement(name = "thegifLetterStatus")
    @ApiParam(name = "thegifLetterStatus", value = "สถานะจดหมาย", required = false)
    @FormParam("thegifLetterStatus")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifLetterStatus;
    
    @XmlElement(name = "thegifSubject")
    @ApiParam(name = "thegifSubject", value = "เรื่อง", required = false)
    @FormParam("thegifSubject")
    @Size(max = 4000)
    @Since(1.0)
    @Expose private String thegifSubject;
    
    @XmlElement(name = "thegifDescription")
    @ApiParam(name = "thegifDescription", value = "คำอธิบาย", required = false)
    @FormParam("thegifDescription")
    @Size(max = 4000)
    @Since(1.0)
    @Expose private String thegifDescription;
    
    @XmlElement(name = "thegifAttachment")
    @ApiParam(name = "thegifAttachment", value = "แนบไฟล์", required = false)
    @FormParam("thegifAttachment")
    @Size(max = 4000)
    @Since(1.0)
    @Expose private String thegifAttachment;
    
    @XmlElement(name = "thegifReference")
    @ApiParam(name = "thegifReference", value = "อ้างถึง", required = false)
    @FormParam("thegifReference")
    @Size(max = 4000)
    @Since(1.0)
    @Expose private String thegifReference;
    
    @XmlElement(name = "thegifFilePath")
    @ApiParam(name = "thegifFilePath", value = "FilePath", required = false)
    @FormParam("thegifFilePath")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifFilePath;
    
    public ThegifPostModel() {
    }

    public String getThegifElementType() {
        return thegifElementType;
    }

    public void setThegifElementType(String thegifElementType) {
        this.thegifElementType = thegifElementType;
    }

    public String getThegifProcessId() {
        return thegifProcessId;
    }

    public void setThegifProcessId(String thegifProcessId) {
        this.thegifProcessId = thegifProcessId;
    }

    public String getThegifSendDate() {
        return thegifSendDate;
    }

    public void setThegifSendDate(String thegifSendDate) {
        this.thegifSendDate = thegifSendDate;
    }

    public String getThegifBookNo() {
        return thegifBookNo;
    }

    public void setThegifBookNo(String thegifBookNo) {
        this.thegifBookNo = thegifBookNo;
    }

    public String getThegifBookDate() {
        return thegifBookDate;
    }

    public void setThegifBookDate(String thegifBookDate) {
        this.thegifBookDate = thegifBookDate;
    }

    public String getThegifSpeed() {
        return thegifSpeed;
    }

    public void setThegifSpeed(String thegifSpeed) {
        this.thegifSpeed = thegifSpeed;
    }

    public String getThegifSecret() {
        return thegifSecret;
    }

    public void setThegifSecret(String thegifSecret) {
        this.thegifSecret = thegifSecret;
    }

    public String getThegifSenderDepartmentCode() {
        return thegifSenderDepartmentCode;
    }

    public void setThegifSenderDepartmentCode(String thegifSenderDepartmentCode) {
        this.thegifSenderDepartmentCode = thegifSenderDepartmentCode;
    }

    public String getThegifReceiverDepartmentCode() {
        return thegifReceiverDepartmentCode;
    }

    public void setThegifReceiverDepartmentCode(String thegifReceiverDepartmentCode) {
        this.thegifReceiverDepartmentCode = thegifReceiverDepartmentCode;
    }

    public String getThegifAcceptDate() {
        return thegifAcceptDate;
    }

    public void setThegifAcceptDate(String thegifAcceptDate) {
        this.thegifAcceptDate = thegifAcceptDate;
    }

    public String getThegifAcceptId() {
        return thegifAcceptId;
    }

    public void setThegifAcceptId(String thegifAcceptId) {
        this.thegifAcceptId = thegifAcceptId;
    }

    public String getThegifAcceptDepartmentCode() {
        return thegifAcceptDepartmentCode;
    }

    public void setThegifAcceptDepartmentCode(String thegifAcceptDepartmentCode) {
        this.thegifAcceptDepartmentCode = thegifAcceptDepartmentCode;
    }

    public String getThegifLetterStatus() {
        return thegifLetterStatus;
    }

    public void setThegifLetterStatus(String thegifLetterStatus) {
        this.thegifLetterStatus = thegifLetterStatus;
    }

    public String getThegifSubject() {
        return thegifSubject;
    }

    public void setThegifSubject(String thegifSubject) {
        this.thegifSubject = thegifSubject;
    }

    public String getThegifDescription() {
        return thegifDescription;
    }

    public void setThegifDescription(String thegifDescription) {
        this.thegifDescription = thegifDescription;
    }

    public String getThegifAttachment() {
        return thegifAttachment;
    }

    public void setThegifAttachment(String thegifAttachment) {
        this.thegifAttachment = thegifAttachment;
    }

    public String getThegifReference() {
        return thegifReference;
    }

    public void setThegifReference(String thegifReference) {
        this.thegifReference = thegifReference;
    }

    public String getThegifFilePath() {
        return thegifFilePath;
    }

    public void setThegifFilePath(String thegifFilePath) {
        this.thegifFilePath = thegifFilePath;
    }

}
