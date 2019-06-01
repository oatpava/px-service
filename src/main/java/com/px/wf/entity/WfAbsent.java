package com.px.wf.entity;

import com.px.share.entity.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author CHALERMPOL
 */
@Entity
@Table(name = "PC_WF_ABSENT")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ABSENT_ID"))
})
public class WfAbsent extends BaseEntity {

    @Transient
    private static final long serialVersionUID = 506617124775312823L;

    @Column(name = "ABSENT_DATE", nullable = false, length = 11)
    private LocalDateTime  absentDate;

    @Column(name = "USER_ID", columnDefinition = "int default '0'")
    private int userId;

    public WfAbsent() {
    }
    
    public LocalDateTime getAbsentDate() {
        return absentDate;
    }

    public void setAbsentDate(LocalDateTime absentDate) {
        this.absentDate = absentDate;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }    
}
