package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "TempTable")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ตารางชั่วคราว")
public class TempTableModel extends VersionModel {

    private static final long serialVersionUID = 7045328427420116127L;

    @XmlElement(name = "id")
    @Expose
    private int id;

    @XmlElement(name = "createdBy")
    @Expose
    private int createdBy;

    @XmlElement(name = "computerName")
    @ApiParam(name = "computerName", value = "ชื่อคอมพิวเตอร์", required = false)
    @Size(max = 150)
    @Expose
    private String computerName;

    @XmlElement(name = "jobType")
    @ApiParam(name = "jobType", value = "ประเภทงาน", required = true)
    @Size(max = 255)
    @Expose
    private String jobType;

    @XmlElement(name = "int01")
    @ApiParam(name = "int01", value = "เลขข้อมูลตารางชั่วคราว1", required = false)
    @Expose
    private int int01;

    @XmlElement(name = "int02")
    @ApiParam(name = "int02", value = "เลขข้อมูลตารางชั่วคราว2", required = false)
    @Expose
    private int int02;

    @XmlElement(name = "int03")
    @ApiParam(name = "int03", value = "เลขข้อมูลตารางชั่วคราว3", required = false)
    @Expose
    private int int03;

    @XmlElement(name = "int04")
    @ApiParam(name = "int04", value = "เลขข้อมูลตารางชั่วคราว4", required = false)
    @Expose
    private int int04;

    @XmlElement(name = "int05")
    @ApiParam(name = "int05", value = "เลขข้อมูลตารางชั่วคราว5", required = false)
    @Expose
    private int int05;

    @XmlElement(name = "int06")
    @ApiParam(name = "int06", value = "เลขข้อมูลตารางชั่วคราว6", required = false)
    @Expose
    private int int06;

    @XmlElement(name = "int07")
    @ApiParam(name = "int07", value = "เลขข้อมูลตารางชั่วคราว7", required = false)
    @Expose
    private int int07;

    @XmlElement(name = "int08")
    @ApiParam(name = "int08", value = "เลขข้อมูลตารางชั่วคราว8", required = false)
    @Expose
    private int int08;

    @XmlElement(name = "int09")
    @ApiParam(name = "int09", value = "เลขข้อมูลตารางชั่วคราว9", required = false)
    @Expose
    private int int09;

    @XmlElement(name = "int10")
    @ApiParam(name = "int10", value = "เลขข้อมูลตารางชั่วคราว10", required = false)
    @Expose
    private int int10;

    @XmlElement(name = "str01")
    @ApiParam(name = "str01", value = "ข้อมูลตารางชั่วคราว1", required = false)
    @Size(max = 1000)
    @Expose
    private String str01;

    @XmlElement(name = "str02")
    @ApiParam(name = "str02", value = "ข้อมูลตารางชั่วคราว2", required = false)
    @Size(max = 1000)
    @Expose
    private String str02;

    @XmlElement(name = "str03")
    @ApiParam(name = "str03", value = "ข้อมูลตารางชั่วคราว3", required = false)
    @Size(max = 1000)
    @Expose
    private String str03;

    @XmlElement(name = "str04")
    @ApiParam(name = "str04", value = "ข้อมูลตารางชั่วคราว4", required = false)
    @Size(max = 1000)
    @Expose
    private String str04;

    @XmlElement(name = "str05")
    @ApiParam(name = "str05", value = "ข้อมูลตารางชั่วคราว5", required = false)
    @Size(max = 1000)
    @Expose
    private String str05;

    @XmlElement(name = "text01")
    @ApiParam(name = "text01", value = "ข้อความตารางชั่วคราว1", required = false)
    @Size(max = 4000)
    @Expose
    private String text01;

    @XmlElement(name = "text02")
    @ApiParam(name = "text02", value = "ข้อความตารางชั่วคราว2", required = false)
    @Size(max = 4000)
    @Expose
    private String text02;

    @XmlElement(name = "text03")
    @ApiParam(name = "text03", value = "ข้อความตารางชั่วคราว3", required = false)
    @Size(max = 4000)
    @Expose
    private String text03;

    @XmlElement(name = "text04")
    @ApiParam(name = "text04", value = "ข้อความตารางชั่วคราว4", required = false)
    @Size(max = 4000)
    @Expose
    private String text04;

