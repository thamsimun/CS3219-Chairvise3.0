package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.FilePrimaryKey;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.FileRecord;

import java.util.List;
import java.util.UUID;

public interface FileRecordRepository extends JpaRepository<FileRecord, FilePrimaryKey> {

    List<FileRecord> findByFilePrimaryKeyEquals(FilePrimaryKey filePrimaryKey);

    List<FileRecord> findAllByUserIdEquals(UUID userId);

    void deleteByFilePrimaryKeyEquals(FilePrimaryKey filePrimaryKey);
}
