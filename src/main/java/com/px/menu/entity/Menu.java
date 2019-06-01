package com.px.menu.entity;

import com.px.share.entity.BaseTreeEntity;
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
@Table(name = "PC_MENU")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="MENU_ID"))
})
public class Menu extends BaseTreeEntity{
    @Transient
    private static final long serialVersionUID = 6025534545379892702L;
    
    @Column(name="MENU_CODE", nullable = false,length = 50)
    private String menuCode;
    
    @Column(name="MENU_NAME", nullable = false,length = 255)
    private String menuName;
    
    @Column(name="MENU_ENG_NAME", nullable = false,length = 255)
    private String menuEngName;
    
    @Column(name="MENU_PICTURE", nullable = false,length = 100)
    private String menuPicture;
    
    @Column(name="MENU_DETAIL", nullable = false,length = 255)
    private String menuDetail;
    
    @Column(name="MENU_FUNCTION", nullable = false,length = 50)
    private String menuFunction;
    
    @Column(name="MENU_DISABLE_SHOW", nullable = false,length = 10)
    private String menuDisableShow;
    
    @Column(name="MENU_DISABLE_CSS", nullable = true,length = 50)
    private String menuDisableCSS;
    
    @ManyToOne()
    @JoinColumn(name="MENU_TYPE_ID", nullable = false)
    private MenuType menuType;
    
    @OneToMany(mappedBy="menu",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    private List<MenuSubmoduleAuth> menuSubmoduleAuth;

    public Menu() {
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuEngName() {
        return menuEngName;
    }

    public void setMenuEngName(String menuEngName) {
        this.menuEngName = menuEngName;
    }

    public String getMenuPicture() {
        return menuPicture;
    }

    public void setMenuPicture(String menuPicture) {
        this.menuPicture = menuPicture;
    }

    public String getMenuDetail() {
        return menuDetail;
    }

    public void setMenuDetail(String menuDetail) {
        this.menuDetail = menuDetail;
    }

    public String getMenuFunction() {
        return menuFunction;
    }

    public void setMenuFunction(String menuFunction) {
        this.menuFunction = menuFunction;
    }

    public String getMenuDisableShow() {
        return menuDisableShow;
    }

    public void setMenuDisableShow(String menuDisableShow) {
        this.menuDisableShow = menuDisableShow;
    }

    public String getMenuDisableCSS() {
        return menuDisableCSS;
    }

    public void setMenuDisableCSS(String menuDisableCSS) {
        this.menuDisableCSS = menuDisableCSS;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }

    public List<MenuSubmoduleAuth> getMenuSubmoduleAuth() {
        return menuSubmoduleAuth;
    }

    public void setMenuSubmoduleAuth(List<MenuSubmoduleAuth> menuSubmoduleAuth) {
        this.menuSubmoduleAuth = menuSubmoduleAuth;
    }
}
