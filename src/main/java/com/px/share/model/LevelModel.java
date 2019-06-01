package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
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
 * @author Peach
 */
@XmlRootElement(name = "Level")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "Level" )
public class LevelModel extends VersionModel {
    private static final long serialVersionUID = -6599539062876396067L;
    
    @Since(1.0)
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "levelId", defaultValue = "0", required = false )
    @Expose 
    private int id;

    @Since(1.0)
    @XmlElement(name = "shortSize")
    @ApiParam(name = "shortSize", example = "0", value = "shortSize", defaultValue = "100", required = false )
    @Expose 
    private int shortSize;
     
    @Since(1.0)
    @XmlElement(name = "levelName")
    @ApiParam(name = "levelName",example = "levelName",value = "levelName",defaultValue = "",required = false)
    @Size(max = 255)
    @Expose
    private String levelName;
    
    @Since(1.0)
    @XmlElement(name = "levelIcon")
    @ApiParam(name = "levelIcon",example = "levelIcon",value = "levelIcon",defaultValue = "",required = false)
    @Size(max = 100)
    @Expose
    private String levelIcon;

    @Since(1.0)
    @XmlElement(name = "shortName")
    @ApiParam(name = "shortName",example = "shortName",value = "shortName",defaultValue = "",required = false)
    @Size(max = 100)
    @Expose
    private String shortName;
    
    @Since(1.0)
    @XmlElement(name = "levelUrl")
    @ApiParam(name = "levelUrl",example = "levelUrl",value = "levelUrl",defaultValue = "",required = false)
    @Size(max = 1000)
    @Expose
    private String levelUrl;

    public LevelModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสไฟล์เอกสารแนบ", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public int getShortSize() {
        return shortSize;
    }

    @ApiModelProperty(name = "shortSize", example = "0", dataType = "int", value = "shortSize", required = false)
    public void setShortSize(int shortSize) {
        this.shortSize = shortSize;
    }

    public String getLevelName() {
        return levelName;
    }

    @ApiModelProperty(name = "levelName", value = "levelName", dataType = "String", required = false)
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelIcon() {
        return levelIcon;
    }

    @ApiModelProperty(name = "levelIcon", value = "levelIcon", dataType = "String", required = false)
    public void setLevelIcon(String levelIcon) {
        this.levelIcon = levelIcon;
    }

    public String getShortName() {
        return shortName;
    }

    @ApiModelProperty(name = "shortName", value = "shortName", dataType = "String", required = false)
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLevelUrl() {
        return levelUrl;
    }

    @ApiModelProperty(name = "levelUrl", value = "levelUrl", dataType = "String", required = false)
    public void setLevelUrl(String levelUrl) {
        this.levelUrl = levelUrl;
    }

}
