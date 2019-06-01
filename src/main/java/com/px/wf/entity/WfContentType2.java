package com.px.wf.entity;

import com.px.share.entity.BaseEntity;
import java.time.LocalDateTime;
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
@Table(name = "PC_WF_CONTENT_TYPE2")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WF_CONTENT_TYPE2_ID"))
})
public class WfContentType2 extends BaseEntity {

    @Transient
    private static final long serialVersionUID = 5420472167490329912L;

    @Column(name = "WF_CONTENT_TYPE2_NAME", nullable = true, length = 255)
    private String contentType2Name;

    @Column(name = "WF_CONTENT_TYPE2_DATE", nullable = true)
    private LocalDateTime contentType2Date;//oat-add

    public String getContentType2Name() {
        return contentType2Name;
    }

    public void setContentType2Name(String contentType2Name) {
        this.contentType2Name = contentType2Name;
    }

    public LocalDateTime getContentType2Date() {
        return contentType2Date;
    }

    public void setContentType2Date(LocalDateTime contentType2Date) {
        this.contentType2Date = contentType2Date;
    }

}
