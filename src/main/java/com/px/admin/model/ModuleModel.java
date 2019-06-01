package com.px.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Peach
 */
@XmlRootElement(name = "Module")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ระบบงาน" )
public class ModuleModel extends VersionModel{
    private static final long serialVersionUID = -2479978541038185589L;
    
    @Since(1.0)
    @XmlElement(name = "id")
    @Expose 
    private int id;
    
    @Since(1.0)
    @XmlElement(name = "moduleCode")
    @ApiParam(name = "moduleCode",value = "รหัสเรียกระบบงาน",required = true)
    @Size(max = 10)
    @Expose 
    private String moduleCode;
    
    @Since(1.0)
    @XmlElement(name = "moduleName")
    @ApiParam(name = "moduleName",value = "ชื่อระบบงาน",required = true)
    @Size(max = 255)
    @Expose 
    private String moduleName;
    
    @Since(1.0)
    @XmlElement(name = "moduleNameEng")
    @ApiParam(name = "moduleNameEng",value = "ชื่อระบบงานภาษาอังกฤษ",required = true)
    @Size(max = 10)
    @Expose 
    private String moduleNameEng;
    
    @Since(1.0)
    @XmlElement(name = "moduleIcon")
    @ApiParam(name = "moduleIcon",value = "icon แสดงระบบงาน",required = true)
    @Size(max = 100)
    @Expose 
    private String moduleIcon;
    
    @Since(1.0)
    @XmlElement(name = "moduleConfigInAdmin")
    @ApiParam(name = "moduleConfigInAdmin",value = "มี config ที่ admin",required = false)
    private int moduleConfigInAdmin;

    public ModuleModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    
    
}
