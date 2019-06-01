//
//package com.px.wf.model.get;
//
//import com.google.gson.annotations.Expose;
//import com.px.rest.model.get.authority.SubmoduleUserAuthModel_groupStructureUser;
//import io.swagger.annotations.ApiModel;
//import java.io.Serializable;
//import java.util.List;
//import javax.persistence.MappedSuperclass;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;
//
///**
// *
// * @author Mali
// */
//@MappedSuperclass
//@ApiModel( description = "รายการของแฟ้มทะเบียนซึ่งจัดกลุ่มโดยแฟ้มทะเบียนและสิทธิ์ของแฟ้มทะเบียน" )
//@XmlRootElement(name = "WfFolderModel_groupWfFolderAndAuth")
//public class WfFolderModel_groupWfFolderAndAuth implements Serializable {
//
//    private static final long serialVersionUID = -8908071555741707345L;
//    
//    @XmlElement(name = "WfFolder")
//    @Expose private WfFolderModel WfFolder;
//    
//    @XmlElement(name = "hasShortcut")
//    @Expose private List<SubmoduleUserAuthModel_groupStructureUser> submoduleUserAuthModel_groupStructureUser;
//    
//    public WfFolderModel_groupWfFolderAndAuth() {
//    }
//
//    public WfFolderModel getWfFolder() {
//        return WfFolder;
//    }
//
//    public void setWfFolder(WfFolderModel WfFolder) {
//        this.WfFolder = WfFolder;
//    }
//
//    public List<SubmoduleUserAuthModel_groupStructureUser> getSubmoduleUserAuthModel_groupStructureUser() {
//        return submoduleUserAuthModel_groupStructureUser;
//    }
//
//    public void setSubmoduleUserAuthModel_groupStructureUser(List<SubmoduleUserAuthModel_groupStructureUser> submoduleUserAuthModel_groupStructureUser) {
//        this.submoduleUserAuthModel_groupStructureUser = submoduleUserAuthModel_groupStructureUser;
//    }
//   
//}
