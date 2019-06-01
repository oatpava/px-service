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
@Table(name = "PC_USER_PROFILE_TYPE")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="USER_PROFILE_TYPE_ID"))
})
public class UserProfileType extends BaseEntity{
    @Transient
    private static final long serialVersionUID = 7610294441873854961L;
    
    @Column(name="USER_PROFILE_TYPE_NAME", nullable = false,length = 100)
    private String userProfileTypeName;

    public UserProfileType() {
    }

    public UserProfileType(String userProfileTypeName, Integer createdBy) {
        super(createdBy);
        this.userProfileTypeName = userProfileTypeName;
    }

    public String getUserProfileTypeName() {
        return userProfileTypeName;
    }

    public void setUserProfileTypeName(String userProfileTypeName) {
        this.userProfileTypeName = userProfileTypeName;
    }
    
    
    
}
