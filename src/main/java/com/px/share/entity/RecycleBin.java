package com.px.share.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author OPAS
 */
@Entity
@Table(name = "PC_RECYCLE_BIN")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ID"))
})
public class RecycleBin extends BaseEntity{
    private static final long serialVersionUID = -8854434461919823309L;
    
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

    public RecycleBin() {
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
