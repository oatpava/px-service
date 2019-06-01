package com.px.wf.entity;

import com.px.share.entity.BaseTreeEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author CHALERMPOL
 */
@Entity
@Table(name = "PC_WF_DOCUMENT_TYPE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "DOCUMENT_TYPE_ID"))
})
public class WfDocumentType extends BaseTreeEntity{
    @Transient
    private static final long serialVersionUID = -9024781637500095098L;    
    
    @Column(name="DOCUMENT_TYPE_NAME", nullable = false, length = 255)
    private String documentTypeName;
    
    @Column(name="DOCUMENT_TYPE_DETAIL",nullable = true,length = 1000)
    private String documentTypeDetail;
    
    @Column(name="DOCUMENT_TYPE_CODE",nullable = true,length = 15)
    private String documentTypeCode;
    
    public WfDocumentType() {
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public String getDocumentTypeDetail() {
        return documentTypeDetail;
    }

    public void setDocumentTypeDetail(String documentTypeDetail) {
        this.documentTypeDetail = documentTypeDetail;
    }

    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    
    
}
