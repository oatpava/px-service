
package com.px.wf.model.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@MappedSuperclass
@ApiModel( description = "รายการของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ" )
@XmlRootElement(name = "ThegifModel_groupShowList")
public class ThegifModel_groupShowList {
    @Expose @Since(1.0) private String fieldName;
    @Expose @Since(1.0) private String data;
    @Expose @Since(1.0) private int thegifId; 
    @Expose @Since(1.0) private String fieldDescription; 
    @Expose @Since(1.0) private String fieldType; 
    @Expose @Since(1.0) private int listWidth; 
    @Expose @Since(1.0) private int fieldLength; 

    public ThegifModel_groupShowList() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getThegifId() {
        return thegifId;
    }

    public void setThegifId(int thegifId) {
        this.thegifId = thegifId;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getListWidth() {
        return listWidth;
    }

    public void setListWidth(int listWidth) {
        this.listWidth = listWidth;
    }

    public int getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(int fieldLength) {
        this.fieldLength = fieldLength;
    }

}
