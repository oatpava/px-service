package com.px.share.entity; 

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
/**
 * Tree entity type to hold common Tree property. To be extended.
 * 
 * @author Peach
 */
@MappedSuperclass
public abstract class BaseTreeEntity extends BaseEntity{
    private static final long serialVersionUID = 535792164890133966L;
    
    @Column(name="PARENT_ID", nullable = true)
    private Integer parentId;
        
    @Column(name="PARENT_KEY", nullable = true,length = 255)
    private String parentKey;
    
    @Column(name="NODE_LEVEL", nullable = true)
    private Integer nodeLevel;
        
    public BaseTreeEntity() {
    }
    
    public BaseTreeEntity(Integer createdBy) {
        super(createdBy);
    } 

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    
}