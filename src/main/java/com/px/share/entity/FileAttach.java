package com.px.share.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author OPAS
 */
@Entity
@Table(name = "PC_FILE_ATTACH")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "FILE_ATTACH_ID"))
})
public class FileAttach extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -8755809664500816196L;

    @Column(name = "FILE_ATTACH_NAME", nullable = false, length = 255)
    private String fileAttachName;

    @Column(name = "FILE_ATTACH_TYPE", nullable = false, length = 100)
    private String fileAttachType;

    @Column(name = "FILE_ATTACH_SIZE", nullable = true)
    private long fileAttachSize;

    @Column(name = "LINK_TYPE", nullable = false, length = 12)
    private String linkType;

    @Column(name = "LINK_ID", nullable = false)
    private int linkId;

    @Column(name = "REFERENCE_ID", columnDefinition = "Integer default '0'", nullable = true)
    private int referenceId;

    @Column(name = "SECRETS", columnDefinition = "Integer default '1'", nullable = true)
    private int secrets;

    @Column(name = "FLAG_CA", nullable = true, length = 1)
    private String flagCa;

    public FileAttach() {
    }

    public String getFileAttachName() {
        return fileAttachName;
    }

    public void setFileAttachName(String fileAttachName) {
        this.fileAttachName = fileAttachName;
    }

    public String getFileAttachType() {
        return fileAttachType;
    }

    public void setFileAttachType(String fileAttachType) {
        this.fileAttachType = fileAttachType;
    }

    public long getFileAttachSize() {
        return fileAttachSize;
    }

    public void setFileAttachSize(long fileAttachSize) {
        this.fileAttachSize = fileAttachSize;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public int getSecrets() {
        return secrets;
    }

    public void setSecrets(int secrets) {
        this.secrets = secrets;
    }

    public String getFlagCa() {
        return flagCa;
    }

    public void setFlagCa(String flagCa) {
        this.flagCa = flagCa;
    }

}
