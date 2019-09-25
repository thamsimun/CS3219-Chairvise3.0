package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.FileId;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.FileRecord;

import java.util.List;

public interface FileRecordRepository extends JpaRepository<FileRecord, FileId> {

    //List<FileRecord> findByFileIdEquals(FileId fileId);

    List<FileRecord> findAllByFileIdUserIdEquals(long userId);

    void deleteByFileIdEquals(FileId fileId);
}
