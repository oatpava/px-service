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
@Table(name = "PC_LOOKUP")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="LOOKUP_ID"))
})
public class Lookup extends BaseEntity {
    @Transient
    private static final long serialVersionUID = -7404462196957096064L;
    
    @Column(name="LOOKUP_NAME", nullable = false,length = 100)
    private String lookupName;

    public Lookup() {
    }

    public Lookup(String lookupName, Integer createdBy) {
        super(createdBy);
        this.lookupName = lookupName;
    }

    public String getLookupName() {
        return lookupName;
    }

    public void setLookupName(String lookupName) {
        this.lookupName = lookupName;
    }

}
