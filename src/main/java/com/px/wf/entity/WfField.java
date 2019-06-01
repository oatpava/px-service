package com.px.wf.entity;

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
@Table(name = "PC_WF_FIELD")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WF_FIELD_ID"))
})
public class WfField extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -4568615326273957057L;

    @Column(name = "WF_FIELD_NAME", length = 255)
    private String wfFieldName;

    @Column(name = "WF_FIELD_DESCRIPTION", length = 50)
    private String wfFieldDescription;

    @Column(name = "WF_FIELD_TYPE", length = 50)
    private String wfFieldType;

    @Column(name = "WF_FIELD_LENGTH", columnDefinition = "int default '0'")
    private int wfFieldLength;

    public String getWfFieldName() {
        return wfFieldName;
    }

    public void setWfFieldName(String wfFieldName) {
        this.wfFieldName = wfFieldName;
    }

    public String getWfFieldDescription() {
        return wfFieldDescription;
    }

    public void setWfFieldDescription(String wfFieldDescription) {
        this.wfFieldDescription = wfFieldDescription;
    }

    public String getWfFieldType() {
        return wfFieldType;
    }

    public void setWfFieldType(String wfFieldType) {
        this.wfFieldType = wfFieldType;
    }

    public int getWfFieldLength() {
        return wfFieldLength;
    }

    public void setWfFieldLength(int wfFieldLength) {
        this.wfFieldLength = wfFieldLength;
    }

}
