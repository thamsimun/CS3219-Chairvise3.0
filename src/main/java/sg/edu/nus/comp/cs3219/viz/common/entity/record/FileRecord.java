package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import javax.persistence.*;

@Exportable(name = "File Record", nameInDB = "file_record")
@Entity
public class FileRecord {

    @EmbeddedId
    private FileId fileId;

    @Column(name = "file_name")
    private String fileName;

    public FileRecord(long userId, String fileNumber, String fileName) {
        this.fileName = fileName;
        this.fileId = new FileId(userId, fileNumber);
    }

    //JPA expects a default constructor
    protected FileRecord() {
    }

    public long getUserId() {
        return this.fileId.getUserId();
    }

    public void setUserId(long userId) {
        this.fileId.setUserId(userId);
    }

    public String getFileNumber() {
        return this.fileId.getFileNumber();
    }

    public void setFileNumber(String fileNumber) {
        this.fileId.setFileNumber(fileNumber);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
