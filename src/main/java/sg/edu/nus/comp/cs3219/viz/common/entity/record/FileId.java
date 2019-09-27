package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FileId implements Serializable {

    @Column(name = "file_number")
    private int fileNumber;

    @Column(name = "user_id")
    private long userId;

    public int getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileId that = (FileId) o;
        return fileNumber == that.fileNumber &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileNumber, userId);
    }
}
