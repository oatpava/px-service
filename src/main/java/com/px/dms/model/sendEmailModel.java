/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.model;

//import com.px.dms.model.post.DmsMenuPostModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TOP
 */
@XmlRootElement(name = "SendEmail")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ส่งอีเมล")
public class sendEmailModel extends VersionModel {

    public static final long serialVersionUID = 0L;

    @XmlElement(name = "From")
    @ApiParam(name = "From", value = "email ผู้ส่ง a@gmail.com,b@gmail.com,..", required = true)
    @Since(1.0)
    @Expose
    private String From;

    @XmlElement(name = "To")
    @ApiParam(name = "To", value = "email ผู้รับ a@gmail.com,b@gmail.com,..", required = false)
    @Since(1.0)
    @Expose
    private String To;
    
    @XmlElement(name = "Cc")
    @ApiParam(name = "Cc", value = "email Cc a@gmail.com,b@gmail.com,..", required = false)
    @Since(1.0)
    @Expose
    private String Cc;

    @XmlElement(name = "Bcc")
    @ApiParam(name = "Bcc", value = "email Bcc a@gmail.com,b@gmail.com,..", required = false)
    @Since(1.0)
    @Expose
    private String Bcc;

    @XmlElement(name = "Subject")
    @ApiParam(name = "Subject", value = "ชื่อหัวข้อ email", required = false)
    @Since(1.0)
    @Expose
    private String Subject;
    
    @XmlElement(name = "Detail")
    @ApiParam(name = "Detail", value = "รายละเอียด email", required = false)
    @Since(1.0)
    @Expose
    private String Detail;
    
    @XmlElement(name = "ListAttachID")
    @ApiParam(name = "ListAttachID", value = "list เอกสารแนบ id,id,id", required = false)
    @Since(1.0)
    @Expose
    private String ListAttachID;

    public sendEmailModel() {
    }

    public String getFrom() {
        return From;
    }

     @ApiModelProperty(name = "From", example = "a@gmail.com,b@gmail.com", dataType = "string", value = "email ผู้ส่ง ", required = true)
    public void setFrom(String From) {
        this.From = From;
    }

    public String getTo() {
        return To;
    }

     @ApiModelProperty(name = "To", example = "a@gmail.com,b@gmail.com", dataType = "string", value = "email รับ ", required = false)
    public void setTo(String To) {
        this.To = To;
    }

    public String getCc() {
        return Cc;
    }
 @ApiModelProperty(name = "Cc", example = "a@gmail.com,b@gmail.com", dataType = "string", value = "email Cc ", required = false)
    public void setCc(String Cc) {
        this.Cc = Cc;
    }

    public String getBcc() {
        return Bcc;
    }

    @ApiModelProperty(name = "Bcc", example = "a@gmail.com,b@gmail.com", dataType = "string", value = "email Bcc ", required = false)
    public void setBcc(String Bcc) {
        this.Bcc = Bcc;
    }

    public String getSubject() {
        return Subject;
    }

    @ApiModelProperty(name = "Subject", example = "ชื่อเรื่อง", dataType = "string", value = "Subject", required = false)
    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getDetail() {
        return Detail;
    }

     @ApiModelProperty(name = "Detail", example = "รายละเอียด", dataType = "string", value = "Detail", required = false)
    public void setDetail(String Detail) {
        this.Detail = Detail;
    }

    public String getListAttachID() {
        return ListAttachID;
    }

     @ApiModelProperty(name = "ListAttachID", example = "1,2,3", dataType = "string", value = "list id เอกสารแนบ", required = false)
    public void setListAttachID(String ListAttachID) {
        this.ListAttachID = ListAttachID;
    }
    
    
}
