package com.px.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PRAXiS
 */
@XmlRootElement(name = "VUserProfile")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "view ประวัติผู้ใช้งานระบบ" )
public class VUserProfileModel extends VersionModel{
    
    private static final long serialVersionUID = -5411770887101814632L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสผู้ใช้งานระบบ", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "structure")
//    @ApiParam(name = "structure", value = "หน่วยงาน", required = true)
    @Expose
    @Since(1.0)
    private StructureModel structure;
    
    @XmlElement(name = "title")
//    @ApiParam(name = "title", value = "คำนำหน้า", required = true)
    @Expose
    @Since(1.0)
    private TitleModel title;
    
    @XmlElement(name = "firstName")
//    @ApiParam(name = "firstName", example = "ทดสอบชื่อผู้ใช้งานระบบ", value = "ชื่อผู้ใช้งานระบบ", defaultValue = "ทดสอบชื่อผู้ใช้งานระบบ", required = true)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String firstName;
    
    @XmlElement(name = "lastName")
//    @ApiParam(name = "lastName", example = "ทดสอบนามสกุลผู้ใช้งานระบบ", value = "นามสกุลผู้ใช้งานระบบ", defaultValue = "ทดสอบนามสกุลผู้ใช้งานระบบ", required = false)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String lastName;
    
    @XmlElement(name = "fullName")
//    @ApiParam(name = "fullName", example = "ทดสอบชื่อ-นามสกุลผู้ใช้งานระบบ", value = "ชื่อ-นามสกุลผู้ใช้งานระบบ", defaultValue = "ทดสอบชื่อ-นามสกุลผู้ใช้งานระบบ", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String fullName;
    
    @XmlElement(name = "email")
//    @ApiParam(name = "email", example = "test@email.com", value = "email", defaultValue = "test@email.com", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String email;
    
    @XmlElement(name = "userProfileType")
//    @ApiParam(name = "userProfileType", value = "ประเภทของผู้ใช้งาน", required = true)
    @Expose
    @Since(1.0)
    private UserProfileTypeModel userProfileType;
    
    @XmlElement(name = "tel")
//    @ApiParam(name = "tel", example = "0123456789", value = "โทรศัพท์", defaultValue = "", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String tel;
    
    @XmlElement(name = "firstNameEng")
//    @ApiParam(name = "firstNameEng", example = "usertest", value = "ชื่อผู้ใช้งานระบบ ภาษาอังกฤษ", defaultValue = "usertest", required = false)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String firstNameEng;
    
    @XmlElement(name = "lastNameEng")
//    @ApiParam(name = "lastNameEng", example = "usertest", value = "นามสกุลผู้ใช้งานระบบ ภาษาอังกฤษ", defaultValue = "usertest", required = false)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String lastNameEng;
    
    @XmlElement(name = "fullNameEng")
//    @ApiParam(name = "fullNameEng", example = "test fullname", value = "ชื่อ-นามสกุลผู้ใช้งานระบบ ภาษาอังกฤษ", defaultValue = "test fullname", required = false)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String fullNameEng;
    
//    @XmlElement(name = "userTypeOrder")
//    @ApiParam(name = "userTypeOrder", value = "ประเภทของผู้ใช้งานระบบสำหรับจัดลำดับ", required = true)
//    @Expose
//    @Since(1.1)
//    private UserTypeOrderModel userTypeOrder;
    
    @XmlElement(name = "idCard")
//    @ApiParam(name = "idCard", example = "1234567890123", value = "เลขที่บัตรประชาชน", required = false)
    @Size(min = 13, max = 13)
    @Expose 
    @Since(1.0)
    private String idCard;
    
    @XmlElement(name = "code")
//    @ApiParam(name = "code", example = "000000000000aaa", value = "รหัสอ้างอิงผู้ใช้งานระบบ", required = false)
    @Size(max = 15)
    @Expose 
    @Since(1.0)
    private String code;
    
    @XmlElement(name = "address")
//    @ApiParam(name = "address", example = "", value = "ที่อยู่", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String address;
    
    @XmlElement(name = "position")
//    @ApiParam(name = "position", value = "ตำแหน่ง", required = false)
    @Expose
    @Since(1.0)
    private PositionModel position;
    
    @XmlElement(name = "positionType")
//    @ApiParam(name = "positionType", value = "ประเภทตำแหน่ง", required = false)
    @Expose
    @Since(1.1)
    private PositionTypeModel positionType;
    
    @XmlElement(name = "positionLevel")
//    @ApiParam(name = "positionLevel", example = "0", value = "ระดับ", defaultValue = "0", required = false)
    @Size(max = 3)
    @Expose
    @Since(1.0)
    private int positionLevel;
    
//    @XmlElement(name = "user")
//    @ApiParam(name = "user", value = "ผู้เข้าใช้งานระบบ", required = true)
//    @Expose
//    @Since(1.1)
//    private UserModel user;
    
    @XmlElement(name = "userStatus")
//    @ApiParam(name = "userStatus", value = "สถานะผู้ใช้งานระบบ", required = false)
    @Expose 
    @Since(1.0)
    private UserStatusModel userStatus;

    public VUserProfileModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StructureModel getStructure() {
        return structure;
    }

    public void setStructure(StructureModel structure) {
        this.structure = structure;
    }

    public TitleModel getTitle() {
        return title;
    }

    public void setTitle(TitleModel title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserProfileTypeModel getUserProfileType() {
        return userProfileType;
    }

    public void setUserProfileType(UserProfileTypeModel userProfileType) {
        this.userProfileType = userProfileType;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    public void setFirstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    public void setLastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
    }

    public String getFullNameEng() {
        return fullNameEng;
    }

    public void setFullNameEng(String fullNameEng) {
        this.fullNameEng = fullNameEng;
    }

//    public UserTypeOrderModel getUserTypeOrder() {
//        return userTypeOrder;
//    }
//
//    public void setUserTypeOrder(UserTypeOrderModel userTypeOrder) {
//        this.userTypeOrder = userTypeOrder;
//    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PositionModel getPosition() {
        return position;
    }

    public void setPosition(PositionModel position) {
        this.position = position;
    }

    public PositionTypeModel getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionTypeModel positionType) {
        this.positionType = positionType;
    }

    public int getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(int positionLevel) {
        this.positionLevel = positionLevel;
    }

//    public UserModel getUser() {
//        return user;
//    }
//
//    public void setUser(UserModel user) {
//        this.user = user;
//    }

    public UserStatusModel getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatusModel userStatus) {
        this.userStatus = userStatus;
    }
    
}
