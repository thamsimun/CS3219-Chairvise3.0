package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import java.io.Serializable;
import java.util.Objects;

public class FilePrimaryKey implements Serializable {
    private String fileId;
    private String userId;

    public FilePrimaryKey(String userId, String fileId) {
        this.fileId = fileId;
        this.userId = userId;
    }

    public String getFile_id() {
        return fileId;
    }

    public void setFile_id(String fileId) {
        this.fileId = fileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilePrimaryKey that = (FilePrimaryKey) o;
        return fileId.equals(that.fileId) &&
                userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, userId);
    }
}
