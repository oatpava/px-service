package com.px.authority.entity;

import com.px.admin.entity.Module;
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
@Table(name = "PC_MODULE_USER_AUTH")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="MODULE_USER_AUTH_ID"))
})
public class ModuleUserAuth extends BaseEntity {
    @Transient
    private static final long serialVersionUID = -2192087922742464189L;
    
    @ManyToOne()
    @JoinColumn(name="STRUCTURE_ID", nullable = true)
    private Structure structure;
    
    @ManyToOne()
    @JoinColumn(name="USER_PROFILE_ID", nullable = true)
    private UserProfile userProfile;
    
    @ManyToOne()
    @JoinColumn(name="MODULE_ID", nullable = false)
    private Module module;
    
    @Column(name="AUTHORITY", nullable = false,length = 10)
    private String authority;

    public ModuleUserAuth() {
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
