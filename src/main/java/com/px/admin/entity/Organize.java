package com.px.admin.entity;

import com.px.share.entity.BaseTreeEntity;
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
@Table(name = "PC_ORGANIZE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ORGANIZE_ID"))
})
public class Organize extends BaseTreeEntity{
    @Transient
    private static final long serialVersionUID = 5207043669893627032L;
    
    @Column(name="ORGANIZE_NAME", nullable = false, length = 255)
    private String organizeName;
    
    @Column(name="ORGANIZE_SHORT_NAME", nullable = true, length = 50)
    private String organizeShortName;
    
    @Column(name="ORGANIZE_DETAIL",nullable = true,length = 1000)
    private String organizeDetail;
    
    @Column(name="ORGANIZE_CODE",nullable = true,length = 15)
    private String organizeCode;

    public Organize() {
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public String getOrganizeShortName() {
        return organizeShortName;
    }

    public void setOrganizeShortName(String organizeShortName) {
        this.organizeShortName = organizeShortName;
    }

    public String getOrganizeDetail() {
        return organizeDetail;
    }

    public void setOrganizeDetail(String organizeDetail) {
        this.organizeDetail = organizeDetail;
    }

    public String getOrganizeCode() {
        return organizeCode;
    }

    public void setOrganizeCode(String organizeCode) {
        this.organizeCode = organizeCode;
    }
    
}
