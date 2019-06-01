package com.px.authority.entity;

import com.px.admin.entity.Submodule;
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
@Table(name = "PC_SUBMODULE_AUTH_TEMPLATE")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="SUBMODULE_AUTH_TEMPLATE_ID"))
})
public class SubmoduleAuthTemplate extends BaseEntity{
    @Transient
    private static final long serialVersionUID = 2442506674770699220L;
    
    @Column(name="SUBMODULE_AUTH_TEMPLATE_NAME", nullable = false,length = 255)
    private String submoduleAuthTemplateName;
    
    @ManyToOne()
    @JoinColumn(name="SUBMODULE_ID", nullable = false)
    private Submodule submodule;
    
    @OneToMany(mappedBy="submoduleAuthTemplate",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    private List<SubmoduleAuthTemplateVal> submoduleAuthTemplateVal;

    public SubmoduleAuthTemplate() {
    }

    public String getSubmoduleAuthTemplateName() {
        return submoduleAuthTemplateName;
    }

    public void setSubmoduleAuthTemplateName(String submoduleAuthTemplateName) {
        this.submoduleAuthTemplateName = submoduleAuthTemplateName;
    }

    public Submodule getSubmodule() {
        return submodule;
    }

    public void setSubmodule(Submodule submodule) {
        this.submodule = submodule;
    }

    public List<SubmoduleAuthTemplateVal> getSubmoduleAuthTemplateVal() {
        return submoduleAuthTemplateVal;
    }

    public void setSubmoduleAuthTemplateVal(List<SubmoduleAuthTemplateVal> submoduleAuthTemplateVal) {
        this.submoduleAuthTemplateVal = submoduleAuthTemplateVal;
    }
    
    
}
