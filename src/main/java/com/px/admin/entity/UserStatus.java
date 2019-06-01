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
@Table(name = "PC_USER_STATUS")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="USER_STATUS_ID"))
})
public class UserStatus extends BaseEntity{
    @Transient
    private static final long serialVersionUID = -4811927017887356011L;
    
    @Column(name="USER_STATUS_NAME", nullable = false,length = 100)
    private String userStatusName;

    public UserStatus() {
    }

    public String getUserStatusName() {
        return userStatusName;
    }

    public void setUserStatusName(String userStatusName) {
        this.userStatusName = userStatusName;
    }
    
    
}
