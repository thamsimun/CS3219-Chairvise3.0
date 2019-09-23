package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.UUID;

@Exportable(name = "File Record", nameInDB = "file_record")
@Entity
@IdClass(FilePrimaryKey.class)
public class FileRecord implements Serializable {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "file_id")
    private String fileId;

    @Column(name = "file_name")
    private String fileName;

    public FileRecord(UUID userId, String fileId, String fileName) {
        this.userId = userId;
        this.fileId = fileId;
        this.fileName = fileName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
