package com.px.share.entity;

import com.px.admin.entity.UserProfile;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Oat
 */
@Entity
@Table(name = "PC_FILE_ATTACH_APPROVE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "FILE_ATTACH_APPROVE_ID"))
})
public class FileAttachApprove extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -2401200251L;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "FILE_ATTACH_ID", nullable = false)
    private FileAttach fileAttach;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_PROFILE_ID", nullable = false)
    private UserProfile userProfile;

    public FileAttachApprove() {
    }

    public FileAttach getFileAttach() {
        return fileAttach;
    }

    public void setFileAttach(FileAttach fileAttach) {
        this.fileAttach = fileAttach;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

}
