package com.px.admin.entity;

import com.px.share.entity.BaseEntity;
import java.time.LocalDateTime;
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
 * @author ken
 */
@Entity
@Table(name = "PC_USER")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "USER_ID"))
})
public class User extends BaseEntity{
    @Transient
    private static final long serialVersionUID = -6934324977721060255L;
    
    @Column(name="USER_NAME", nullable = false,length = 255)
    private String userName;
    
    @Column(name="USER_PASSWORD", nullable = false,length = 255)
    private String userPassword;
    
    @Column(name="USER_ACTIVE_DATE", nullable = true)
    private LocalDateTime userActiveDate;
    
    @Column(name="USER_EXPIRE_DATE", nullable = true)
    private LocalDateTime userExpireDate;
    
    @Column(name="USER_PASSWORD_EXPIRE_DATE", nullable = true)
    private LocalDateTime userPasswordExpireDate;
    
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="USER_STATUS_ID", nullable = true)
    private UserStatus userStatus;
    
//    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name="USER_TYPE_ID", nullable = true)
//    private UserType userType;

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public LocalDateTime getUserActiveDate() {
        return userActiveDate;
    }

    public void setUserActiveDate(LocalDateTime userActiveDate) {
        this.userActiveDate = userActiveDate;
    }

    public LocalDateTime getUserExpireDate() {
        return userExpireDate;
    }

    public void setUserExpireDate(LocalDateTime userExpireDate) {
        this.userExpireDate = userExpireDate;
    }

    public LocalDateTime getUserPasswordExpireDate() {
        return userPasswordExpireDate;
    }

    public void setUserPasswordExpireDate(LocalDateTime userPasswordExpireDate) {
        this.userPasswordExpireDate = userPasswordExpireDate;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

//    public UserType getUserType() {
//        return userType;
//    }
//
//    public void setUserType(UserType userType) {
//        this.userType = userType;
//    }

    
    
}
