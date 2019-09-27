package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.FileId;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.ReviewRecord;

import java.util.List;

public interface ReviewRecordRepository extends JpaRepository<ReviewRecord, Long> {

    List<ReviewRecord> findAllByFileRecordFileIdUserIdEquals(long userId);

    //delete all records by a user id
    void deleteAllByFileRecordFileIdUserIdEquals(long userId);

    //delete all records by specific file
    void deleteAllByFileRecordFileIdEquals(FileId fileId);
}
