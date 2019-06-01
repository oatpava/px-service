package com.px.admin.entity;

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
@Table(name = "PC_AD")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="AD_ID"))
})
public class Ad extends BaseEntity {
    @Transient
    private static final long serialVersionUID = -7876389972784063039L;
    
    @Column(name="AD_NAME", nullable = false,length = 100)
    private String adName;
    
    @Column(name="AD_HOST", nullable = false,length = 50)
    private String adHost;
    
    @Column(name="AD_PORT", nullable = false,length = 5)
    private String adPort;
    
    @Column(name="AD_BASE", nullable = false,length = 100)
    private String adBase;
    
    @Column(name="AD_USER", nullable = false,length = 50)
    private String adUser;
    
    @Column(name="AD_PASSWORD", nullable = false,length = 50)
    private String adPassword;
    
    @Column(name="AD_TYPE", nullable = false,length = 5)
    private String adType;
    
    @Column(name="AD_PREFIX", nullable = true,length = 20)
    private String adPrefix;
    
    @Column(name="AD_SUFFIX", nullable = true,length = 20)
    private String adSuffix;
    
    @Column(name="AD_ATTRIBUTE", nullable = true,length = 20)
    private String adAttribute;

    public Ad() {
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdHost() {
        return adHost;
    }

    public void setAdHost(String adHost) {
        this.adHost = adHost;
    }

    public String getAdPort() {
        return adPort;
    }

    public void setAdPort(String adPort) {
        this.adPort = adPort;
    }

    public String getAdBase() {
        return adBase;
    }

    public void setAdBase(String adBase) {
        this.adBase = adBase;
    }

    public String getAdUser() {
        return adUser;
    }

    public void setAdUser(String adUser) {
        this.adUser = adUser;
    }

    public String getAdPassword() {
        return adPassword;
    }

    public void setAdPassword(String adPassword) {
        this.adPassword = adPassword;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getAdPrefix() {
        return adPrefix;
    }

    public void setAdPrefix(String adPrefix) {
        this.adPrefix = adPrefix;
    }

    public String getAdSuffix() {
        return adSuffix;
    }

    public void setAdSuffix(String adSuffix) {
        this.adSuffix = adSuffix;
    }

    public String getAdAttribute() {
        return adAttribute;
    }

    public void setAdAttribute(String adAttribute) {
        this.adAttribute = adAttribute;
    }
    
    
}
