package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OPAS
 */
@XmlRootElement(name = "LogType")
@ApiModel( description = "รายการประเภท log ต่างๆ " )
public class LogTypeModel extends VersionModel{
    
    private static final long serialVersionUID = -7915046483905612504L;
    @Since(1.0)
    @XmlElement(name = "module")
    @Expose
    private String module;
    @Since(1.0)
    @XmlElement(name = "moduleName")
    @Expose
    private String moduleName;
    @Since(1.0)
    @XmlElement(name = "subModule")
    @Expose
    private String subModule;

    public void setModule(String module) {
        this.module = module;
    }

    public void setSubModule(String subModule) {
        this.subModule = subModule;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    
    
}
