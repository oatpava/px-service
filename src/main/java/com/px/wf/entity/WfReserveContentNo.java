
package com.px.wf.entity;

import com.px.share.entity.BaseEntity;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
@Entity
@Table(name = "PC_WF_RESERVE_NO")//
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WF_RESERVE_NO_ID"))//
})
public class WfReserveContentNo extends BaseEntity {

    private static final long serialVersionUID = 8098295272992957513L;

    //@Column(name = "WF_RESERVE_CONTENTNO_FOLDER_ID", columnDefinition = "int default '0'")
    @Column(name = "WF_RESERVE_FOLDER_ID", columnDefinition = "int default '0'")
    private Integer reserveContentNoFolderId;
    
    //@Column(name = "WF_RESERVE_CONTENTNO_CONTENT_NO", length = 50)
    @Column(name = "WF_RESERVE_CONTENT_NO", length = 50)
    private String reserveContentNoContentNo;
    
    //@Column(name = "WF_RESERVE_CONTENTNO_CONTENT_DATE", nullable = false)
    @Column(name = "WF_RESERVE_CONTENT_DATE", nullable = false)
    private LocalDateTime reserveContentNoContentDate;
    
    
    //@Column(name = "WF_RESERVE_CONTENTNO_CONTENT_TIME", length = 10)
    @Column(name = "WF_RESERVE_CONTENT_TIME", length = 10)
    private String reserveContentNoContentTime;
    
    //@Column(name = "WF_RESERVE_CONTENTNO_USER_ID", columnDefinition = "int default '0'")
    @Column(name = "WF_RESERVE_USER_ID", columnDefinition = "int default '0'")
    private Integer reserveContentNoUserId;
    
    //@Column(name = "WF_RESERVE_CONTENTNO_CONTENT_YEAR", columnDefinition = "int default '0'")
    @Column(name = "WF_RESERVE_CONTENT_YEAR", columnDefinition = "int default '0'")
    private Integer reserveContentNoContentYear;
    
    //@Column(name = "WF_RESERVE_CONTENTNO_CONTENT_NUMBER", columnDefinition = "int default '0'")
    @Column(name = "WF_RESERVE_CONTENT_NUMBER", columnDefinition = "int default '0'")
    private int reserveContentNoContentNumber;
    
    //@Column(name = "WF_RESERVE_CONTENTNO_NOTE", length = 255)
    @Column(name = "WF_RESERVE_NOTE", length = 255)
    private String reserveContentNoNote;
    
    //@Column(name = "WF_RESERVE_CONTENTNO_STATUS", columnDefinition = "int default '0'")
    @Column(name = "WF_RESERVE_STATUS", columnDefinition = "int default '0'")
    private int reserveContentNoStatus;
    
    public WfReserveContentNo() {
    }

    public WfReserveContentNo(Integer createdBy) {
        super(createdBy);
    }

    public Integer getReserveContentNoFolderId() {
        return reserveContentNoFolderId;
    }

    public void setReserveContentNoFolderId(Integer reserveContentNoFolderId) {
        this.reserveContentNoFolderId = reserveContentNoFolderId;
    }

    public String getReserveContentNoContentNo() {
        return reserveContentNoContentNo;
    }

    public void setReserveContentNoContentNo(String reserveContentNoContentNo) {
        this.reserveContentNoContentNo = reserveContentNoContentNo;
    }

    public LocalDateTime getReserveContentNoContentDate() {
        return reserveContentNoContentDate;
    }

    public void setReserveContentNoContentDate(LocalDateTime reserveContentNoContentDate) {
        this.reserveContentNoContentDate = reserveContentNoContentDate;
    }

    public String getReserveContentNoContentTime() {
        return reserveContentNoContentTime;
    }

    public void setReserveContentNoContentTime(String reserveContentNoContentTime) {
        this.reserveContentNoContentTime = reserveContentNoContentTime;
    }

    public Integer getReserveContentNoUserId() {
        return reserveContentNoUserId;
    }

    public void setReserveContentNoUserId(Integer reserveContentNoUserId) {
        this.reserveContentNoUserId = reserveContentNoUserId;
    }

    public Integer getReserveContentNoContentYear() {
        return reserveContentNoContentYear;
    }

    public void setReserveContentNoContentYear(Integer reserveContentNoContentYear) {
        this.reserveContentNoContentYear = reserveContentNoContentYear;
    }

    public int getReserveContentNoContentNumber() {
        return reserveContentNoContentNumber;
    }

    public void setReserveContentNoContentNumber(int reserveContentNoContentNumber) {
        this.reserveContentNoContentNumber = reserveContentNoContentNumber;
    }

    public String getReserveContentNoNote() {
        return reserveContentNoNote;
    }

    public void setReserveContentNoNote(String reserveContentNoNote) {
        this.reserveContentNoNote = reserveContentNoNote;
    }

    public int getReserveContentNoStatus() {
        return reserveContentNoStatus;
    }

    public void setReserveContentNoStatus(int reserveContentNoStatus) {
        this.reserveContentNoStatus = reserveContentNoStatus;
    }
}
