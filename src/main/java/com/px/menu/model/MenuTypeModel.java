package com.px.menu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.admin.model.ModuleModel;
import com.px.share.model.VersionModel;
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
@XmlRootElement(name = "MenuType")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ประเภทเมนู" )
public class MenuTypeModel extends VersionModel{
    private static final long serialVersionUID = 7874447630157400297L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสประเภทเมนู", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "menuTypeCode")
    @ApiParam(name = "menuTypeCode", example = "รหัสเรียกประเภทเมนู", value = "รหัสเรียกประเภทเมนู", defaultValue = "รหัสเรียกประเภทเมนู", required = true)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String menuTypeCode;
    
    @XmlElement(name = "menuTypeName")
    @ApiParam(name = "menuTypeName", example = "ชื่อประเภทเมนู", value = "ชื่อประเภทเมนู", defaultValue = "ชื่อประเภทเมนู", required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String menuTypeName;
    
    @XmlElement(name = "module")
    @ApiParam(name = "module", value = "ระบบงาน", required = true)
    @Expose
    @Since(1.0)
    private ModuleModel module;

    public MenuTypeModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสประเภทเมนู", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getMenuTypeCode() {
        return menuTypeCode;
    }

    @ApiModelProperty(name = "menuTypeCode", example = "รหัสเรียกประเภทเมนู", dataType = "string", value = "รหัสเรียกประเภทเมนู", required = true)
    public void setMenuTypeCode(String menuTypeCode) {
        this.menuTypeCode = menuTypeCode;
    }

    public String getMenuTypeName() {
        return menuTypeName;
    }

    @ApiModelProperty(name = "menuTypeName", example = "ชื่อประเภทเมนู", dataType = "string", value = "ชื่อประเภทเมนู", required = true)
    public void setMenuTypeName(String menuTypeName) {
        this.menuTypeName = menuTypeName;
    }

    public ModuleModel getModule() {
        return module;
    }

    @ApiModelProperty(name = "module", value = "ระบบงาน", required = true)
    public void setModule(ModuleModel module) {
        this.module = module;
    }

}
