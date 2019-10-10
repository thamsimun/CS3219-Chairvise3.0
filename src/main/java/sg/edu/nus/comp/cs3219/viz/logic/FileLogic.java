package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.entity.UserDetails;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.FileRecord;
import sg.edu.nus.comp.cs3219.viz.common.exception.UserNotFoundException;
import sg.edu.nus.comp.cs3219.viz.storage.repository.FileRecordRepository;
import sg.edu.nus.comp.cs3219.viz.storage.repository.UserDetailsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class FileLogic {
    private FileRecordRepository fileRecordRepository;
    private UserDetailsRepository userDetailsRepository;

    public FileLogic(FileRecordRepository fileRecordRepository, UserDetailsRepository userDetailsRepository) {
        this.fileRecordRepository = fileRecordRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public FileRecord createFileRecord(long userId, String fileName) {

        UserDetails userDetails = retrieveUserDetailsUsingUserId(userId);
        int fileNumber = findNextUnusedFileId(userDetails.getUserId());
        FileRecord fileRecord = new FileRecord();
        fileRecord.setFileName(fileName);
        fileRecord.setUserDetails(userDetails);
        fileRecord.setFileNumber(fileNumber);

        return fileRecord;
    }

    public void saveFileRecord(FileRecord fileRecord) {
        fileRecordRepository.save(fileRecord);
    }

    public FileRecord createAndSaveFileRecord(long userId, String fileName) {
        FileRecord fileRecord = createFileRecord(userId, fileName);
        saveFileRecord(fileRecord);
        return fileRecord;
    }

    private UserDetails retrieveUserDetailsUsingUserId(long userId) throws UserNotFoundException {
        Optional<UserDetails> profile = userDetailsRepository.findByUserId(userId);
        if (!profile.isPresent()) {
            throw new UserNotFoundException(userId);
        }
        return profile.get();
    }

    private int findNextUnusedFileId(long userId) {
        List<FileRecord> fileRecords = fileRecordRepository.findAllByFileIdUserIdEquals(userId);
        //if no records, means first file
        if (fileRecords.size() == 0) {
            return 0;
        }
        //return the first integer not used by file ids
        List<Integer> fileIds = new ArrayList<>();
        fileRecords.forEach(x -> {
            fileIds.add(x.getFileNumber());
        });
        if (fileIds.size() > 0) {
            Collections.sort(fileIds);
        }
        return findSmallestUnusedId(fileIds);
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
            if ((fileIds.get(i + 1) - fileIds.get(i)) > 1) {
                return fileIds.get(i) + 1;
            }
        }
        return fileIds.get(fileIds.size() - 1) + 1;
    }


}
