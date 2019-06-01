package com.px.share.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author OPAS
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListReturnModel", propOrder = {
    "all",
    "count",
    "next"
})
public class ListReturnModel implements Serializable{
    private static final long serialVersionUID = -7720848116942585292L;
    
    @XmlElement(name = "all")
    @Expose private int all;
    @XmlElement(name = "count")
    @Expose private int count;
    @XmlElement(name = "next")
    @Expose private int next;

    public ListReturnModel() {
    }

    public ListReturnModel(int all, int count, int next) {
        this.all = all;
        this.count = count;
        this.next = next;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }
    
    
}
