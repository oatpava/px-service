
package com.px.wf.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Mali
 */
@Entity
@Table(name = "PC_THEGIF_DOCFILE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "THEGIF_DOCFILE_ID"))
})
public class ThegifDocFile extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -1697821998042535258L;
    
    @Column(name = "THEGIF_ID", columnDefinition = "int default '0'")
    private int thegifId;
    
    @Column(name = "THEGIF_DOCFILE_FILE_ID", columnDefinition = "int default '0'")
    private int thegifDocFileFileId; 
    
    @Column(name = "THEGIF_DOCFILE_LETTER", columnDefinition="Text")
    private String thegifDocFileLetter;
    
    @Column(name = "THEGIF_DOCFILE_LETTER_TYPE", length = 255)
    private String thegifDocFileLetterType;
    
    @Column(name = "DMS_DOCUMENT_ID", columnDefinition = "int default '0'")
    private int dmsDocumentId;
    
    public ThegifDocFile() {
    }

    public int getThegifId() {
        return thegifId;
    }

    public void setThegifId(int thegifId) {
        this.thegifId = thegifId;
    }

    public int getThegifDocFileFileId() {
        return thegifDocFileFileId;
    }

    public void setThegifDocFileFileId(int thegifDocFileFileId) {
        this.thegifDocFileFileId = thegifDocFileFileId;
    }
    
    public String getThegifDocFileLetter() {
        return thegifDocFileLetter;
    }

    public void setThegifDocFileLetter(String thegifDocFileLetter) {
        this.thegifDocFileLetter = thegifDocFileLetter;
    }

    public String getThegifDocFileLetterType() {
        return thegifDocFileLetterType;
    }

    public void setThegifDocFileLetterType(String thegifDocFileLetterType) {
        this.thegifDocFileLetterType = thegifDocFileLetterType;
    }

    public int getDmsDocumentId() {
        return dmsDocumentId;
    }

    public void setDmsDocumentId(int dmsDocumentId) {
        this.dmsDocumentId = dmsDocumentId;
    }
   
}
