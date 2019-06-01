/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.wf.model;

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
 * @author Oat
 */
@XmlRootElement(name = "Email")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "email")
public class EmailModel extends VersionModel {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "from")
    @ApiParam(name = "from", value = "from", required = true)
    @Since(1.0)
    @Expose
    private String from;

    @XmlElement(name = "to")
    @ApiParam(name = "to", value = "to", required = true)
    @Since(1.0)
    @Expose
    private String to;

    @XmlElement(name = "cc")
    @ApiParam(name = "cc", value = "cc", required = true)
    @Since(1.0)
    @Expose
    private String cc;

    @XmlElement(name = "bcc")
    @ApiParam(name = "bcc", value = "bcc", required = true)
    @Since(1.0)
    @Expose
    private String bcc;

    @XmlElement(name = "subject")
    @ApiParam(name = "subject", value = "subject", required = true)
    @Since(1.0)
    @Expose
    private String subject;

    @XmlElement(name = "body")
    @ApiParam(name = "body", value = "body", required = true)
    @Since(1.0)
    @Expose
    private String body;

    @XmlElement(name = "fileAttachsId")
    @ApiParam(name = "fileAttachsId", value = "fileAttachsId", required = true)
    @Since(1.0)
    @Expose
    private String fileAttachsId;

    public String getFrom() {
        return from;
    }

    @ApiModelProperty(name = "from", example = "a@gmail.com,b@gmail.com", dataType = "string", value = "email ผู้ส่ง ", required = true)
    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    @ApiModelProperty(name = "to", example = "a@gmail.com,b@gmail.com", dataType = "string", value = "email รับ ", required = false)
    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    @ApiModelProperty(name = "cc", example = "a@gmail.com,b@gmail.com", dataType = "string", value = "email Cc ", required = false)
    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    @ApiModelProperty(name = "bcc", example = "a@gmail.com,b@gmail.com", dataType = "string", value = "email Bcc ", required = false)
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    @ApiModelProperty(name = "subject", example = "ชื่อเรื่อง", dataType = "string", value = "subject", required = false)
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getBody() {
        return body;
    }

     @ApiModelProperty(name = "body", example = "รายละเอียด", dataType = "string", value = "body", required = false)
    public void setBody(String body) {
        this.body = body;
    }

    public String getFileattachsId() {
        return fileAttachsId;
    }

     @ApiModelProperty(name = "fileAttachsId", example = "1,2,3", dataType = "string", value = "list id เอกสารแนบ", required = false)
    public void setFileattachsId(String fileAttachsId) {
        this.fileAttachsId = fileAttachsId;
    }
}
