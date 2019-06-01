/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author TOP
 */
@Entity
@Table(name = "PC_DOCUMENT_TYPE")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="DOCUMENT_TYPE_ID"))
})
public class DocumentType extends BaseEntity{
    @Transient
    public static final long serialVersionUID = 0L;
    
     @Column(name="DOCUMENT_TYPE_NAME", nullable = true,length = 255)
    private String documentTypeName;
    
    @Column(name="DOCUMENT_TYPE_DESCRIPTION", nullable = true, length = 4000)
    private String documentTypeDescription;

    public DocumentType() {
    }

    public DocumentType(String documentTypeName, String documentTypeDescription, Integer createdBy) {
        super(createdBy);
        this.documentTypeName = documentTypeName;
        this.documentTypeDescription = documentTypeDescription;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public String getDocumentTypeDescription() {
        return documentTypeDescription;
    }

    public void setDocumentTypeDescription(String documentTypeDescription) {
        this.documentTypeDescription = documentTypeDescription;
    }
    
}
