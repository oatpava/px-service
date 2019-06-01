package com.px.admin.entity;

import com.px.authority.entity.ModuleUserAuth;
import com.px.authority.entity.SubmoduleUserAuth;
import com.px.share.entity.BaseTreeEntity;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author ken
 */
@Entity
@Table(name = "PC_STRUCTURE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "STRUCTURE_ID"))
})
public class Structure extends BaseTreeEntity{
    @Transient
    private static final long serialVersionUID = 5586499741213523130L;
    
    @Column(name="STRUCTURE_NAME", nullable = false, length = 255)
    private String structureName;
    
    @Column(name="STRUCTURE_SHORT_NAME", nullable = true, length = 50)
    private String structureShortName;
    
    @Column(name="STRUCTURE_DETAIL",nullable = true,length = 1000)
    private String structureDetail;
    
    @Column(name="STRUCTURE_CODE",nullable = true,length = 15)
    private String structureCode;
    
    @Column(name="STRUCTURE_TYPE",nullable = true, columnDefinition="int default '0'")
    private int structureType;
    
    @OneToMany(mappedBy="structure",cascade={CascadeType.DETACH},fetch = FetchType.LAZY)
    private List<SubmoduleUserAuth> submoduleUserAuth;
    
    @OneToMany(mappedBy="structure",cascade={CascadeType.DETACH},fetch = FetchType.LAZY)
    private List<ModuleUserAuth> moduleUserAuth;

    public Structure() {
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getStructureShortName() {
        return structureShortName;
    }

    public void setStructureShortName(String structureShortName) {
        this.structureShortName = structureShortName;
    }
    
    public String getStructureDetail() {
        return structureDetail;
    }

    public void setStructureDetail(String structureDetail) {
        this.structureDetail = structureDetail;
    }

    public List<SubmoduleUserAuth> getSubmoduleUserAuth() {
        return submoduleUserAuth;
    }

    public void setSubmoduleUserAuth(List<SubmoduleUserAuth> submoduleUserAuth) {
        this.submoduleUserAuth = submoduleUserAuth;
    }

    public List<ModuleUserAuth> getModuleUserAuth() {
        return moduleUserAuth;
    }

    public void setModuleUserAuth(List<ModuleUserAuth> moduleUserAuth) {
        this.moduleUserAuth = moduleUserAuth;
    }

    public String getStructureCode() {
        return structureCode;
    }

    public void setStructureCode(String structureCode) {
        this.structureCode = structureCode;
    }

    public int getStructureType() {
        return structureType;
    }

    public void setStructureType(int structureType) {
        this.structureType = structureType;
    }
    
}
