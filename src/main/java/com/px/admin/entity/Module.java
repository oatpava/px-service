package com.px.admin.entity;

import com.px.authority.entity.ModuleUserAuth;
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
@Table(name = "PC_MODULE")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="MODULE_ID"))
})
public class Module extends BaseEntity{
    @Transient
    private static final long serialVersionUID = -2456449498448725441L;
    
    @Column(name="MODULE_CODE", nullable = true,length = 10)
    private String moduleCode;
    
    @Column(name="MODULE_NAME", nullable = false,length = 255)
    private String moduleName;
    
    @Column(name="MODULE_NAME_ENG", nullable = false,length = 100)
    private String moduleNameEng;
    
    @Column(name="MODULE_ICON", nullable = false,length = 100)
    private String moduleIcon;
    
    @Column(name="MODULE_CONFIG_IN_ADMIN", nullable = false,length = 1)
    private int moduleConfigInAdmin;
    
    @OneToMany(mappedBy="module",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    private List<Submodule> submodule;
    
    @OneToMany(mappedBy="module",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    private List<ModuleUserAuth> moduleUserAuth;
    
    public Module() {
    }

    public Module(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleNameEng() {
        return moduleNameEng;
    }

    public void setModuleNameEng(String moduleNameEng) {
        this.moduleNameEng = moduleNameEng;
    }

    public String getModuleIcon() {
        return moduleIcon;
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon;
    }

    public int getModuleConfigInAdmin() {
        return moduleConfigInAdmin;
    }

    public void setModuleConfigInAdmin(int moduleConfigInAdmin) {
        this.moduleConfigInAdmin = moduleConfigInAdmin;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<Submodule> getSubmodule() {
        return submodule;
    }

    public void setSubmodule(List<Submodule> submodule) {
        this.submodule = submodule;
    }

    public List<ModuleUserAuth> getModuleUserAuth() {
        return moduleUserAuth;
    }

    public void setModuleUserAuth(List<ModuleUserAuth> moduleUserAuth) {
        this.moduleUserAuth = moduleUserAuth;
    }

}
