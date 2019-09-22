package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Exportable(name = "File Record", nameInDB = "file_record")
@Entity
@IdClass(FilePrimaryKey.class)
public class FileRecord implements Serializable {

    @Id
    private String userId;

    @Id
    private String fileId;

    public FileRecord(String userId, String fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