    @XmlElement(name = "text05")
    @ApiParam(name = "text05", value = "ข้อความตารางชั่วคราว5", required = false)
    @Size(max = 4000)
    @Expose
    private String text05;

    @XmlElement(name = "text06")
    @ApiParam(name = "text06", value = "ข้อความตารางชั่วคราว6", required = false)
    @Size(max = 4000)
    @Expose
    private String text06;

    @XmlElement(name = "text07")
    @ApiParam(name = "text07", value = "ข้อความตารางชั่วคราว7", required = false)
    @Size(max = 4000)
    @Expose
    private String text07;

    @XmlElement(name = "text08")
    @ApiParam(name = "text08", value = "ข้อความตารางชั่วคราว8", required = false)
    @Size(max = 4000)
    @Expose
    private String text08;

    @XmlElement(name = "text09")
    @ApiParam(name = "text09", value = "ข้อความตารางชั่วคราว9", required = false)
    @Size(max = 4000)
    @Expose
    private String text09;

    @XmlElement(name = "text10")
    @ApiParam(name = "text10", value = "ข้อความตารางชั่วคราว10", required = false)
    @Size(max = 4000)
    @Expose
    private String text10;

    @XmlElement(name = "date01")
    @ApiParam(name = "date01", value = "date01", required = false)
    @Size(max = 255)
    private String date01;

    public TempTableModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public int getInt01() {
        return int01;
    }

    public void setInt01(int int01) {
        this.int01 = int01;
    }

    public int getInt02() {
        return int02;
    }

    public void setInt02(int int02) {
        this.int02 = int02;
    }

    public int getInt03() {
        return int03;
    }

    public void setInt03(int int03) {
        this.int03 = int03;
    }

    public int getInt04() {
        return int04;
    }

    public void setInt04(int int04) {
        this.int04 = int04;
    }

    public int getInt05() {
        return int05;
    }

    public void setInt05(int int05) {
        this.int05 = int05;
    }

    public int getInt06() {
        return int06;
    }

    public void setInt06(int int06) {
        this.int06 = int06;
    }

    public int getInt07() {
        return int07;
    }

    public void setInt07(int int07) {
        this.int07 = int07;
    }

    public int getInt08() {
        return int08;
    }

    public void setInt08(int int08) {
        this.int08 = int08;
    }

    public int getInt09() {
        return int09;
    }

    public void setInt09(int int09) {
        this.int09 = int09;
    }

    public int getInt10() {
        return int10;
    }

    public void setInt10(int int10) {
        this.int10 = int10;
    }

    public String getStr01() {
        return str01;
    }

    public void setStr01(String str01) {
        this.str01 = str01;
    }

    public String getStr02() {
        return str02;
    }

    public void setStr02(String str02) {
        this.str02 = str02;
    }

    public String getStr03() {
        return str03;
    }

    public void setStr03(String str03) {
        this.str03 = str03;
    }

    public String getStr04() {
        return str04;
    }

    public void setStr04(String str04) {
        this.str04 = str04;
    }

    public String getStr05() {
        return str05;
    }

    public void setStr05(String str05) {
        this.str05 = str05;
    }

    public String getText01() {
        return text01;
    }

    public void setText01(String text01) {
        this.text01 = text01;
    }

    public String getText02() {
        return text02;
    }

    public void setText02(String text02) {
        this.text02 = text02;
    }

    public String getText03() {
        return text03;
    }

    public void setText03(String text03) {
        this.text03 = text03;
    }

    public String getText04() {
        return text04;
    }

    public void setText04(String text04) {
        this.text04 = text04;
    }

    public String getText05() {
        return text05;
    }

    public void setText05(String text05) {
        this.text05 = text05;
    }

    public String getText06() {
        return text06;
    }

    public void setText06(String text06) {
        this.text06 = text06;
    }

    public String getText07() {
        return text07;
    }

    public void setText07(String text07) {
        this.text07 = text07;
    }

    public String getText08() {
        return text08;
    }

    public void setText08(String text08) {
        this.text08 = text08;
    }

    public String getText09() {
        return text09;
    }

    public void setText09(String text09) {
        this.text09 = text09;
    }

    public String getText10() {
        return text10;
    }

    public void setText10(String text10) {
        this.text10 = text10;
    }

    public String getDate01() {
        return date01;
    }

    public void setDate01(String date01) {
        this.date01 = date01;
    }

}
