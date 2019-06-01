package com.px.menu.entity;

import com.px.authority.entity.SubmoduleAuth;
import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Peach
 */
@Entity
@Table(name = "PC_MENU_SUBMODULE_AUTH")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="MENU_SUBMODULE_AUTH_ID"))
})
public class MenuSubmoduleAuth extends BaseEntity {
    @Transient
    private static final long serialVersionUID = 1219447155393384746L;
    
    @ManyToOne()
    @JoinColumn(name="MENU_ID", nullable = false)
    private Menu menu;
    
    
    @ManyToOne()
    @JoinColumn(name="SUBMODULE_AUTH_ID", nullable = false)
    private SubmoduleAuth submoduleAuth;

    public MenuSubmoduleAuth() {
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public SubmoduleAuth getSubmoduleAuth() {
        return submoduleAuth;
    }

    public void setSubmoduleAuth(SubmoduleAuth submoduleAuth) {
        this.submoduleAuth = submoduleAuth;
    }
}
