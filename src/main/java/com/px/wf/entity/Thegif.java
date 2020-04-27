package com.px.wf.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Mali
 */
@Entity
@Table(name = "PC_THEGIF")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "THEGIF_ID"))
})
public class Thegif extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -1634414678447721565L;

    @Column(name = "THEGIF_ELEMENT_TYPE", length = 255)
    private String thegifElementType;

    @Column(name = "THEGIF_PROCESS_ID", length = 255)
    private String thegifProcessId;

    @Column(name = "THEGIF_SEND_DATE", length = 255)
    private String thegifSendDate;

    @Column(name = "THEGIF_BOOK_NO", length = 255)
    private String thegifBookNo;

    @Column(name = "THEGIF_BOOK_DATE", length = 255)
    private String thegifBookDate;

    @Column(name = "THEGIF_SPEED", length = 10)
    private String thegifSpeed;

    @Column(name = "THEGIF_SECRET", length = 10)
    private String thegifSecret;

    @Column(name = "THEGIF_SENDER_DEPARTMENT_CODE", length = 10)
    private String thegifSenderDepartmentCode;

    @Column(name = "THEGIF_RECEIVER_DEPARTMENT_CODE", length = 10)
    private String thegifReceiverDepartmentCode;

    @Column(name = "THEGIF_ACCEPT_DATE", length = 255)
    private String thegifAcceptDate;

    @Column(name = "THEGIF_ACCEPT_ID", length = 255)
    private String thegifAcceptId;

    @Column(name = "THEGIF_ACCEPT_DEPARTMENT_CODE", length = 10)
    private String thegifAcceptDepartmentCode;
    
    @Column(name = "THEGIF_LETTER_STATUS", length = 255)
    private String thegifLetterStatus;
    
    @Column(name = "THEGIF_SUBJECT", columnDefinition="Text")
    private String thegifSubject;
    
    @Column(name = "THEGIF_DESCRIPTION", columnDefinition="Text")
    private String thegifDescription;
    
    @Column(name = "THEGIF_ATTACHMENT", columnDefinition="Text")
    private String thegifAttachment; 
    
    @Column(name = "THEGIF_REFERENCE", columnDefinition="Text")
    private String thegifReference; 
    
    @Column(name = "THEGIF_FILEPATH", length = 255)
    private String thegifFilePath; 
     
    @Column(name = "THEGIF_FROM", length = 1000) 
    private String thegifFrom;      
 
    @Column(name = "THEGIF_TO", length = 1000) 
    private String thegifTo;       
 
    public Thegif() {
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
   
    public String getThegifFrom() { 
        return thegifFrom; 
    } 
 
    public void setThegifFrom(String thegifFrom) { 
        this.thegifFrom = thegifFrom; 
    } 
 
    public String getThegifTo() { 
        return thegifTo; 
    } 
 
    public void setThegifTo(String thegifTo) { 
        this.thegifTo = thegifTo; 
    } 
   
}
