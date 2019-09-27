package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import org.hibernate.annotations.Cascade;
import sg.edu.nus.comp.cs3219.viz.common.entity.UserProfile;

import javax.persistence.*;

@Exportable(name = "File Record", nameInDB = "file_record")
@Entity
public class FileRecord {

    @EmbeddedId
    private FileId fileId;

    @Column(name = "file_name")
    private String fileName;

    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private UserProfile userProfile;

    public FileRecord() {
        this.fileId = new FileId();
    }

    public UserProfile getUserProfile() {
        return this.userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public int getFileNumber() {
        return this.fileId.getFileNumber();
    }

    public void setFileNumber(int fileNumber) {
        this.fileId.setFileNumber(fileNumber);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileId getFileId() {
        return fileId;
    }

    public void setFileId(FileId fileId) {
        this.fileId = fileId;
    }
}
