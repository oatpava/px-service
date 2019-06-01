package com.px.admin.entity;

import com.px.share.entity.BaseEntity;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author OPAS
 */
@Entity
@Table(name = "PC_HOLIDAY")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "HOLIDAY_ID"))
})
public class Holiday extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -1074541252096326875L;

    @Column(name = "HOLIDAY_DATE", nullable = false, length = 11)
    private LocalDateTime  holidayDate;

    @Column(name = "HOLIDAY_NAME", nullable = false, length = 255)
    private String holidayName;

    public Holiday() {
    }
    
    public LocalDateTime getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(LocalDateTime holidayDate) {
        this.holidayDate = holidayDate;
    }
    
    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }    
}
