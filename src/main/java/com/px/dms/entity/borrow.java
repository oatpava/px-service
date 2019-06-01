/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.entity;

import com.px.share.entity.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author top
 */
@Entity
@Table(name = "PC_BORROW")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "BORROW_ID"))
})
public class borrow extends BaseEntity {

    @Transient
    public static final long serialVersionUID = 0L;

    public borrow() {
    }

    @ManyToOne()
    @JoinColumn(name = "DMS_DOCUMENT_ID", nullable = false)
    private DmsDocument dmsDocument;

    @Column(name = "LEND_USER_ID", nullable = true, columnDefinition = "int default '0'")
    private int userLentId;

    @Column(name = "RETURN_USER_ID", nullable = true, columnDefinition = "int default '0'")
    private int userReturnId;

    @Column(name = "HENDLER_USER_ID", nullable = true, columnDefinition = "int default '0'")
    private int userHandlerId;

    @Column(name = "NUM_LEND_DAY", nullable = true, columnDefinition = "int default '0'")
    private int numLentDay;

    @Column(name = "LENT_DATE")
    private LocalDateTime lendDate;

    @Column(name = "RETURN_DATE")
    private LocalDateTime retuenDate;
    
    @Column(name = "RETURN_NAME", nullable = true, length = 255)
    private String returnName;
    
    @Column(name = "LENT_NAME", nullable = true, length = 255)
    private String lendName;

    public DmsDocument getDmsDocument() {
        return dmsDocument;
    }

    public void setDmsDocument(DmsDocument dmsDocument) {
        this.dmsDocument = dmsDocument;
    }

    public int getUserLentId() {
        return userLentId;
    }

    public void setUserLentId(int userLentId) {
        this.userLentId = userLentId;
    }

    public int getUserReturnId() {
        return userReturnId;
    }

    public void setUserReturnId(int userReturnId) {
        this.userReturnId = userReturnId;
    }

    public int getNumLentDay() {
        return numLentDay;
    }

    public void setNumLentDay(int numLentDay) {
        this.numLentDay = numLentDay;
    }

    public LocalDateTime getLendDate() {
        return lendDate;
    }

    public void setLendDate(LocalDateTime lendDate) {
        this.lendDate = lendDate;
    }

    public LocalDateTime getRetuenDate() {
        return retuenDate;
    }

    public void setRetuenDate(LocalDateTime retuenDate) {
        this.retuenDate = retuenDate;
    }

    public int getUserHandlerId() {
        return userHandlerId;
    }

    public void setUserHandlerId(int userHandlerId) {
        this.userHandlerId = userHandlerId;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public String getLendName() {
        return lendName;
    }

    public void setLendName(String lendName) {
        this.lendName = lendName;
    }
    
    

}
