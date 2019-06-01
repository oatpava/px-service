package com.px.share.entity;

import com.px.share.entity.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Mali
 */
@Entity
@Table(name = "PC_TEMPTABLE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "TEMPTABLE_ID"))
})
public class TempTable extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -4397784278696417760L;

    @Column(name = "COMPUTER_NAME", nullable = false, length = 150)
    private String computerName;

    @Column(name = "JOB_TYPE", nullable = false, length = 255)
    private String jobType;

    @Column(name = "INT01", columnDefinition = "int default '0'")
    private Integer int01;

    @Column(name = "INT02", columnDefinition = "int default '0'")
    private Integer int02;

    @Column(name = "INT03", columnDefinition = "int default '0'")
    private Integer int03;

    @Column(name = "INT04", columnDefinition = "int default '0'")
    private Integer int04;

    @Column(name = "INT05", columnDefinition = "int default '0'")
    private Integer int05;

    @Column(name = "INT06", columnDefinition = "int default '0'")
    private Integer int06;

    @Column(name = "INT07", columnDefinition = "int default '0'")
    private Integer int07;

    @Column(name = "INT08", columnDefinition = "int default '0'")
    private Integer int08;

    @Column(name = "INT09", columnDefinition = "int default '0'")
    private Integer int09;

    @Column(name = "INT10", columnDefinition = "int default '0'")
    private Integer int10;

    @Column(name = "STR01", nullable = true, length = 1000)
    private String str01;

    @Column(name = "STR02", nullable = true, length = 1000)
    private String str02;

    @Column(name = "STR03", nullable = true, length = 1000)
    private String str03;

    @Column(name = "STR04", nullable = true, length = 1000)
    private String str04;

    @Column(name = "STR05", nullable = true, length = 1000)
    private String str05;

    //@Column(name = "TEXT01", nullable = true, columnDefinition="Text")
    @Column(name = "TEXT01", nullable = true, length = 4000)
    private String text01;

    //@Column(name = "TEXT02", nullable = true, columnDefinition = "Text")
    @Column(name = "TEXT02", nullable = true, length = 4000)
    private String text02;

    //@Column(name = "TEXT03", nullable = true, columnDefinition = "Text")
    @Column(name = "TEXT03", nullable = true, length = 4000)
    private String text03;

    //@Column(name = "TEXT04", nullable = true, columnDefinition = "Text")
    @Column(name = "TEXT04", nullable = true, length = 4000)
    private String text04;

    //@Column(name = "TEXT05", nullable = true, columnDefinition = "Text")
    @Column(name = "TEXT05", nullable = true, length = 4000)
    private String text05;

    //@Column(name = "TEXT06", nullable = true, columnDefinition = "Text")
    @Column(name = "TEXT06", nullable = true, length = 4000)
    private String text06;

    //@Column(name = "TEXT07", nullable = true, columnDefinition = "Text")
    @Column(name = "TEXT07", nullable = true, length = 4000)
    private String text07;

    //@Column(name = "TEXT08", nullable = true, columnDefinition = "Text")
    @Column(name = "TEXT08", nullable = true, length = 4000)
    private String text08;

    //@Column(name = "TEXT09", nullable = true, columnDefinition = "Text")
    @Column(name = "TEXT09", nullable = true, length = 4000)
    private String text09;

    //@Column(name = "TEXT10", nullable = true, columnDefinition = "Text")
    @Column(name = "TEXT10", nullable = true, length = 4000)
    private String text10;

    //oat-add
    @Column(name = "DATE01", nullable = true)
    private LocalDateTime date01;

    public TempTable() {
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

    public Integer getInt01() {
        return int01;
    }

    public void setInt01(Integer int01) {
        this.int01 = int01;
    }

    public Integer getInt02() {
        return int02;
    }

    public void setInt02(Integer int02) {
        this.int02 = int02;
    }

    public Integer getInt03() {
        return int03;
    }

    public void setInt03(Integer int03) {
        this.int03 = int03;
    }

    public Integer getInt04() {
        return int04;
    }

    public void setInt04(Integer int04) {
        this.int04 = int04;
    }

    public Integer getInt05() {
        return int05;
    }

    public void setInt05(Integer int05) {
        this.int05 = int05;
    }

    public Integer getInt06() {
        return int06;
    }

    public void setInt06(Integer int06) {
        this.int06 = int06;
    }

    public Integer getInt07() {
        return int07;
    }

    public void setInt07(Integer int07) {
        this.int07 = int07;
    }

    public Integer getInt08() {
        return int08;
    }

    public void setInt08(Integer int08) {
        this.int08 = int08;
    }

    public Integer getInt09() {
        return int09;
    }

    public void setInt09(Integer int09) {
        this.int09 = int09;
    }

    public Integer getInt10() {
        return int10;
    }

    public void setInt10(Integer int10) {
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

    public LocalDateTime getDate01() {
        return date01;
    }

    public void setDate01(LocalDateTime date01) {
        this.date01 = date01;
    }
}
