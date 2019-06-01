package com.px.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OPAS
 */
@XmlRootElement(name = "UserProfile")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ประวัติผู้ใช้งานระบบ")
public class UserProfileModel extends VersionModel {

    private static final long serialVersionUID = -6025156709868344139L;

    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสผู้ใช้งานระบบ", defaultValue = "0", required = false)
    @Expose
    @Since(1.0)
    private int id;

    @XmlElement(name = "structure")
    @ApiParam(name = "structure", value = "หน่วยงาน", required = true)
    @Expose
    @Since(1.0)
    private StructureModel structure;

    @XmlElement(name = "title")
    @ApiParam(name = "title", value = "คำนำหน้า", required = true)
    @Expose
    @Since(1.0)
    private TitleModel title;

    @XmlElement(name = "firstName")
    @ApiParam(name = "firstName", example = "ทดสอบชื่อผู้ใช้งานระบบ", value = "ชื่อผู้ใช้งานระบบ", defaultValue = "ทดสอบชื่อผู้ใช้งานระบบ", required = true)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String firstName;

    @XmlElement(name = "lastName")
    @ApiParam(name = "lastName", example = "ทดสอบนามสกุลผู้ใช้งานระบบ", value = "นามสกุลผู้ใช้งานระบบ", defaultValue = "ทดสอบนามสกุลผู้ใช้งานระบบ", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String lastName;

    @XmlElement(name = "fullName")
    @ApiParam(name = "fullName", example = "ทดสอบชื่อ-นามสกุลผู้ใช้งานระบบ", value = "ชื่อ-นามสกุลผู้ใช้งานระบบ", defaultValue = "ทดสอบชื่อ-นามสกุลผู้ใช้งานระบบ", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String fullName;

    @XmlElement(name = "email")
    @ApiParam(name = "email", example = "test@email.com", value = "email", defaultValue = "test@email.com", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String email;

    @XmlElement(name = "userProfileType")
    @ApiParam(name = "userProfileType", value = "ประเภทของผู้ใช้งาน", required = true)
    @Expose
    @Since(1.0)
    private UserProfileTypeModel userProfileType;

    @XmlElement(name = "tel")
    @ApiParam(name = "tel", example = "0123456789", value = "โทรศัพท์", defaultValue = "", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String tel;

    @XmlElement(name = "defaultSelect")
    @ApiParam(name = "defaultSelect", example = "0", value = "ค่าปกติการใช้งาน", defaultValue = "0", required = false)
    @Size(max = 1)
    @Expose
    @Since(1.1)
    private int defaultSelect;

    @XmlElement(name = "firstNameEng")
    @ApiParam(name = "firstNameEng", example = "usertest", value = "ชื่อผู้ใช้งานระบบ ภาษาอังกฤษ", defaultValue = "usertest", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String firstNameEng;

    @XmlElement(name = "lastNameEng")
    @ApiParam(name = "lastNameEng", example = "usertest", value = "นามสกุลผู้ใช้งานระบบ ภาษาอังกฤษ", defaultValue = "usertest", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String lastNameEng;

    @XmlElement(name = "fullNameEng")
    @ApiParam(name = "fullNameEng", example = "test fullname", value = "ชื่อ-นามสกุลผู้ใช้งานระบบ ภาษาอังกฤษ", defaultValue = "test fullname", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String fullNameEng;

    @XmlElement(name = "userTypeOrder")
    @ApiParam(name = "userTypeOrder", value = "ประเภทของผู้ใช้งานระบบสำหรับจัดลำดับ", required = true)
    @Expose
    @Since(1.1)
    private UserTypeOrderModel userTypeOrder;

    @XmlElement(name = "idCard")
    @ApiParam(name = "idCard", example = "1234567890123", value = "เลขที่บัตรประชาชน", required = false)
    @Size(min = 13, max = 13)
    @Expose
    @Since(1.0)
    private String idCard;

    @XmlElement(name = "code")
    @ApiParam(name = "code", example = "000000000000aaa", value = "รหัสอ้างอิงผู้ใช้งานระบบ", required = false)
    @Size(max = 15)
    @Expose
    @Since(1.0)
    private String code;

    @XmlElement(name = "address")
    @ApiParam(name = "address", example = "", value = "ที่อยู่", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String address;

    @XmlElement(name = "position")
    @ApiParam(name = "position", value = "ตำแหน่ง", required = false)
    @Expose
    @Since(1.0)
    private PositionModel position;

    @XmlElement(name = "positionType")
    @ApiParam(name = "positionType", value = "ประเภทตำแหน่ง", required = false)
    @Expose
    @Since(1.1)
    private PositionTypeModel positionType;

    @XmlElement(name = "positionLevel")
    @ApiParam(name = "positionLevel", example = "0", value = "ระดับ", defaultValue = "0", required = false)
    @Size(max = 2)
    @Expose
    @Since(1.0)
    private int positionLevel;

    @XmlElement(name = "digitalKey")
    @ApiParam(name = "digitalKey", example = "000000000000aaa", value = "ลายเซ็นต์อิเล็กทรอนิค", defaultValue = "", required = false)
    @Size(max = 255)
    @Expose
    @Since(1.2)
    @DefaultValue("")
    private String digitalKey;

    @XmlElement(name = "user")
    @ApiParam(name = "user", value = "ผู้เข้าใช้งานระบบ", required = true)
    @Expose
    @Since(1.1)
    private UserModel user;

    @XmlElement(name = "userStatus")
    @ApiParam(name = "userStatus", value = "สถานะผู้ใช้งานระบบ", required = false)
    @Expose
    @Since(1.0)
    private UserStatusModel userStatus;

    //oat-add
    @XmlElement(name = "recentPassword")
    @ApiParam(name = "recentPassword", example = "", value = "recentPassword", defaultValue = "", required = false)
    @Expose
    private String recentPassword;

    public UserProfileModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสประวัติผู้ใช้งาน", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public StructureModel getStructure() {
        return structure;
    }

    @ApiModelProperty(name = "structure", value = "หน่วยงาน", required = true)
    public void setStructure(StructureModel structure) {
        this.structure = structure;
    }

    public TitleModel getTitle() {
        return title;
    }

    @ApiModelProperty(name = "title", value = "คำนำหน้า", required = true)
    public void setTitle(TitleModel title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    @ApiModelProperty(name = "firstName", example = "ทดสอบชื่อผู้ใช้งานระบบ", dataType = "string", value = "ชื่อผู้ใช้งานระบบ", required = true)
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @ApiModelProperty(name = "lastName", example = "ทดสอบนามสกุลผู้ใช้งานระบบ", dataType = "string", value = "นามสกุลผู้ใช้งานระบบ", required = false)
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    @ApiModelProperty(name = "fullName", example = "ทดสอบชื่อ-นามสกุลผู้ใช้งานระบบ", dataType = "string", value = "ชื่อ-นามสกุลผู้ใช้งานระบบ", required = false)
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    @ApiModelProperty(name = "email", example = "test@email.com", dataType = "string", value = "email", required = false)
    public void setEmail(String email) {
        this.email = email;
    }

    public UserProfileTypeModel getUserProfileType() {
        return userProfileType;
    }

    @ApiModelProperty(name = "userProfileType", value = "ประเภทของผู้ใช้งาน", required = true)
    public void setUserProfileType(UserProfileTypeModel userProfileType) {
        this.userProfileType = userProfileType;
    }

    public String getTel() {
        return tel;
    }

    @ApiModelProperty(name = "tel", example = "0123456789", dataType = "string", value = "โทรศัพท์", required = false)
    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getDefaultSelect() {
        return defaultSelect;
    }

    @ApiModelProperty(name = "defaultSelect", example = "0", dataType = "int", value = "ค่าปกติการใช้งาน", required = false)
    public void setDefaultSelect(int defaultSelect) {
        this.defaultSelect = defaultSelect;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    @ApiModelProperty(name = "firstNameEng", example = "test firstNameEng", dataType = "string", value = "ชื่อผู้ใช้งานระบบ ภาษาอังกฤษ", required = false)
    public void setFirstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    @ApiModelProperty(name = "lastNameEng", example = "test lastNameEng", dataType = "string", value = "นามสกุลผู้ใช้งานระบบ ภาษาอังกฤษ", required = false)
    public void setLastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
    }

    public String getFullNameEng() {
        return fullNameEng;
    }

    @ApiModelProperty(name = "fullNameEng", example = "test fullname", dataType = "string", value = "ชื่อ-นามสกุลผู้ใช้งานระบบ ภาษาอังกฤษ", required = false)
    public void setFullNameEng(String fullNameEng) {
        this.fullNameEng = fullNameEng;
    }

    public UserTypeOrderModel getUserTypeOrder() {
        return userTypeOrder;
    }

    @ApiModelProperty(name = "userTypeOrder", value = "ประเภทของผู้ใช้งานระบบสำหรับจัดลำดับ", required = true)
    public void setUserTypeOrder(UserTypeOrderModel userTypeOrder) {
        this.userTypeOrder = userTypeOrder;
    }

    public String getIdCard() {
        return idCard;
    }

    @ApiModelProperty(name = "idCard", example = "1234567890123", dataType = "string", value = "เลขที่บัตรประชาชน", required = false)
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCode() {
        return code;
    }

    @ApiModelProperty(name = "code", example = "000000000000aaa", dataType = "string", value = "รหัสอ้างอิงผู้ใช้งานระบบ", required = false)
    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    @ApiModelProperty(name = "address", example = "", dataType = "string", value = "ที่อยู่", required = false)
    public void setAddress(String address) {
        this.address = address;
    }

    public PositionModel getPosition() {
        return position;
    }

    @ApiModelProperty(name = "position", value = "ตำแหน่ง", required = true)
    public void setPosition(PositionModel position) {
        this.position = position;
    }

    public PositionTypeModel getPositionType() {
        return positionType;
    }

    @ApiModelProperty(name = "positionType", value = "ประเภทตำแหน่ง", required = true)
    public void setPositionType(PositionTypeModel positionType) {
        this.positionType = positionType;
    }

    public int getPositionLevel() {
        return positionLevel;
    }

    @ApiModelProperty(name = "positionLevel", example = "0", dataType = "int", value = "ระดับ", required = false)
    public void setPositionLevel(int positionLevel) {
        this.positionLevel = positionLevel;
    }

    public String getDigitalKey() {
        return digitalKey;
    }

    @ApiModelProperty(name = "digitalKey", example = "000000000000aaa", dataType = "string", value = "ลายเซ็นต์อิเล็กทรอนิค", required = false)
    public void setDigitalKey(String digitalKey) {
        this.digitalKey = digitalKey;
    }

    public UserModel getUser() {
        return user;
    }

    @ApiModelProperty(name = "user", value = "ผู้เข้าใช้งานระบบ", required = true)
    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserStatusModel getUserStatus() {
        return userStatus;
    }

    @ApiModelProperty(name = "userStatus", value = "สถานะผู้ใช้งานระบบ", required = true)
    public void setUserStatus(UserStatusModel userStatus) {
        this.userStatus = userStatus;
    }

    public String getRecentPassword() {
        return recentPassword;
    }

    public void setRecentPassword(String recentPassword) {
        this.recentPassword = recentPassword;
    }

}
