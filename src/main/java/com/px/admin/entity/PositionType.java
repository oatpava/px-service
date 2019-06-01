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
@Table(name = "PC_POSITION_TYPE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "POSITION_TYPE_ID"))
})
public class PositionType extends BaseEntity {
    @Transient
    private static final long serialVersionUID = -7919263098795666641L;
    
    @Column(name = "POSITION_TYPE_NAME", nullable = false, length = 100)
    private String positionTypeName;
    
    @Column(name = "POSITION_TYPE_ABBR", nullable = false, length = 3)
    private String positionTypeAbbr;

    public PositionType() {
    }

    public PositionType(String positionTypeName, Integer createdBy) {
        super(createdBy);
        this.positionTypeName = positionTypeName;
    }

    public String getPositionTypeName() {
        return positionTypeName;
    }

    public void setPositionTypeName(String positionTypeName) {
        this.positionTypeName = positionTypeName;
    }

    public String getPositionTypeAbbr() {
        return positionTypeAbbr;
    }

    public void setPositionTypeAbbr(String positionTypeAbbr) {
        this.positionTypeAbbr = positionTypeAbbr;
    }
    
    
}
