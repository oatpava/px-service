package com.px.menu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.BaseTreeModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Peach
 */
@XmlRootElement(name = "Menu")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "เมนู" )
public class MenuModel extends BaseTreeModel{
    private static final long serialVersionUID = 8705691948394928937L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสเมนู", defaultValue = "0", required = false )
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "menuCode")
    @ApiParam(name = "menuCode", example = "รหัสเรียกเมนู", value = "รหัสเรียกเมนู", defaultValue = "รหัสเรียกเมนู", required = true)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String menuCode;
    
    @XmlElement(name = "menuName")
    @ApiParam(name = "menuName", example = "ชื่อเมนู", value = "ชื่อเมนู", defaultValue = "ชื่อเมนู", required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String menuName;
    
    @XmlElement(name = "menuEngName")
    @ApiParam(name = "menuEngName", example = "ชื่อเมนูภาษาอังกฤษ", value = "ชื่อเมนูภาษาอังกฤษ", defaultValue = "ชื่อเมนูภาษาอังกฤษ", required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String menuEngName;
    
    @XmlElement(name = "menuPicture")
    @ApiParam(name = "menuPicture", example = "ภาพเมนู", value = "ภาพเมนู", defaultValue = "ภาพเมนู", required = true)
    @Size(max = 100)
    @Expose 
    @Since(1.0)
    private String menuPicture;
    
    @XmlElement(name = "menuDetail")
    @ApiParam(name = "menuDetail", example = "รายละเอียดเมนู", value = "รายละเอียดเมนู", defaultValue = "รายละเอียดเมนู", required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String menuDetail;
    
    @XmlElement(name = "menuFunction")
    @ApiParam(name = "menuFunction", example = "ชื่อฟังก์ชั่นเมื่อถูกเรียกใช้งาน", value = "ชื่อฟังก์ชั่นเมื่อถูกเรียกใช้งาน", defaultValue = "ชื่อฟังก์ชั่นเมื่อถูกเรียกใช้งาน", required = true)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String menuFunction;
    
    @XmlElement(name = "menuDisableShow")
    @ApiParam(name = "menuDisableShow", example = "สถานะการแสดงเมื่อไม่มีสิทธิ", value = "สถานะการแสดงเมื่อไม่มีสิทธิ", defaultValue = "สถานะการแสดงเมื่อไม่มีสิทธิ", required = true)
    @Size(max = 10)
    @Expose 
    @Since(1.0)
    private String menuDisableShow;
    
    @XmlElement(name = "menuDisableCSS")
    @ApiParam(name = "menuDisableCSS", example = "รูปแบบการแสดงเมื่อไม่มีสิทธิ", value = "รูปแบบการแสดงเมื่อไม่มีสิทธิ", defaultValue = "รูปแบบการแสดงเมื่อไม่มีสิทธิ", required = false)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String menuDisableCSS;
    
    @XmlElement(name = "menuType")
    @ApiParam(name = "menuType", value = "ประเภทเมนู", required = true)
    @Expose
    @Since(1.0)
    private MenuTypeModel menuType;

    public MenuModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสเมนู", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    @ApiModelProperty(name = "menuCode", example = "รหัสเรียกเมนู", dataType = "string", value = "รหัสเรียกเมนู", required = true)
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    @ApiModelProperty(name = "menuName", example = "ชื่อเมนู", dataType = "string", value = "ชื่อเมนู", required = true)
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuEngName() {
        return menuEngName;
    }

    @ApiModelProperty(name = "menuEngName", example = "ชื่อเมนูภาษาอังกฤษ", dataType = "string", value = "ชื่อเมนูภาษาอังกฤษ", required = true)
    public void setMenuEngName(String menuEngName) {
        this.menuEngName = menuEngName;
    }

    public String getMenuPicture() {
        return menuPicture;
    }

    @ApiModelProperty(name = "menuPicture", example = "ภาพเมนู", dataType = "string", value = "ภาพเมนู", required = true)
    public void setMenuPicture(String menuPicture) {
        this.menuPicture = menuPicture;
    }

    public String getMenuDetail() {
        return menuDetail;
    }

    @ApiModelProperty(name = "menuDetail", example = "รายละเอียดเมนู", dataType = "string", value = "รายละเอียดเมนู", required = true)
    public void setMenuDetail(String menuDetail) {
        this.menuDetail = menuDetail;
    }

    public String getMenuFunction() {
        return menuFunction;
    }

    @ApiModelProperty(name = "menuFunction", example = "ชื่อฟังก์ชั่นเมื่อถูกเรียกใช้งาน", dataType = "string", value = "ชื่อฟังก์ชั่นเมื่อถูกเรียกใช้งาน", required = true)
    public void setMenuFunction(String menuFunction) {
        this.menuFunction = menuFunction;
    }

    public String getMenuDisableShow() {
        return menuDisableShow;
    }

    @ApiModelProperty(name = "menuDisableShow", example = "สถานะการแสดงเมื่อไม่มีสิทธิ", dataType = "string", value = "สถานะการแสดงเมื่อไม่มีสิทธิ", required = true)
    public void setMenuDisableShow(String menuDisableShow) {
        this.menuDisableShow = menuDisableShow;
    }

    public String getMenuDisableCSS() {
        return menuDisableCSS;
    }

    @ApiModelProperty(name = "menuDisableCSS", example = "รูปแบบการแสดงเมื่อไม่มีสิทธิ", dataType = "string", value = "รูปแบบการแสดงเมื่อไม่มีสิทธิ", required = false)
    public void setMenuDisableCSS(String menuDisableCSS) {
        this.menuDisableCSS = menuDisableCSS;
    }

    public MenuTypeModel getMenuType() {
        return menuType;
    }

    @ApiModelProperty(name = "menuType", value = "ประเภทเมนู", required = true)
    public void setMenuType(MenuTypeModel menuType) {
        this.menuType = menuType;
    }
    
    
    
}
