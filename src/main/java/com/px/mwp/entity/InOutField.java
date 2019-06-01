
package com.px.mwp.entity;

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
@Table(name = "PC_INOUT_FIELD")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "INOUT_FIELD_ID"))
})
public class InOutField extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -4568615326273957057L;

    @Column(name = "INOUT_FIELD_NAME", length = 255)
    private String inoutFieldName;

    @Column(name = "INOUT_FIELD_DESCRIPTION", length = 50)
    private String inoutFieldDescription;

    @Column(name = "INOUT_FIELD_TYPE", length = 50)
    private String inoutFieldType;

    @Column(name = "INOUT_FIELD_LENGTH", columnDefinition = "int default '0'")
    private int inoutFieldLength;

    public String getInoutFieldName() {
        return inoutFieldName;
    }

    public void setInoutFieldName(String inoutFieldName) {
        this.inoutFieldName = inoutFieldName;
    }

    public String getInoutFieldDescription() {
        return inoutFieldDescription;
    }

    public void setInoutFieldDescription(String inoutFieldDescription) {
        this.inoutFieldDescription = inoutFieldDescription;
    }

    public String getInoutFieldType() {
        return inoutFieldType;
    }

    public void setInoutFieldType(String inoutFieldType) {
        this.inoutFieldType = inoutFieldType;
    }

    public int getInoutFieldLength() {
        return inoutFieldLength;
    }

    public void setInoutFieldLength(int inoutFieldLength) {
        this.inoutFieldLength = inoutFieldLength;
    }
    
}
