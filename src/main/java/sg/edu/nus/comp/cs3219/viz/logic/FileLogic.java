package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.FileRecord;
import sg.edu.nus.comp.cs3219.viz.storage.repository.FileRecordRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class FileLogic {
    private FileRecordRepository fileRecordRepository;

    public FileLogic(FileRecordRepository fileRecordRepository) {
        this.fileRecordRepository = fileRecordRepository;
    }

    public void addNewFileRecordForUser(UUID userId, String fileName) {
        String fileId = findNextUnusedFileId(userId);
        fileRecordRepository.save(new FileRecord(userId, fileId, fileName));
    }

    private String findNextUnusedFileId(UUID uuid) {
        List<FileRecord> fileRecords = fileRecordRepository.findAllByUserIdEquals(uuid);
        //return the first integer not used by file ids
        List<Integer> fileIds = new ArrayList<>();
        fileRecords.forEach(x -> {
            fileIds.add(Integer.parseInt(x.getFileId()));
        });
        Collections.sort(fileIds);
        return String.valueOf(findSmallestUnusedId(fileIds));
    }

    /**
     * Finds the smallest integer which is unused for a file id. Note that this uses 0-based indexing
     * @param fileIds
     * @return smallest unused id
     */
    private int findSmallestUnusedId(List<Integer> fileIds) {
        //base cases
        if (fileIds.size() == 0) {
            return 0;
        }
        if (fileIds.size() == 1) {
            if (fileIds.get(0) == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        //if the difference between 2 consecutive fileIds is larger than 1, then there is a empty space
        for (int i = 0; i < fileIds.size() - 1; i++) {
            if (fileIds.get(i + 1) - fileIds.get(i) > 1) {
                return fileIds.get(i) + 1;
            }
        }
        return fileIds.get(fileIds.size() - 1) + 1;
    }


}
