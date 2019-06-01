package com.px.authority.entity;

import com.px.share.entity.BaseEntity;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Peach
 */
@Entity
@Table(name = "PC_AUTH")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="AUTH_ID"))
})
public class Auth extends BaseEntity{
    @Transient
    private static final long serialVersionUID = -8823232135071266808L;
    
    @Column(name="AUTH_NAME", nullable = false,length = 255)
    private String authName;
    
    @OneToMany(mappedBy="auth",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    private List<SubmoduleAuth> submoduleAuth;
    
    public Auth() {
    }

    public Auth(String authName) {
        this.authName = authName;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public List<SubmoduleAuth> getSubmoduleAuth() {
        return submoduleAuth;
    }

    public void setSubmoduleAuth(List<SubmoduleAuth> submoduleAuth) {
        this.submoduleAuth = submoduleAuth;
    }

}
