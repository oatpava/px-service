package com.px.admin.entity;

import com.px.share.entity.BaseEntity;
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
@Table(name = "PC_POSITION")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "POSITION_ID"))
})
public class Position extends BaseEntity {
    @Transient
    private static final long serialVersionUID = 7989504712982606236L;
    
    @Column(name = "POSITION_NAME", nullable = false, length = 100)
    private String positionName;
    
    @Column(name = "POSITION_NAME_ENG", nullable = true, length = 100)
    private String positionNameEng;
    
    @Column(name = "POSITION_NAME_EXTRA", nullable = true, length = 255)
    private String positionNameExtra;
    
    public Position() {
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionNameEng() {
        return positionNameEng;
    }

    public void setPositionNameEng(String positionNameEng) {
        this.positionNameEng = positionNameEng;
    }

    public String getPositionNameExtra() {
        return positionNameExtra;
    }

    public void setPositionNameExtra(String positionNameExtra) {
        this.positionNameExtra = positionNameExtra;
    }
    
}
