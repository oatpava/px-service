package com.px.admin.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author OPAS
 */
@Entity
@Table(name = "PC_TITLE")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="TITLE_ID"))
})
public class Title extends BaseEntity{
    @Transient
    private static final long serialVersionUID = 7245119733329140409L;
    
    @Column(name="TITLE_NAME", nullable = false,length = 50)
    private String titleName;
    
    @Column(name="TITLE_NAME_ENG", nullable = true,length = 50)
    private String titleNameEng;

    public Title() {
    }

    public Title(String titleName, String titleNameEng, Integer createdBy) {
        super(createdBy);
        this.titleName = titleName;
        this.titleNameEng = titleNameEng;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleNameEng() {
        return titleNameEng;
    }

    public void setTitleNameEng(String titleNameEng) {
        this.titleNameEng = titleNameEng;
    }
    
}
