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
 * @author PRAXiS
 */
@XmlRootElement(name = "Structure")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "เปรียบเทียบโครงสร้าง" )
public class StructureConvertModel extends VersionModel{

    private static final long serialVersionUID = 5735749201453218239L;
    
    @XmlElement(name = "status")
    @ApiParam(name = "status", example = "1", value = "(1=เพิ่ม,2=ลบ,3=แก้ไข)", defaultValue = "สถานะแต่ละเรคคอร์ด", required = true)
    @Size(max = 3)
    @Expose 
    @Since(1.0)
    private int status;
    
    @XmlElement(name = "structure")
    @ApiParam(name = "structure", value = "โครงสร้างหน่วยงาน", required = false)
    @Expose 
    @Since(1.0)
    private StructureModel structure;
    
    @XmlElement(name = "vStructure")
    @ApiParam(name = "vStructure", value = "view โครงสร้างหน่วยงาน", required = false)
    @Expose 
    @Since(1.0)
    private VStructureModel vStructure;

    public StructureConvertModel() {
    }

    public int getStatus() {
        return status;
    }

    @ApiModelProperty(name = "status", example = "0", dataType = "int", value = "สถานะ", required = true)
    public void setStatus(int status) {
        this.status = status;
    }

    public StructureModel getStructure() {
        return structure;
    }

    @ApiModelProperty(name = "structure", value = "โครงสร้างหน่วยงาน", required = false)
    public void setStructure(StructureModel structure) {
        this.structure = structure;
    }

    public VStructureModel getVStructure() {
        return vStructure;
    }

    @ApiModelProperty(name = "vStructure", value = "view โครงสร้างหน่วยงาน", required = false)
    public void setVStructure(VStructureModel vStructure) {
        this.vStructure = vStructure;
    }

}
