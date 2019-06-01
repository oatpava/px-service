package com.px.share.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
/**
 * Base entity type to hold common Id property. To be extended.
 * 
 * @author OPAS
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable{
    private static final long serialVersionUID = 1104784577806556865L;
    
    @TableGenerator(name="table-gen",
                    pkColumnName="TABLE_NAME", allocationSize=1,
                    table="PC_HIBERNATE_GENERATORS")
    @Id
    @Generated(GenerationTime.INSERT)
    @Basic    
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table-gen")    
    @Column(name="ID", unique = true, nullable = false, insertable = true, updatable = false)
    private Integer id;
            
    /**
     *
     */
    @Column(name="CREATED_BY", columnDefinition="Integer default '0'", nullable = false, insertable=true, updatable = false)
    private Integer createdBy;
    
    @Column(name="CREATED_DATE", nullable = false, insertable=true, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
    
    @Column(name="UPDATED_BY", columnDefinition="Integer default '0'", nullable = true, insertable=false, updatable=true)
    private Integer updatedBy = 0;
    
    @Column(name="UPDATED_DATE", nullable = true, insertable=false, updatable=true)
    private LocalDateTime updatedDate;
    
    @Column(name="REMOVED_BY", columnDefinition="Integer default '0'", nullable = false, insertable=false, updatable=true)
    private Integer removedBy = 0;
    
    @Column(name="REMOVED_DATE", nullable = true, insertable=false, updatable=true)
    private LocalDateTime removedDate;    
    
    @Generated(GenerationTime.INSERT)
    @Column(name="ORDER_NO", columnDefinition="float default '0.0'", nullable = true, insertable=false, updatable=true)
    private double orderNo = 0;
            
    public BaseEntity() {
        this.createdDate = LocalDateTime.now();
    }

    public BaseEntity(Integer createdBy) {
        this.createdBy = createdBy;
    }    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getRemovedBy() {
        return removedBy;
    }

    public void setRemovedBy(Integer removedBy) {
        this.removedBy = removedBy;
    }

    public LocalDateTime getRemovedDate() {
        return removedDate;
    }

    public void setRemovedDate(LocalDateTime removedDate) {
        this.removedDate = removedDate;
    }
    
    public double getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(double orderNo) {
        this.orderNo = orderNo;
    }
}