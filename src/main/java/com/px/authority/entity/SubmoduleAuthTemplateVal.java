package com.px.authority.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Peach
 */
@Entity
@Table(name = "PC_SUBMODULE_AUTH_TEMPLATE_VAL")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="SUBMODULE_AUTH_TEMPLATE_VAL_ID"))
})
public class SubmoduleAuthTemplateVal extends BaseEntity{
    @Transient
    private static final long serialVersionUID = -2772206559104267399L;
    
    @ManyToOne()
    @JoinColumn(name="SUBMODULE_AUTH_TEMPLATE_ID", nullable = false)
    private SubmoduleAuthTemplate submoduleAuthTemplate;
    
    @ManyToOne()
    @JoinColumn(name="SUBMODULE_AUTH_ID", nullable = false)
    private SubmoduleAuth submoduleAuth;
    
    @Column(name="LINK_ID", nullable = true)
    private Integer linkId;
    
    @Column(name="AUTHORITY", nullable = false,length = 10)
    private String authority;

    public SubmoduleAuthTemplateVal() {
    }

    public SubmoduleAuthTemplate getSubmoduleAuthTemplate() {
        return submoduleAuthTemplate;
    }

    public void setSubmoduleAuthTemplate(SubmoduleAuthTemplate submoduleAuthTemplate) {
        this.submoduleAuthTemplate = submoduleAuthTemplate;
    }

    public SubmoduleAuth getSubmoduleAuth() {
        return submoduleAuth;
    }

    public void setSubmoduleAuth(SubmoduleAuth submoduleAuth) {
        this.submoduleAuth = submoduleAuth;
    }

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    
}
