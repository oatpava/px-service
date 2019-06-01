package com.px.admin.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author OPAS
 */
@Entity
@Table(name = "PC_LOOKUP_DETAIL")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="LOOKUP_DETAIL_ID"))
})
public class LookupDetail extends BaseEntity {
    private static final long serialVersionUID = -8032501133743774358L;
    
    @Column(name="LOOKUP_DETAIL_NAME", nullable = false,length = 255)
    private String lookupDetailName;
    
    @ManyToOne(cascade={CascadeType.DETACH})
    @JoinColumn(name="LOOKUP_ID", nullable = false)
    private Lookup lookup;

    public LookupDetail() {
    }

    public LookupDetail(String lookupDetailName, Lookup lookup, Integer createdBy) {
        super(createdBy);
        this.lookupDetailName = lookupDetailName;
        this.lookup = lookup;
    }

    public String getLookupDetailName() {
        return lookupDetailName;
    }

    public void setLookupDetailName(String lookupDetailName) {
        this.lookupDetailName = lookupDetailName;
    }

    public Lookup getLookup() {
        return lookup;
    }

    public void setLookup(Lookup lookup) {
        this.lookup = lookup;
    }
    
    
}
