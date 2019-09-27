package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.AuthorRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.FileId;

import java.util.List;

public interface AuthorRecordRepository extends JpaRepository<AuthorRecord, Long> {

    List<AuthorRecord> findAllByFileRecordFileIdUserIdEquals(long userId);

    //delete all by a user id
    void deleteAllByFileRecordFileIdUserIdEquals(long userId);

    //delete all by a specific file
    void deleteAllByFileRecordFileIdEquals(FileId fileId);

}
