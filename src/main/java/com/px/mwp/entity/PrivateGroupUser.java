/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Oat
 */
@Entity
@Table(name = "PC_PRIVATE_GROUP_USER")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ID"))
})
public class PrivateGroupUser extends BaseEntity {

    private static final long serialVersionUID = -354391911700640239L;

    @Column(name = "PRIVATE_GROUP_ID", columnDefinition = "int default '0'")
    private int privateGroupId;

    @Column(name = "USER_ID", columnDefinition = "int default '0'")
    private int userId;

    @Column(name = "USER_NAME", nullable = false, length = 1000)
    private String userName;

    @Column(name = "USER_TYPE", columnDefinition = "int default '0'")
    private int userType;

    @Column(name = "EMAIL", nullable = true, length = 1000)//only outside user(userType = 2, userId = -1)
    private String email;

    public int getPrivateGroupId() {
        return privateGroupId;
    }

    public void setPrivateGroupId(int privateGroupId) {
        this.privateGroupId = privateGroupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
