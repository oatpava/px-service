
package com.px.wf.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
@Entity
@Table(name = "PC_WF_CONTENT")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WF_CONTENT_ID"))
})
public class WfContent extends BaseEntity {

    @Transient
    private static final long serialVersionUID = 3973170866035597739L;

    @Column(name = "WF_DOCUMENT_ID", columnDefinition = "int default '0'")
    private Integer wfDocumentId;

    @Column(name = "WORKFLOW_ID", columnDefinition = "int default '0'")
    private Integer workflowId;

    @Column(name = "WF_CONTENT_FOLDER_ID", columnDefinition = "int default '0'")
    private Integer wfContentFolderId;

    @Column(name = "WF_CONTENT_CONTENT_PRE", length = 50)
    private String wfContentContentPre;

    @Column(name = "WF_CONTENT_CONTENT_YEAR", columnDefinition = "int default '0'")
    private Integer wfContentContentYear;

    @Column(name = "WF_CONTENT_CONTENT_NO", length = 50)
    private String wfContentContentNo;

    @Column(name = "WF_CONTENT_CONTENT_NUMBER", columnDefinition = "int default '0'")
    private int wfContentContentNumber;

    @Column(name = "WF_CONTENT_CONTENT_POINT", columnDefinition = "int default '0'")
    private int wfContentContentPoint;

    @Column(name = "WF_CONTENT_CONTENT_DATE", nullable = false)
    private LocalDateTime wfContentContentDate;

    @Column(name = "WF_CONTENT_CONTENT_TIME", length = 10)
    private String wfContentContentTime;

    @Column(name = "WF_CONTENT_BOOK_PRE", length = 50)
    private String wfContentBookPre;

    @Column(name = "WF_CONTENT_BOOK_YEAR", columnDefinition = "int default '0'")
    private Integer wfContentBookYear;

    @Column(name = "WF_CONTENT_BOOK_NO", length = 50)
    private String wfContentBookNo;

    @Column(name = "WF_CONTENT_BOOK_NUMBER", columnDefinition = "int default '0'")
    private int wfContentBookNumber;

    @Column(name = "WF_CONTENT_BOOK_POINT", columnDefinition = "int default '0'")
    private int wfContentBookPoint;

    @Column(name = "WF_CONTENT_BOOK_DATE", nullable = true)
    private LocalDateTime wfContentBookDate;

    @Column(name = "WF_CONTENT_FROM", length = 1000)
    private String wfContentFrom;

    //@Column(name = "WF_CONTENT_TO", columnDefinition="Text")
    @Column(name = "WF_CONTENT_TO", length = 4000)
    private String wfContentTo;

    //@Column(name = "WF_CONTENT_TITLE", columnDefinition="Text")
    @Column(name = "WF_CONTENT_TITLE", length = 4000)
    private String wfContentTitle;

    @Column(name = "WF_CONTENT_SPEED", columnDefinition = "int default '0'")
    private int wfContentSpeed;

    @Column(name = "WF_CONTENT_SECRET", columnDefinition = "int default '0'")
    private int wfContentSecret;

    //@Column(name = "WF_CONTENT_DESCRIPTION", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_DESCRIPTION", nullable = true, length = 4000)
    private String wfContentDescription;

    //@Column(name = "WF_CONTENT_REFERENCE", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_REFERENCE", nullable = true, length = 4000)
    private String wfContentReference;

    //@Column(name = "WF_CONTENT_ATTACHMENT", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_ATTACHMENT", nullable = true, length = 4000)
    private String wfContentAttachment;

    @Column(name = "WF_CONTENT_EXPIRE_DATE", nullable = true)
    private LocalDateTime wfContentExpireDate;

    @Column(name = "WF_CONTENT_OWNERNAME", length = 255)
    private String wfContentOwnername;

    @Column(name = "INBOX_ID", columnDefinition = "int default '0'")
    private int inboxId;

