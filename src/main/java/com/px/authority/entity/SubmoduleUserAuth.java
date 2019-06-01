package com.px.authority.entity;

import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
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
@Table(name = "PC_SUBMODULE_USER_AUTH")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="SUBMODULE_USER_AUTH_ID"))
})
public class SubmoduleUserAuth extends BaseEntity {
    @Transient
    private static final long serialVersionUID = -4343033553424502220L;
    
    @ManyToOne()
    @JoinColumn(name="STRUCTURE_ID", nullable = true)
    private Structure structure;
    
    @ManyToOne()
    @JoinColumn(name="USER_PROFILE_ID", nullable = true)
    private UserProfile userProfile;
    
    @ManyToOne()
    @JoinColumn(name="SUBMODULE_AUTH_ID", nullable = false)
    private SubmoduleAuth submoduleAuth;
    
    @Column(name="LINK_ID", nullable = true)
    private Integer linkId;
    
    @Column(name="AUTHORITY", nullable = false,length = 10)
    private String authority;

    public SubmoduleUserAuth() {
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
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
