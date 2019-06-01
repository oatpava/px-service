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
import javax.persistence.Transient;

/**
 *
 * @author OPAS
 */
@Entity
@Table(name = "PC_MODULE_CONFIG")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "MODULE_CONFIG_ID"))
})
public class ModuleConfig extends BaseEntity{
    @Transient
    private static final long serialVersionUID = -8378970714678312812L;
    
    @Column(name="MODULE_CONFIG_NAME", nullable = false,length = 100)
    private String moduleConfigName;
    
    @Column(name="MODULE_CONFIG_NAME_ENG", nullable = false,length = 20)
    private String moduleConfigNameEng;
    
    @Column(name="MODULE_CONFIG_SHOW", nullable = false, columnDefinition="int default '0'")
    private int moduleConfigShow;
    
    @Column(name="MODULE_CONFIG_ICON", nullable = false,length = 100)
    private String moduleConfigIcon;
    
    @Column(name="MODULE_CONFIG_FUNCTION", nullable = true,length = 100)
    private String moduleConfigFunction;
        
    @ManyToOne(cascade={CascadeType.DETACH})
    @JoinColumn(name="MODULE_ID", nullable = false)
    private Module module;

    public ModuleConfig() {
        this.moduleConfigShow = 1;
    }

    public String getModuleConfigName() {
        return moduleConfigName;
    }

    public void setModuleConfigName(String moduleConfigName) {
        this.moduleConfigName = moduleConfigName;
    }

    public String getModuleConfigNameEng() {
        return moduleConfigNameEng;
    }

    public void setModuleConfigNameEng(String moduleConfigNameEng) {
        this.moduleConfigNameEng = moduleConfigNameEng;
    }

    public int getModuleConfigShow() {
        return moduleConfigShow;
    }

    public void setModuleConfigShow(int moduleConfigShow) {
        this.moduleConfigShow = moduleConfigShow;
    }

    public String getModuleConfigIcon() {
        return moduleConfigIcon;
    }

    public void setModuleConfigIcon(String moduleConfigIcon) {
        this.moduleConfigIcon = moduleConfigIcon;
    }

    public String getModuleConfigFunction() {
        return moduleConfigFunction;
    }

    public void setModuleConfigFunction(String moduleConfigFunction) {
        this.moduleConfigFunction = moduleConfigFunction;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
    
}
