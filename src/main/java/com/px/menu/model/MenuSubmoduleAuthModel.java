package com.px.menu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.authority.model.SubmoduleAuthModel;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Peach
 */
@XmlRootElement(name = "MenuSubmoduleAuth")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "การควบคุมเมนูโดยสิทธิการใช้งาน" )
public class MenuSubmoduleAuthModel extends VersionModel{
    private static final long serialVersionUID = 3708308234285670382L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสการควบคุมเมนูโดยสิทธิการใช้งาน", defaultValue = "0", required = false )
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "menu")
    @ApiParam(name = "menu", value = "เมนู", required = true)
    @Expose
    @Since(1.0)
    private MenuModel menu;
    
    @XmlElement(name = "submoduleAuth")
    @ApiParam(name = "submoduleAuth", value = "สิทธิการใช้ระบบงานย่อย", required = true)
    @Expose
    @Since(1.0)
    private SubmoduleAuthModel submoduleAuth;

    public MenuSubmoduleAuthModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสการควบคุมเมนูโดยสิทธิการใช้งาน", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public MenuModel getMenu() {
        return menu;
    }

    @ApiModelProperty(name = "menu", value = "เมนู", required = true)
    public void setMenu(MenuModel menu) {
        this.menu = menu;
    }

    public SubmoduleAuthModel getSubmoduleAuth() {
        return submoduleAuth;
    }

    @ApiModelProperty(name = "submoduleAuth", value = "สิทธิการใช้ระบบงานย่อย", required = true)
    public void setSubmoduleAuth(SubmoduleAuthModel submoduleAuth) {
        this.submoduleAuth = submoduleAuth;
    }

}