    @Column(name = "WF_CONTENT_STR01", nullable = true, length = 255)
    private String wfContentStr01;

    @Column(name = "WF_CONTENT_STR02", nullable = true, length = 255)
    private String wfContentStr02;

    @Column(name = "WF_CONTENT_STR03", nullable = true, length = 255)
    private String wfContentStr03;

    @Column(name = "WF_CONTENT_STR04", nullable = true, length = 255)
    private String wfContentStr04;

    @Column(name = "WF_CONTENT_STR05", nullable = true, length = 255)
    private String wfContentStr05;

    @Column(name = "WF_CONTENT_STR06", nullable = true, length = 255)
    private String wfContentStr06;

    @Column(name = "WF_CONTENT_STR07", nullable = true, length = 255)
    private String wfContentStr07;

    @Column(name = "WF_CONTENT_STR08", nullable = true, length = 255)
    private String wfContentStr08;

    @Column(name = "WF_CONTENT_STR09", nullable = true, length = 255)
    private String wfContentStr09;

    @Column(name = "WF_CONTENT_STR10", nullable = true, length = 255)
    private String wfContentStr10;

    //@Column(name = "WF_CONTENT_TEXT01", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_TEXT01", nullable = true, length = 4000)
    private String wfContentText01;

    //@Column(name = "WF_CONTENT_TEXT02", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_TEXT02", nullable = true, length = 4000)
    private String wfContentText02;

    //@Column(name = "WF_CONTENT_TEXT03", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_TEXT03", nullable = true, length = 4000)
    private String wfContentText03;

    //@Column(name = "WF_CONTENT_TEXT04", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_TEXT04", nullable = true, length = 4000)
    private String wfContentText04;

    //@Column(name = "WF_CONTENT_TEXT05", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_TEXT05", nullable = true, length = 4000)
    private String wfContentText05;

    //@Column(name = "WF_CONTENT_TEXT06", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_TEXT06", nullable = true, length = 4000)
    private String wfContentText06;

    //@Column(name = "WF_CONTENT_TEXT07", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_TEXT07", nullable = true, length = 4000)
    private String wfContentText07;

    //@Column(name = "WF_CONTENT_TEXT08", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_TEXT08", nullable = true, length = 4000)
    private String wfContentText08;

    //@Column(name = "WF_CONTENT_TEXT09", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_TEXT09", nullable = true, length = 4000)
    private String wfContentText09;

    //@Column(name = "WF_CONTENT_TEXT10", nullable = true, columnDefinition="Text")
    @Column(name = "WF_CONTENT_TEXT10", nullable = true, length = 4000)
    private String wfContentText10;

    @Column(name = "WF_CONTENT_INT01", columnDefinition = "int default '0'")
    private Integer wfContentInt01;

    @Column(name = "WF_CONTENT_INT02", columnDefinition = "int default '0'")
    private Integer wfContentInt02;

    @Column(name = "WF_CONTENT_INT03", columnDefinition = "int default '0'")
    private Integer wfContentInt03;

    @Column(name = "WF_CONTENT_INT04", columnDefinition = "int default '0'")
    private Integer wfContentInt04;

    @Column(name = "WF_CONTENT_INT05", columnDefinition = "int default '0'")
    private Integer wfContentInt05;

    @Column(name = "WF_CONTENT_INT06", columnDefinition = "int default '0'")
    private Integer wfContentInt06;

    @Column(name = "WF_CONTENT_INT07", columnDefinition = "int default '0'")
    private Integer wfContentInt07;

    @Column(name = "WF_CONTENT_INT08", columnDefinition = "int default '0'")
    private Integer wfContentInt08;

    @Column(name = "WF_CONTENT_INT09", columnDefinition = "int default '0'")
    private Integer wfContentInt09;

    @Column(name = "WF_CONTENT_INT10", columnDefinition = "int default '0'")
    private Integer wfContentInt10;

    @Column(name = "WF_CONTENT_DATE01", nullable = true)
    private LocalDateTime wfContentDate01;

