package com.px.authority.entity;

import com.px.admin.entity.Submodule;
import com.px.share.entity.BaseEntity;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Peach
 */
@Entity
@Table(name = "PC_SUBMODULE_AUTH")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="SUBMODULE_AUTH_ID"))
})
public class SubmoduleAuth extends BaseEntity {
    @Transient
    private static final long serialVersionUID = 2846051892770961719L;
    
    @Column(name="SUBMODULE_AUTH_CODE", nullable = false,length = 10)
    private String submoduleAuthCode;
    
    @ManyToOne()
    @JoinColumn(name="SUBMODULE_ID", nullable = false)
    private Submodule submodule;
    
    @ManyToOne()
    @JoinColumn(name="AUTH_ID", nullable = false)
    private Auth auth;
    
    @OneToMany(mappedBy="submoduleAuth",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    private List<SubmoduleUserAuth> submoduleUserAuth;
    
    @OneToMany(mappedBy="submoduleAuth",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    private List<SubmoduleAuthTemplateVal> submoduleAuthTemplateVal;
    
//    @OneToMany(mappedBy="submoduleAuth",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
//    private List<MenuSubmoduleAuth> menuSubmoduleAuth;

    public SubmoduleAuth() {
    }

    public String getSubmoduleAuthCode() {
        return submoduleAuthCode;
    }

    public void setSubmoduleAuthCode(String submoduleAuthCode) {
        this.submoduleAuthCode = submoduleAuthCode;
    }

    public Submodule getSubmodule() {
        return submodule;
    }

    public void setSubmodule(Submodule submodule) {
        this.submodule = submodule;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public List<SubmoduleUserAuth> getSubmoduleUserAuth() {
        return submoduleUserAuth;
    }

    public void setSubmoduleUserAuth(List<SubmoduleUserAuth> submoduleUserAuth) {
        this.submoduleUserAuth = submoduleUserAuth;
    }

    public List<SubmoduleAuthTemplateVal> getSubmoduleAuthTemplateVal() {
        return submoduleAuthTemplateVal;
    }

    public void setSubmoduleAuthTemplateVal(List<SubmoduleAuthTemplateVal> submoduleAuthTemplateVal) {
        this.submoduleAuthTemplateVal = submoduleAuthTemplateVal;
    }

   
}
