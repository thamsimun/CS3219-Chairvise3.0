package sg.edu.nus.comp.cs3219.viz.logic.testutil;

import sg.edu.nus.comp.cs3219.viz.common.entity.record.FileId;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.FileRecord;

/**
 * Utility class which contains convenient methods to generate dummy file records
 */
public class FileRecordUtil {

    public static FileRecord generateFileRecordWithUserIdAndFileId(long userId, int fileNumber, String fileName) {
        FileRecord record = new FileRecord();
        record.setFileName(fileName);
        FileId fileId = new FileId();
        fileId.setFileNumber(fileNumber);
        fileId.setUserId(userId);
        record.setFileId(fileId);
        return record;
    }

    public static FileId generateFileIdWithUserIdAndFileNumber(long userId, int fileNumber) {
        FileId fileId = new FileId();
        fileId.setUserId(userId);
        fileId.setFileNumber(fileNumber);
        return fileId;
    }

}
