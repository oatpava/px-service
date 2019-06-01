package com.px.menu.entity;

import com.px.admin.entity.Module;
import com.px.share.entity.BaseEntity;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Peach
 */
@Entity
@Table(name = "PC_MENU_TYPE")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="MENU_TYPE_ID"))
})
public class MenuType extends BaseEntity{
    @Transient
    private static final long serialVersionUID = -6977952352570991731L;
    
    @Column(name="MENU_TYPE_CODE", nullable = false,length = 50)
    private String menuTypeCode;
    
    @Column(name="MENU_TYPE_NAME", nullable = false,length = 255)
    private String menuTypeName;
    
    @ManyToOne()
    @JoinColumn(name="MODULE_ID", nullable = false)
    private Module module;
    
    @OneToMany(mappedBy="menuType",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    private List<Menu> menu;

    public MenuType() {
    }

    public String getMenuTypeCode() {
        return menuTypeCode;
    }

    public void setMenuTypeCode(String menuTypeCode) {
        this.menuTypeCode = menuTypeCode;
    }

    public String getMenuTypeName() {
        return menuTypeName;
    }

    public void setMenuTypeName(String menuTypeName) {
        this.menuTypeName = menuTypeName;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }
}
