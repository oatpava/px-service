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
 * @author OPAS
 */
@XmlRootElement(name = "ModuleConfig")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "รายละเอียดการตั้งค่า Module" )
public class ModuleConfigModel extends VersionModel{    
    private static final long serialVersionUID = 6204563692473430046L;
    
    @XmlElement(name = "id")
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "moduleConfigName")
    @ApiParam(name = "moduleConfigName",value = "ชื่อ config",required = true)
    @Size(max = 100)
    @Expose 
    @Since(1.0)
    private String moduleConfigName;
    
    @XmlElement(name = "moduleConfigNameEng")
    @ApiParam(name = "moduleConfigNameEng",value = "ชื่อ config ภาษาอังกฤษ",required = true)
    @Size(max = 100)
    @Expose 
    @Since(1.0)
    private String moduleConfigNameEng;
    
    @XmlElement(name = "moduleConfigShow")
    @ApiParam(name = "moduleConfigShow",value = "แสดง config 0 แสดง , 1 ซ่อน",required = true)
    private int moduleConfigShow;
    
    @XmlElement(name = "moduleConfigIcon")
    @ApiParam(name = "moduleConfigIcon",value = "ชื่อ icon",required = true)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String moduleConfigIcon;
    
    @XmlElement(name = "moduleConfigFunction")
    @ApiParam(name = "moduleConfigFunction",value = "ชื่อ function",required = false)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String moduleConfigFunction;
    
    @XmlElement(name = "moduleId")
    @ApiParam(name = "moduleId",value = "รหัส Module",required = true)
    private int moduleId;

    public ModuleConfigModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    
    
    
}
