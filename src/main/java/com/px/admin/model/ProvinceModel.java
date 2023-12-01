package com.px.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oat
 */
@XmlRootElement(name = "Province")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "จังหวัด")
public class ProvinceModel extends VersionModel {

    @XmlElement(name = "id")
    @ApiParam(name = "id", value = "รหัสจังหวัด", required = true)
    @Since(1.0)
    @Expose
    private int id;

    @XmlElement(name = "code")
    @ApiParam(name = "code", value = "รหัสแทนจังหวัด", required = true)
    @Size(max = 10)
    @Expose
    @Since(1.1)
    private String code;

    @XmlElement(name = "name")
    @ApiParam(name = "name", value = "ชื่อจังหวัด", required = true)
    @Size(max = 100)
    @Expose
    @Since(1.0)
    private String name;

    public ProvinceModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "1", dataType = "int", value = "รหัสจังหวัด", required = true)
    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    @ApiModelProperty(name = "code", example = "0010", dataType = "string", value = "รหัสแทนจังหวัด", required = true)
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "กรุงเทพมหานคร", dataType = "string", value = "ชื่อจังหวัด", required = true)
    public void setName(String name) {
        this.name = name;
    }

}
