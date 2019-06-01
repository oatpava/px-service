package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OPAS
 */
@XmlRootElement(name = "LevelBar")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "LevelBar" )
public class LevelBarModel extends VersionModel{
    private static final long serialVersionUID = -245948113389869808L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id",value = "id",required = false)
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "listLevelName")
    @ApiParam(name = "listLevelName",value = "listLevelName",required = false)
    @Expose 
    @Since(1.0)
    private ArrayList<String> listLevelName;
    
    @XmlElement(name = "listLevel")
    @ApiParam(name = "listLevel",value = "listLevel",required = false)
    @Expose 
    @Since(1.0)
    private ArrayList<LevelModel> listLevel;

    @XmlElement(name = "newLevel")
    @ApiParam(name = "newLevel",value = "newLevel",required = false)
    @Expose 
    @Since(1.0)
    private LevelModel newLevel;

    public LevelBarModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", value = "id", required = false)
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getListLevelName() {
        return listLevelName;
    }

    @ApiModelProperty(name = "listLevelName", value = "listLevelName", required = false)
    public void setListLevelName(ArrayList<String> listLevelName) {
        this.listLevelName = listLevelName;
    }

    public ArrayList<LevelModel> getListLevel() {
        return listLevel;
    }

    @ApiModelProperty(name = "listLevel", value = "listLevel", required = false)
    public void setListLevel(ArrayList<LevelModel> listLevel) {
        this.listLevel = listLevel;
    }

    public LevelModel getNewLevel() {
        return newLevel;
    }

    @ApiModelProperty(name = "newLevel", value = "newLevel", required = false)
    public void setNewLevel(LevelModel newLevel) {
        this.newLevel = newLevel;
    }


    
    
}
