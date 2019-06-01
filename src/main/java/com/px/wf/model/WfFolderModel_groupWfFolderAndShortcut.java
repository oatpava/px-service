
package com.px.wf.model;

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
@ApiModel( description = "รายการของแฟ้มทะเบียนซึ่งจัดกลุ่มโดยแฟ้มทะเบียนและแฟ้มทะเบียนลัด" )
@XmlRootElement(name = "WfFolderModel_groupWfFolderAndShortcut")
public class WfFolderModel_groupWfFolderAndShortcut extends WfFolderModel {
    
    private static final long serialVersionUID = -8910029145313090597L;

    @Expose @Since(1.0) private String hasShortcut;


    public WfFolderModel_groupWfFolderAndShortcut() {
    }

    public String getHasShortcut() {
        return hasShortcut;
    }

    public void setHasShortcut(String hasShortcut) {
        this.hasShortcut = hasShortcut;
    }
}
