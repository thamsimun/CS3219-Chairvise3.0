package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class FileId implements Serializable {
    private String fileId;
    private UUID userId;

    public FileId(UUID userId, String fileId) {
        this.fileId = fileId;
        this.userId = userId;
    }

    //JPA expects a default constructor
    protected FileId() {
    }

    public String getFile_id() {
        return fileId;
    }

    public void setFile_id(String fileId) {
        this.fileId = fileId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileId that = (FileId) o;
        return fileId.equals(that.fileId) &&
                userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, userId);
    }
}
