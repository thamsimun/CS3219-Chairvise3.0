package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import sg.edu.nus.comp.cs3219.viz.common.entity.UserDetails;

import javax.persistence.*;

@Exportable(name = "File Record", nameInDB = "file_record")
@Entity
public class FileRecord {

    @EmbeddedId
    private FileId fileId;

    @Column(name = "file_name")
    @Exportable(name = "fileName", nameInDB = "file_name")
    private String fileName;

    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserDetails userDetails;

    @Column(name = "file_type")
    private String fileType;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public FileRecord() {
        this.fileId = new FileId();
    }

    public UserDetails getUserDetails() {
        return this.userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
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
