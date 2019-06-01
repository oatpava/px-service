package com.px.share.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Peach
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListReturnLongModel", propOrder = {
    "all",
    "count",
    "next"
})
public class ListReturnLongModel implements Serializable{
    private static final long serialVersionUID = -7720848116942585292L;
    
    @XmlElement(name = "all")
    @Expose private long all;
    @XmlElement(name = "count")
    @Expose private long count;
    @XmlElement(name = "next")
    @Expose private long next;

    public ListReturnLongModel() {
    }

    public ListReturnLongModel(long all, long count, long next) {
        this.all = all;
        this.count = count;
        this.next = next;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getAll() {
        return all;
    }

    public void setAll(long all) {
        this.all = all;
    }

    public long getNext() {
        return next;
    }

    public void setNext(long next) {
        this.next = next;
    }
    
    
}