    @Column(name = "WF_CONTENT_DATE02", nullable = true)
    private LocalDateTime wfContentDate02;

    @Column(name = "WF_CONTENT_DATE03", nullable = true)
    private LocalDateTime wfContentDate03;

    @Column(name = "WF_CONTENT_DATE04", nullable = true)
    private LocalDateTime wfContentDate04;

    @Column(name = "WF_CONTENT_DATE05", nullable = true)
    private LocalDateTime wfContentDate05;

    @Column(name = "WF_CONTENT_DATE06", nullable = true)
    private LocalDateTime wfContentDate06;

    @Column(name = "WF_CONTENT_DATE07", nullable = true)
    private LocalDateTime wfContentDate07;

    @Column(name = "WF_CONTENT_DATE08", nullable = true)
    private LocalDateTime wfContentDate08;

    @Column(name = "WF_CONTENT_DATE09", nullable = true)
    private LocalDateTime wfContentDate09;

    @Column(name = "WF_CONTENT_DATE10", nullable = true)
    private LocalDateTime wfContentDate10;
    
    @Column(name = "F_TRANS_MAIN_ID", columnDefinition = "int default '0'")
    private Integer fTransMainId;
    
    @Column(name = "F_ORG_ID", columnDefinition = "int default '0'")
    private Integer fOrgId;
     
    @Column(name = "F_TYPE", columnDefinition = "int default '0'")
    private Integer fType;
    
    @Column(name = "CONVERT_ID", columnDefinition = "int default '0'")
    private Integer convertId;

    public WfContent() {
    }

    public WfContent(Integer createdBy) {
        super(createdBy);
    }

    public Integer getWfDocumentId() {
        return wfDocumentId;
    }

    public void setWfDocumentId(Integer wfDocumentId) {
        this.wfDocumentId = wfDocumentId;
    }

