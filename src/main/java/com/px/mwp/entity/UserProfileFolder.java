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
import javax.persistence.Transient;

/**
 *
 * @author ken
 */
@Entity
@Table(name = "PC_USER_PROFILE_FOLDER")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "USER_PROFILE_FOLDER_ID"))
})
public class UserProfileFolder extends BaseEntity{
    @Transient
    private static final long serialVersionUID = -7348131246005862790L;
    
    @Column(name="USER_PROFILE_FOLDER_NAME", length = 255)
    private String userProfileFolderName;
    
    @Column(name="USER_PROFILE_FOLDER_TYPE", length = 10)
    private String userProfileFolderType;
    
    @Column(name="USER_PROFILE_FOLDER_LINK_ID", columnDefinition="int default '0'")
    private int userProfileFolderLinkId;
    
    @Column(name="USER_PROFILE_FOLDER_DETAIL", length = 1000)
    private String userProfileFolderDetail;
    
//    @ManyToOne(cascade={CascadeType.DETACH})
//    @JoinColumn(name="USER_PROFILE_ID", nullable = false)
//    private UserProfile userProfile;
    
    @Column(name="USER_PROFILE_ID", columnDefinition="int default '0'")
    private int userProfileId;

    public UserProfileFolder() {
    }

    public UserProfileFolder(Integer createdBy) {
        super(createdBy);
    }

    public String getUserProfileFolderName() {
        return userProfileFolderName;
    }

    public void setUserProfileFolderName(String userProfileFolderName) {
        this.userProfileFolderName = userProfileFolderName;
    }

    public String getUserProfileFolderType() {
        return userProfileFolderType;
    }

    public void setUserProfileFolderType(String userProfileFolderType) {
        this.userProfileFolderType = userProfileFolderType;
    }

    public int getUserProfileFolderLinkId() {
        return userProfileFolderLinkId;
    }

    public void setUserProfileFolderLinkId(int userProfileFolderLinkId) {
        this.userProfileFolderLinkId = userProfileFolderLinkId;
    }

    public String getUserProfileFolderDetail() {
        return userProfileFolderDetail;
    }

    public void setUserProfileFolderDetail(String userProfileFolderDetail) {
        this.userProfileFolderDetail = userProfileFolderDetail;
    }

    public int getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
    }
}
