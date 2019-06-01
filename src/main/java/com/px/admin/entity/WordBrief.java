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
@Table(name = "PC_WORDBRIEF")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="WORDBRIEF_ID"))
})
public class WordBrief extends BaseEntity {
    @Transient
    private static final long serialVersionUID = -1864654376173365526L;
    
    @Column(name="WORDBRIEF_NAME", nullable = false,length = 100)
    private String wordBriefName;

    public WordBrief() {
    }
    
    public WordBrief(String wordBriefName, Integer createdBy) {
        super(createdBy);
        this.wordBriefName = wordBriefName;
    }

    public String getWordBriefName() {
        return wordBriefName;
    }

    public void setWordBriefName(String wordBriefName) {
        this.wordBriefName = wordBriefName;
    }
    
    
}
