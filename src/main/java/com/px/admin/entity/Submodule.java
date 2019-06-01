package com.px.admin.entity;

import com.px.authority.entity.SubmoduleAuthTemplate;
import com.px.authority.entity.SubmoduleAuth;
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
@Table(name = "PC_SUBMODULE")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="SUBMODULE_ID"))
})
public class Submodule extends BaseEntity{
    @Transient
    private static final long serialVersionUID = -7667384109590917058L;
    
    @Column(name="SUBMODULE_CODE", nullable = false,length = 10)
    private String submoduleCode;
    
    @Column(name="SUBMODULE_NAME", nullable = false,length = 255)
    private String submoduleName;
    
    @ManyToOne()
    @JoinColumn(name="MODULE_ID", nullable = false)
    private Module module;
    
    @OneToMany(mappedBy="submodule",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    private List<SubmoduleAuth> submoduleAuth;
    
    @OneToMany(mappedBy="submodule",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    private List<SubmoduleAuthTemplate> submoduleAuthTemplate;

    public Submodule() {
    }

    public String getSubmoduleCode() {
        return submoduleCode;
    }

    public void setSubmoduleCode(String submoduleCode) {
        this.submoduleCode = submoduleCode;
    }

    public String getSubmoduleName() {
        return submoduleName;
    }

    public void setSubmoduleName(String submoduleName) {
        this.submoduleName = submoduleName;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<SubmoduleAuth> getSubmoduleAuth() {
        return submoduleAuth;
    }

    public void setSubmoduleAuth(List<SubmoduleAuth> submoduleAuth) {
        this.submoduleAuth = submoduleAuth;
    }

    public List<SubmoduleAuthTemplate> getSubmoduleAuthTemplate() {
        return submoduleAuthTemplate;
    }

    public void setSubmoduleAuthTemplate(List<SubmoduleAuthTemplate> submoduleAuthTemplate) {
        this.submoduleAuthTemplate = submoduleAuthTemplate;
    }
    
    
}
