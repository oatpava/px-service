package com.px.menu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Peach
 */
@XmlRootElement(name = "MenuModel_Tree")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ลำดับขั้นของเมนู" )
public class MenuModel_Tree extends MenuModel {
    private static final long serialVersionUID = -6618785494950860005L;
    
    @XmlElement(name = "child")
    @ApiParam(name = "child", value = "รายการเมนู", required = true)
    @Expose
    @Since(1.0)
    private List<MenuModel_Tree> child;

    public MenuModel_Tree() {
    }

    public List<MenuModel_Tree> getChild() {
        return child;
    }

    @ApiModelProperty(name = "child", value = "รายการเมนู", required = true)
    public void setChild(List<MenuModel_Tree> child) {
        this.child = child;
    }
    
}