    public Integer getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Integer workflowId) {
        this.workflowId = workflowId;
    }

    public Integer getWfContentFolderId() {
        return wfContentFolderId;
    }

    public void setWfContentFolderId(Integer wfContentFolderId) {
        this.wfContentFolderId = wfContentFolderId;
    }

    public String getWfContentContentPre() {
        return wfContentContentPre;
    }

    public void setWfContentContentPre(String wfContentContentPre) {
        this.wfContentContentPre = wfContentContentPre;
    }

    public Integer getWfContentYear() {
        return wfContentContentYear;
    }

    public void setWfContentYear(Integer wfContentContentYear) {
        this.wfContentContentYear = wfContentContentYear;
    }

    public String getWfContentContentNo() {
        return wfContentContentNo;
    }

    public void setWfContentContentNo(String wfContentContentNo) {
        this.wfContentContentNo = wfContentContentNo;
    }

    public int getWfContentContentNumber() {
        return wfContentContentNumber;
    }

    public void setWfContentContentNumber(int wfContentContentNumber) {
        this.wfContentContentNumber = wfContentContentNumber;
    }

    public int getWfContentContentPoint() {
        return wfContentContentPoint;
    }

    public void setWfContentContentPoint(int wfContentContentPoint) {
        this.wfContentContentPoint = wfContentContentPoint;
    }

    public LocalDateTime getWfContentContentDate() {
        return wfContentContentDate;
    }

    public void setWfContentContentDate(LocalDateTime wfContentContentDate) {
        this.wfContentContentDate = wfContentContentDate;
    }

    public String getWfContentContentTime() {
        return wfContentContentTime;
    }

    public void setWfContentContentTime(String wfContentContentTime) {
        this.wfContentContentTime = wfContentContentTime;
    }

    public String getWfContentBookPre() {
        return wfContentBookPre;
    }

    public void setWfContentBookPre(String wfContentBookPre) {
        this.wfContentBookPre = wfContentBookPre;
    }

    public Integer getWfContentBookYear() {
        return wfContentBookYear;
    }

    public void setWfContentBookYear(Integer wfContentBookYear) {
        this.wfContentBookYear = wfContentBookYear;
    }

    public String getWfContentBookNo() {
        return wfContentBookNo;
    }

    public void setWfContentBookNo(String wfContentBookNo) {
        this.wfContentBookNo = wfContentBookNo;
    }

    public int getWfContentBookNumber() {
        return wfContentBookNumber;
    }

    public void setWfContentBookNumber(int wfContentBookNumber) {
        this.wfContentBookNumber = wfContentBookNumber;
    }

    public int getWfContentBookPoint() {
        return wfContentBookPoint;
    }

    public void setWfContentBookPoint(int wfContentBookPoint) {
        this.wfContentBookPoint = wfContentBookPoint;
    }

    public LocalDateTime getWfContentBookDate() {
        return wfContentBookDate;
    }

    public void setWfContentBookDate(LocalDateTime wfContentBookDate) {
        this.wfContentBookDate = wfContentBookDate;
    }

    public String getWfContentFrom() {
        return wfContentFrom;
    }

    public void setWfContentFrom(String wfContentFrom) {
        this.wfContentFrom = wfContentFrom;
    }

    public String getWfContentTo() {
        return wfContentTo;
    }

    public void setWfContentTo(String wfContentTo) {
        this.wfContentTo = wfContentTo;
    }

    public String getWfContentTitle() {
        return wfContentTitle;
    }

    public void setWfContentTitle(String wfContentTitle) {
        this.wfContentTitle = wfContentTitle;
    }

    public int getWfContentSpeed() {
        return wfContentSpeed;
    }

    public void setWfContentSpeed(int wfContentSpeed) {
        this.wfContentSpeed = wfContentSpeed;
    }

    public int getWfContentSecret() {
        return wfContentSecret;
    }

    public void setWfContentSecret(int wfContentSecret) {
        this.wfContentSecret = wfContentSecret;
    }

    public String getWfContentDescription() {
        return wfContentDescription;
    }

    public void setWfContentDescription(String wfContentDescription) {
        this.wfContentDescription = wfContentDescription;
    }

    public String getWfContentReference() {
        return wfContentReference;
    }

    public void setWfContentReference(String wfContentReference) {
        this.wfContentReference = wfContentReference;
    }

    public String getWfContentAttachment() {
        return wfContentAttachment;
    }

    public void setWfContentAttachment(String wfContentAttachment) {
        this.wfContentAttachment = wfContentAttachment;
    }

    public Integer getWfContentContentYear() {
        return wfContentContentYear;
    }

    public void setWfContentContentYear(Integer wfContentContentYear) {
        this.wfContentContentYear = wfContentContentYear;
    }

    public LocalDateTime getWfContentExpireDate() {
        return wfContentExpireDate;
    }

    public void setWfContentExpireDate(LocalDateTime wfContentExpireDate) {
        this.wfContentExpireDate = wfContentExpireDate;
    }

    public String getWfContentOwnername() {
        return wfContentOwnername;
    }

    public void setWfContentOwnername(String wfContentOwnername) {
        this.wfContentOwnername = wfContentOwnername;
    }

    public int getInboxId() {
        return inboxId;
    }

    public void setInboxId(int inboxId) {
        this.inboxId = inboxId;
    }

    public String getWfContentStr01() {
        return wfContentStr01;
    }

    public void setWfContentStr01(String wfContentStr01) {
        this.wfContentStr01 = wfContentStr01;
    }

    public String getWfContentStr02() {
        return wfContentStr02;
    }

    public void setWfContentStr02(String wfContentStr02) {
        this.wfContentStr02 = wfContentStr02;
    }

    public String getWfContentStr03() {
        return wfContentStr03;
    }

    public void setWfContentStr03(String wfContentStr03) {
        this.wfContentStr03 = wfContentStr03;
    }

    public String getWfContentStr04() {
        return wfContentStr04;
    }

    public void setWfContentStr04(String wfContentStr04) {
        this.wfContentStr04 = wfContentStr04;
    }

    public String getWfContentStr05() {
        return wfContentStr05;
    }

    public void setWfContentStr05(String wfContentStr05) {
        this.wfContentStr05 = wfContentStr05;
    }

    public String getWfContentStr06() {
        return wfContentStr06;
    }

    public void setWfContentStr06(String wfContentStr06) {
        this.wfContentStr06 = wfContentStr06;
    }

    public String getWfContentStr07() {
        return wfContentStr07;
    }

    public void setWfContentStr07(String wfContentStr07) {
        this.wfContentStr07 = wfContentStr07;
    }

    public String getWfContentStr08() {
        return wfContentStr08;
    }

    public void setWfContentStr08(String wfContentStr08) {
        this.wfContentStr08 = wfContentStr08;
    }

    public String getWfContentStr09() {
        return wfContentStr09;
    }

    public void setWfContentStr09(String wfContentStr09) {
        this.wfContentStr09 = wfContentStr09;
    }

    public String getWfContentStr10() {
        return wfContentStr10;
    }

    public void setWfContentStr10(String wfContentStr10) {
        this.wfContentStr10 = wfContentStr10;
    }

    public String getWfContentText01() {
        return wfContentText01;
    }

    public void setWfContentText01(String wfContentText01) {
        this.wfContentText01 = wfContentText01;
    }

    public String getWfContentText02() {
        return wfContentText02;
    }

    public void setWfContentText02(String wfContentText02) {
        this.wfContentText02 = wfContentText02;
    }

    public String getWfContentText03() {
        return wfContentText03;
    }

    public void setWfContentText03(String wfContentText03) {
        this.wfContentText03 = wfContentText03;
    }

    public String getWfContentText04() {
        return wfContentText04;
    }

    public void setWfContentText04(String wfContentText04) {
        this.wfContentText04 = wfContentText04;
    }

    public String getWfContentText05() {
        return wfContentText05;
    }

    public void setWfContentText05(String wfContentText05) {
        this.wfContentText05 = wfContentText05;
    }

    public String getWfContentText06() {
        return wfContentText06;
    }

    public void setWfContentText06(String wfContentText06) {
        this.wfContentText06 = wfContentText06;
    }

    public String getWfContentText07() {
        return wfContentText07;
    }

    public void setWfContentText07(String wfContentText07) {
        this.wfContentText07 = wfContentText07;
    }

    public String getWfContentText08() {
        return wfContentText08;
    }

    public void setWfContentText08(String wfContentText08) {
        this.wfContentText08 = wfContentText08;
    }

    public String getWfContentText09() {
        return wfContentText09;
    }

    public void setWfContentText09(String wfContentText09) {
        this.wfContentText09 = wfContentText09;
    }

    public String getWfContentText10() {
        return wfContentText10;
    }

    public void setWfContentText10(String wfContentText10) {
        this.wfContentText10 = wfContentText10;
    }

    public Integer getWfContentInt01() {
        return wfContentInt01;
    }

    public void setWfContentInt01(Integer wfContentInt01) {
        this.wfContentInt01 = wfContentInt01;
    }

    public Integer getWfContentInt02() {
        return wfContentInt02;
    }

    public void setWfContentInt02(Integer wfContentInt02) {
        this.wfContentInt02 = wfContentInt02;
    }

    public Integer getWfContentInt03() {
        return wfContentInt03;
    }

    public void setWfContentInt03(Integer wfContentInt03) {
        this.wfContentInt03 = wfContentInt03;
    }

    public Integer getWfContentInt04() {
        return wfContentInt04;
    }

    public void setWfContentInt04(Integer wfContentInt04) {
        this.wfContentInt04 = wfContentInt04;
    }

    public Integer getWfContentInt05() {
        return wfContentInt05;
    }

    public void setWfContentInt05(Integer wfContentInt05) {
        this.wfContentInt05 = wfContentInt05;
    }

    public Integer getWfContentInt06() {
        return wfContentInt06;
    }

    public void setWfContentInt06(Integer wfContentInt06) {
        this.wfContentInt06 = wfContentInt06;
    }

    public Integer getWfContentInt07() {
        return wfContentInt07;
    }

    public void setWfContentInt07(Integer wfContentInt07) {
        this.wfContentInt07 = wfContentInt07;
    }

    public Integer getWfContentInt08() {
        return wfContentInt08;
    }

    public void setWfContentInt08(Integer wfContentInt08) {
        this.wfContentInt08 = wfContentInt08;
    }

    public Integer getWfContentInt09() {
        return wfContentInt09;
    }

    public void setWfContentInt09(Integer wfContentInt09) {
        this.wfContentInt09 = wfContentInt09;
    }

    public Integer getWfContentInt10() {
        return wfContentInt10;
    }

    public void setWfContentInt10(Integer wfContentInt10) {
        this.wfContentInt10 = wfContentInt10;
    }

    public LocalDateTime getWfContentDate01() {
        return wfContentDate01;
    }

    public void setWfContentDate01(LocalDateTime wfContentDate01) {
        this.wfContentDate01 = wfContentDate01;
    }

    public LocalDateTime getWfContentDate02() {
        return wfContentDate02;
    }

    public void setWfContentDate02(LocalDateTime wfContentDate02) {
        this.wfContentDate02 = wfContentDate02;
    }

    public LocalDateTime getWfContentDate03() {
        return wfContentDate03;
    }

    public void setWfContentDate03(LocalDateTime wfContentDate03) {
        this.wfContentDate03 = wfContentDate03;
    }

    public LocalDateTime getWfContentDate04() {
        return wfContentDate04;
    }

    public void setWfContentDate04(LocalDateTime wfContentDate04) {
        this.wfContentDate04 = wfContentDate04;
    }

    public LocalDateTime getWfContentDate05() {
        return wfContentDate05;
    }

    public void setWfContentDate05(LocalDateTime wfContentDate05) {
        this.wfContentDate05 = wfContentDate05;
    }

    public LocalDateTime getWfContentDate06() {
        return wfContentDate06;
    }

    public void setWfContentDate06(LocalDateTime wfContentDate06) {
        this.wfContentDate06 = wfContentDate06;
    }

    public LocalDateTime getWfContentDate07() {
        return wfContentDate07;
    }

    public void setWfContentDate07(LocalDateTime wfContentDate07) {
        this.wfContentDate07 = wfContentDate07;
    }

    public LocalDateTime getWfContentDate08() {
        return wfContentDate08;
    }

    public void setWfContentDate08(LocalDateTime wfContentDate08) {
        this.wfContentDate08 = wfContentDate08;
    }

    public LocalDateTime getWfContentDate09() {
        return wfContentDate09;
    }

    public void setWfContentDate09(LocalDateTime wfContentDate09) {
        this.wfContentDate09 = wfContentDate09;
    }

    public LocalDateTime getWfContentDate10() {
        return wfContentDate10;
    }

    public void setWfContentDate10(LocalDateTime wfContentDate10) {
        this.wfContentDate10 = wfContentDate10;
    }

    public Integer getfTransMainId() {
        return fTransMainId;
    }

    public void setfTransMainId(Integer fTransMainId) {
        this.fTransMainId = fTransMainId;
    }

    public Integer getfOrgId() {
        return fOrgId;
    }

    public void setfOrgId(Integer fOrgId) {
        this.fOrgId = fOrgId;
    }

    public Integer getfType() {
        return fType;
    }

    public void setfType(Integer fType) {
        this.fType = fType;
    }

    public Integer getConvertId() {
        return convertId;
    }

    public void setConvertId(Integer convertId) {
        this.convertId = convertId;
    }
    
}
