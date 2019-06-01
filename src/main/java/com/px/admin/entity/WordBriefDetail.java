package com.px.admin.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author OPAS
 */
@Entity
@Table(name = "PC_WORDBRIEF_DETAIL")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="WORDBRIEF_DETAIL_ID"))
})
public class WordBriefDetail extends BaseEntity {
    @Transient
    private static final long serialVersionUID = -6416367086699476039L;
    
    @Column(name="WORDBRIEF_DETAIL_NAME", nullable = false,length = 255)
    private String WordBriefDetailName;
    
    @ManyToOne(cascade={CascadeType.DETACH})
    @JoinColumn(name="WORDBRIEF_ID", nullable = false)
    private WordBrief wordBrief;

    public WordBriefDetail() {
    }

    public WordBriefDetail(String WordBriefDetailName, WordBrief wordBrief, Integer createdBy) {
        super(createdBy);
        this.WordBriefDetailName = WordBriefDetailName;
        this.wordBrief = wordBrief;
    }

    public String getWordBriefDetailName() {
        return WordBriefDetailName;
    }

    public void setWordBriefDetailName(String WordBriefDetailName) {
        this.WordBriefDetailName = WordBriefDetailName;
    }

    public WordBrief getWordBrief() {
        return wordBrief;
    }

    public void setWordBrief(WordBrief wordBrief) {
        this.wordBrief = wordBrief;
    }
    
    
}
