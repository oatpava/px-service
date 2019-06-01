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
 * @author PRAXiS
 */
@Entity
@Table(name = "PC_V_USER_PROFILE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "V_USER_PROFILE_ID"))
})
public class VUserProfile extends BaseEntity{
    
    @Transient
    private static final long serialVersionUID = 8186858026463393955L;
    
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="USER_ID", nullable = false)
//    private User user;
    
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="STRUCTURE_ID", nullable = false)
    private Structure structure;
    
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="TITLE_ID", nullable = false)
    private Title title;
    
    @Column(name="USER_PROFILE_FIRST_NAME", nullable = true,length = 255)
    private String userProfileFirstName;
    
    @Column(name="USER_PROFILE_LAST_NAME", nullable = true,length = 255)
    private String userProfileLastName;
    
    @Column(name="USER_PROFILE_FULL_NAME", nullable = true,length = 255)
    private String userProfileFullName;
    
    @Column(name="USER_PROFILE_FIRST_NAME_ENG", nullable = true,length = 255)
    private String userProfileFirstNameEng;
    
    @Column(name="USER_PROFILE_LAST_NAME_ENG", nullable = true,length = 255)
    private String userProfileLastNameEng;
    
    @Column(name="USER_PROFILE_FULL_NAME_ENG", nullable = true,length = 255)
    private String userProfileFullNameEng;
    
    @Column(name="USER_PROFILE_EMAIL", nullable = true,length = 50)
    private String userProfileEmail;
    
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="USER_PROFILE_TYPE", nullable = true)
    private UserProfileType userProfileType;
    
//    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name="USER_PROFILE_TYPE_ORDER", nullable = true)
//    private UserTypeOrder userProfileTypeOrder;
    
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="USER_PROFILE_STATUS", nullable = true)
    private UserStatus userProfileStatus;    
    
    @Column(name="USER_PROFILE_VERSION", nullable = true, columnDefinition="int default '0'")
    private int userProfileVersion = 0;
    
    @Column(name="USER_PROFILE_TEL", nullable = true,length = 50)
    private String userProfileTel;
    
    @Column(name="USER_PROFILE_DEFAULT_SELECT", nullable = true, columnDefinition="int default '0'")
    private int userProfileDefaultSelect = 0;  
        
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="POSITION_ID", nullable = true)
    private Position position;
    
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="POSITION_TYPE_ID", nullable = true)
    private PositionType positionType;    
    
    @Column(name="POSITION_LEVEL", nullable = true, columnDefinition="int default '0'")
    private int positionLevel = 0; 
    
    @Column(name="USER_PROFILE_CARD_ID", nullable = true,length = 13)
    private String userProfileCardId;
    
    @Column(name="USER_PROFILE_CODE", nullable = true,length = 15)
    private String userProfileCode;
    
    @Column(name="USER_PROFILE_ADDRESS", nullable = true,length = 255)
    private String userProfileAddress;
    
    public VUserProfile() {
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getUserProfileFirstName() {
        return userProfileFirstName;
    }

    public void setUserProfileFirstName(String userProfileFirstName) {
        this.userProfileFirstName = userProfileFirstName;
    }

    public String getUserProfileLastName() {
        return userProfileLastName;
    }

    public void setUserProfileLastName(String userProfileLastName) {
        this.userProfileLastName = userProfileLastName;
    }

    public String getUserProfileFullName() {
        return userProfileFullName;
    }

    public void setUserProfileFullName(String userProfileFullName) {
        this.userProfileFullName = userProfileFullName;
    }

    public String getUserProfileFirstNameEng() {
        return userProfileFirstNameEng;
    }

    public void setUserProfileFirstNameEng(String userProfileFirstNameEng) {
        this.userProfileFirstNameEng = userProfileFirstNameEng;
    }

    public String getUserProfileLastNameEng() {
        return userProfileLastNameEng;
    }

    public void setUserProfileLastNameEng(String userProfileLastNameEng) {
        this.userProfileLastNameEng = userProfileLastNameEng;
    }

    public String getUserProfileFullNameEng() {
        return userProfileFullNameEng;
    }

    public void setUserProfileFullNameEng(String userProfileFullNameEng) {
        this.userProfileFullNameEng = userProfileFullNameEng;
    }

    public String getUserProfileEmail() {
        return userProfileEmail;
    }

    public void setUserProfileEmail(String userProfileEmail) {
        this.userProfileEmail = userProfileEmail;
    }

    public UserProfileType getUserProfileType() {
        return userProfileType;
    }

    public void setUserProfileType(UserProfileType userProfileType) {
        this.userProfileType = userProfileType;
    }

//    public UserTypeOrder getUserProfileTypeOrder() {
//        return userProfileTypeOrder;
//    }
//
//    public void setUserProfileTypeOrder(UserTypeOrder userProfileTypeOrder) {
//        this.userProfileTypeOrder = userProfileTypeOrder;
//    }

    public UserStatus getUserProfileStatus() {
        return userProfileStatus;
    }

    public void setUserProfileStatus(UserStatus userProfileStatus) {
        this.userProfileStatus = userProfileStatus;
    }

    public int getUserProfileVersion() {
        return userProfileVersion;
    }

    public void setUserProfileVersion(int userProfileVersion) {
        this.userProfileVersion = userProfileVersion;
    }

    public String getUserProfileTel() {
        return userProfileTel;
    }

    public void setUserProfileTel(String userProfileTel) {
        this.userProfileTel = userProfileTel;
    }

    public int getUserProfileDefaultSelect() {
        return userProfileDefaultSelect;
    }

    public void setUserProfileDefaultSelect(int userProfileDefaultSelect) {
        this.userProfileDefaultSelect = userProfileDefaultSelect;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public int getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(int positionLevel) {
        this.positionLevel = positionLevel;
    }

    public String getUserProfileCardId() {
        return userProfileCardId;
    }

    public void setUserProfileCardId(String userProfileCardId) {
        this.userProfileCardId = userProfileCardId;
    }

    public String getUserProfileCode() {
        return userProfileCode;
    }

    public void setUserProfileCode(String userProfileCode) {
        this.userProfileCode = userProfileCode;
    }

    public String getUserProfileAddress() {
        return userProfileAddress;
    }

    public void setUserProfileAddress(String userProfileAddress) {
        this.userProfileAddress = userProfileAddress;
    }
 
}
