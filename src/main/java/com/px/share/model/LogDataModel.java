package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OPAS
 */
@XmlRootElement(name = "LogData")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ประวัติการใช้งานระบบ" )
public class LogDataModel extends VersionModel{
    
    private static final long serialVersionUID = 2461330180212327432L;
    @Since(1.0)
    @XmlElement(name = "id")
    @Expose 
    private int id;
    @Since(1.0)
    @XmlElement(name = "createdDate")
    @Expose 
    private String createdDate;
    @Since(1.0)
    @XmlElement(name = "moduleName")
    @Expose
    private String moduleName;
    @Since(1.0)
    @XmlElement(name = "moduleIcon")
    @Expose
    private String moduleIcon;
    @Since(1.0)
    @XmlElement(name = "entityName")
    @Expose
    private String entityName;
    @Since(1.0)
    @XmlElement(name = "description")
    @Expose
    private String description;
    @Since(1.0)
    @XmlElement(name = "userProfileName")
    @Expose
    private String userProfileName;
    @Since(1.0)
    @XmlElement(name = "ipAddress")
    @Expose
    private String ipAddress;


    public LogDataModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleIcon() {
        return moduleIcon;
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserProfileName() {
        return userProfileName;
    }

    public void setUserProfileName(String userProfileName) {
        this.userProfileName = userProfileName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    
    
    
}
