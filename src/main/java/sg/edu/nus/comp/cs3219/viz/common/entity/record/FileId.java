package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FileId implements Serializable {

    @Column(name = "file_number")
    private String fileNumber;

    @Column(name = "user_id")
    private long userId;
/*
    public FileId(long userId, String fileNumber) {
        this.userId = userId;
        this.fileNumber = fileNumber;
    }

    //JPA expects a default constructor
    protected FileId() {
    }*/

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
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
        return fileNumber.equals(that.fileNumber) &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileNumber, userId);
    }
}
