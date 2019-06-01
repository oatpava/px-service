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
 * @author Pritsana
 */
@Entity
@Table(name = "PC_USER_PARAM")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="USER_PARAM_ID"))
})
public class UserParam extends BaseEntity{
    @Transient
    private static final long serialVersionUID = -269664721112481876L;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="USER_ID", nullable = false)
    private User user;
    
    @Column(name="PARAM_NAME", nullable = false,length = 100)
    private String paramName = "";   
    
    @Column(name="PARAM_VALUE", nullable = false,length = 100)
    private String paramValue = "";

    public UserParam() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
    
}
