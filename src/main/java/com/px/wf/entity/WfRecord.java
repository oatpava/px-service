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
 * @author oat
 */
@Entity
@Table(name = "PC_WF_RECORD")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WF_RECORD_ID"))
})
public class WfRecord extends BaseEntity {

    @Transient
    private static final long serialVersionUID = 2323L;

    @Column(name = "CONTENT_ID", columnDefinition = "int default '0'")
    private Integer contentId;

    @Column(name = "DOCUMENT_ID", columnDefinition = "int default '0'")
    private Integer documentId;

    @Column(name = "DESCRIPTION", nullable = true, length = 4000)
    private String description;

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }
    
    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
