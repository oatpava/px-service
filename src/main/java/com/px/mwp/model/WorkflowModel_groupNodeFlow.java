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
@XmlRootElement(name = "WorkflowModel_groupNodeFlow")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "รายการข้อมูล node ของผังการไหลแบบรูปภาพ")
public class WorkflowModel_groupNodeFlow implements Serializable {

    private static final long serialVersionUID = -710933744526561535L;

    @XmlElement(name = "key")
    @Expose
    @Since(1.0)
    private String key;
    @XmlElement(name = "name")
    @Expose
    @Since(1.0)
    private String name;
    @XmlElement(name = "text")
    @Expose
    @Since(1.0)
    private String text;
    @XmlElement(name = "category")
    @Expose
    @Since(1.0)
    private String category;
    @XmlElement(name = "source")
    @Expose
    @Since(1.0)
    private String source;
    @XmlElement(name = "isStrikethrough")//oat-add
    @Expose
    @Since(1.0)
    private boolean isStrikethrough;

    public WorkflowModel_groupNodeFlow() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean getIsStrikethrough() {
        return isStrikethrough;
    }

    public void setIsStrikethrough(boolean isStrikethrough) {
        this.isStrikethrough = isStrikethrough;
    }

}
