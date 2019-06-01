
package com.px.mwp.entity;

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
@Table(name = "PC_INOUT_ASSIGN")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "INOUT_ASSIGN_ID"))
})
public class InOutAssign extends BaseEntity {
    
    public static final long serialVersionUID = 0L;

    @Column(name = "INOUT_ASSIGN_OWNER_ID", columnDefinition = "int default '0'")
    private int inOutAssignOwnerId;
    
    @Column(name = "INOUT_ASSIGN_ASSIGN_ID", columnDefinition = "int default '0'")
    private int inOutAssignAssignId;
    
    @Column(name = "INOUT_ASSIGN_OWNER_TYPE", columnDefinition = "int default '0'")
    private int inOutAssignOwnerType;
    
    @Column(name = "INOUT_ASSIGN_ISPERIOD", columnDefinition = "int default '0'")
    private int inOutAssignIsperiod;
    
    @Column(name = "INOUT_ASSIGN_START_DATE", nullable = true)
    private LocalDateTime inOutAssignStartDate;
    
    @Column(name = "INOUT_ASSIGN_END_DATE", nullable = true)
    private LocalDateTime inOutAssignEndDate;

    public int getInOutAssignOwnerId() {
        return inOutAssignOwnerId;
    }

    public void setInOutAssignOwnerId(int inOutAssignOwnerId) {
        this.inOutAssignOwnerId = inOutAssignOwnerId;
    }

    public int getInOutAssignAssignId() {
        return inOutAssignAssignId;
    }

    public void setInOutAssignAssignId(int inOutAssignAssignId) {
        this.inOutAssignAssignId = inOutAssignAssignId;
    }

    public int getInOutAssignOwnerType() {
        return inOutAssignOwnerType;
    }

    public void setInOutAssignOwnerType(int inOutAssignOwnerType) {
        this.inOutAssignOwnerType = inOutAssignOwnerType;
    }

    public int getInOutAssignIsperiod() {
        return inOutAssignIsperiod;
    }

    public void setInOutAssignIsperiod(int inOutAssignIsperiod) {
        this.inOutAssignIsperiod = inOutAssignIsperiod;
    }

    public LocalDateTime getInOutAssignStartDate() {
        return inOutAssignStartDate;
    }

    public void setInOutAssignStartDate(LocalDateTime inOutAssignStartDate) {
        this.inOutAssignStartDate = inOutAssignStartDate;
    }

    public LocalDateTime getInOutAssignEndDate() {
        return inOutAssignEndDate;
    }

    public void setInOutAssignEndDate(LocalDateTime inOutAssignEndDate) {
        this.inOutAssignEndDate = inOutAssignEndDate;
    }
    
}
