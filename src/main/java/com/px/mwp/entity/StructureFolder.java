package com.px.mwp.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author ken
 */
@Entity
@Table(name = "PC_STRUCTURE_FOLDER")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "STRUCTURE_FOLDER_ID"))
})
public class StructureFolder extends BaseEntity {

    @Transient
    private static final long serialVersionUID = 3670870567982632766L;

    @Column(name = "STRUCTURE_FOLDER_NAME", length = 255)
    private String structureFolderName;

    @Column(name = "STRUCTURE_FOLDER_TYPE", length = 10)
    private String structureFolderType;

    @Column(name = "STRUCTURE_FOLDER_LINK_ID", columnDefinition = "int default '0'")
    private int structureFolderLinkId;

    @Column(name = "STRUCTURE_FOLDER_DETAIL", length = 1000)
    private String structureFolderDetail;

//    @ManyToOne(cascade={CascadeType.DETACH})
//    @JoinColumn(name="STRUCTURE_ID", nullable = false)
//    private Structure structure;
    
    @Column(name = "STRUCTURE_ID", columnDefinition = "int default '0'")
    private int structureId;

    public StructureFolder() {
    }

    public StructureFolder(Integer createdBy) {
        super(createdBy);
    }

    public String getStructureFolderName() {
        return structureFolderName;
    }

    public void setStructureFolderName(String structureFolderName) {
        this.structureFolderName = structureFolderName;
    }

    public String getStructureFolderType() {
        return structureFolderType;
    }

    public void setStructureFolderType(String structureFolderType) {
        this.structureFolderType = structureFolderType;
    }

    public int getStructureFolderLinkId() {
        return structureFolderLinkId;
    }

    public void setStructureFolderLinkId(int structureFolderLinkId) {
        this.structureFolderLinkId = structureFolderLinkId;
    }

    public String getStructureFolderDetail() {
        return structureFolderDetail;
    }

    public void setStructureFolderDetail(String structureFolderDetail) {
        this.structureFolderDetail = structureFolderDetail;
    }

    public int getStructureId() {
        return structureId;
    }

    public void setStructureId(int structureId) {
        this.structureId = structureId;
    }
}
