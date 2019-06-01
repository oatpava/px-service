
package com.px.mwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "WorkflowModel_groupLinkFlow")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "รายการข้อมูลการเชื่อมโยงผังการไหลแบบรูปภาพ")
public class WorkflowModel_groupLinkFlow implements Serializable {

    private static final long serialVersionUID = -4931945062268641120L;
    
    @XmlElement(name = "from")
    @Expose @Since(1.0) private String from;
    @XmlElement(name = "to")
    @Expose @Since(1.0) private String to;
    @XmlElement(name = "category")
    @Expose @Since(1.0) private String category;
    @XmlElement(name = "caption")
    @Expose @Since(1.0) private String caption;
    @XmlElement(name = "text")
    @Expose @Since(1.0) private String text;
    
    public WorkflowModel_groupLinkFlow() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
