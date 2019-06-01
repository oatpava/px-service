package com.px.admin.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author PRAXiS
 */
@Entity
@Table(name = "PC_V_STRUCTURE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "V_STRUCTURE_ID"))
})
public class VStructure extends BaseEntity{

    @Transient
    private static final long serialVersionUID = 3069266617717058053L;
    
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

    public VStructure() {
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
