
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
@Table(name = "PC_WF_CONTENT_TYPE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WF_CONTENT_TYPE_ID"))
})
public class WfContentType extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -106781415805972609L;

    @Column(name = "WF_CONTENT_TYPE_NAME", nullable = true, length = 255)
    private String contentTypeName;
    
    @Column(name = "WF_CONTENT_TYPE_CHILD", nullable = true, length = 255)
    private String contentTypeChild;

    public String getContentTypeName() {
        return contentTypeName;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }

    public String getContentTypeChild() {
        return contentTypeChild;
    }

    public void setContentTypeChild(String contentTypeChild) {
        this.contentTypeChild = contentTypeChild;
    }

}
