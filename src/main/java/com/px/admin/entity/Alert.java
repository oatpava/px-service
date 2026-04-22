package com.px.admin.entity;

import com.px.share.entity.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Oat
 */
@Entity
@Table(name = "PC_ALERT")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ALERT_ID"))
})
public class Alert extends BaseEntity {

    @Column(name = "START_DATE", nullable = false, length = 11)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false, length = 11)
    private LocalDateTime endDate;

    @Column(name = "MESSAGE", nullable = false, length = 2000)
    private String message;

    @Column(name = "ACTIVE", nullable = false, length = 1)
    private String active;

    public Alert() {
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    
}
