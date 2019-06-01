package com.px.share.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author OPAS
 */
@Entity
@Table(name = "PC_LOG_DATA")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ID"))
})
public class LogData extends BaseEntity{
    @Transient
    private static final long serialVersionUID = 5962819894683033922L;
    public static final String MODULE_MWP = "mwp";
    public static final String MODULE_DMS = "dms";
    public static final String MODULE_CALENDAR = "calendar";
    public static final String MODULE_LEAVE = "leave";
    public static final String MODULE_MEETING = "meeting";
    public static final String MODULE_VEHICLE = "vehicle";
    public static final String MODULE_OCHART = "ochart";
    public static final String MODULE_CARCARD = "carcard";
    public static final String MODULE_CONFERENCE = "conference";
    public static final String MODULE_HR = "hr";
    public static final String MODULE_VL = "vl";
    public static final String MODULE_TIMESTAMP = "timestamp";
    public static final String MODULE_SUPPLY = "supply";
    public static final String MODULE_SLIP = "slip";
    public static final String MODULE_ADMIN = "admin";
    public static final String MODULE_WF = "wf";
    
    @Column(name="TYPE", nullable = false)
    private int type;
    
    @Column(name="LINK_ID", nullable = false)
    private int linkId;
    
    @Column(name="MODULE_NAME", nullable = false,length = 10)
    private String moduleName;
    
    @Column(name="ENTITY_NAME", nullable = false,length = 100)
    private String entityName;
    
    @Column(name="DESCRIPTION", nullable = false,length = 4000)
    private String description;
    
    @Column(name="IP_ADDRESS", nullable = false,length = 50)
    private String ipAddress;
    
    public LogData() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
