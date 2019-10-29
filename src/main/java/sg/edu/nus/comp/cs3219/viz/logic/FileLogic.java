package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.FileInfo;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.FileTemplateData;
import sg.edu.nus.comp.cs3219.viz.common.entity.FileTemplate;
import sg.edu.nus.comp.cs3219.viz.common.entity.FileTemplate.TemplateMappingList;
import sg.edu.nus.comp.cs3219.viz.common.entity.UserDetails;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.FileId;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.FileRecord;
import sg.edu.nus.comp.cs3219.viz.common.exception.EntityNotFoundException;
import sg.edu.nus.comp.cs3219.viz.storage.repository.FileRecordRepository;
import sg.edu.nus.comp.cs3219.viz.storage.repository.FileTemplateRepository;
import sg.edu.nus.comp.cs3219.viz.storage.repository.UserDetailsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FileLogic {
    private FileRecordRepository fileRecordRepository;
    private UserDetailsRepository userDetailsRepository;
    private FileTemplateRepository fileTemplateRepository;

    public FileLogic(FileRecordRepository fileRecordRepository, UserDetailsRepository userDetailsRepository, FileTemplateRepository fileTemplateRepository) {
        this.fileRecordRepository = fileRecordRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.fileTemplateRepository = fileTemplateRepository;
    }

    public FileRecord createFileRecord(long userId, String fileName, String fileType) {

        UserDetails userDetails = retrieveUserDetailsUsingUserId(userId);
        int fileNumber = findNextUnusedFileId(userDetails.getUserId());
        FileRecord fileRecord = new FileRecord();
        fileRecord.setFileName(fileName);
        fileRecord.setUserDetails(userDetails);
        fileRecord.setFileNumber(fileNumber);
        fileRecord.setFileType(fileType);

        return fileRecord;
    }

    public List<FileInfo> deleteFileRecord(long userId, FileInfo fileInfo) {
        FileId fileId = new FileId();
        fileId.setFileNumber(fileInfo.getFileNumber());
        fileId.setUserId(userId);
        return fileRecordRepository.deleteByFileIdEquals(fileId).stream().map(x -> {
            FileInfo info = new FileInfo();
            info.setFileNumber(x.getFileNumber());
            info.setFileName(x.getFileName());
            return info;
        }).collect(Collectors.toList());
    }

    public List<FileInfo> retrieveFileRecordsUsingUserId(long userId) {
        List<FileRecord> fileRecords = fileRecordRepository.findAllByFileIdUserIdEquals(userId);
        List<FileInfo> fileInfo = new ArrayList<>();
        fileRecords.forEach(x -> fileInfo.add(createFileInfoFromFileRecord(x)));
        return fileInfo;
    }

    private FileInfo createFileInfoFromFileRecord(FileRecord fileRecord) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(fileRecord.getFileName());
        fileInfo.setFileNumber(fileRecord.getFileNumber());
        fileInfo.setFileType(fileRecord.getFileType());
        return fileInfo;
    }

    public void saveFileRecord(FileRecord fileRecord) {
        fileRecordRepository.save(fileRecord);
    }

    public FileRecord createAndSaveFileRecord(long userId, String fileName, String fileType) {
        FileRecord fileRecord = createFileRecord(userId, fileName, fileType);
        saveFileRecord(fileRecord);
        return fileRecord;
    }

    private UserDetails retrieveUserDetailsUsingUserId(long userId) throws EntityNotFoundException {
        Optional<UserDetails> profile = userDetailsRepository.findByUserId(userId);
        if (!profile.isPresent()) {
            throw new EntityNotFoundException("User " + userId);
        }
        return profile.get();
    }

    public void saveTemplate(TemplateMappingList templateMappingList, long userId) {
        FileTemplate template = new FileTemplate();
        template.setCreatorIdentifier(String.valueOf(userId));
        template.setTemplateMappingList(templateMappingList);
        fileTemplateRepository.save(template);
    }

    public List<FileTemplateData> getTemplatesForUser(long userId) {
        List<FileTemplate> templates = fileTemplateRepository.findAllByCreatorIdentifierEquals(String.valueOf(userId));
        List<FileTemplateData> templateData = new ArrayList<>();
        templates.forEach(x -> templateData.add(parseFileTemplateForDataTransfer(x)));
        return templateData;
    }

    public List<FileTemplateData> deleteTemplateForUser(long templateId, long userId) {
        Optional<FileTemplate> template = fileTemplateRepository.findByCreatorIdentifierAndIdEquals(String.valueOf(userId), templateId);
        if (!template.isPresent()) {
            throw new EntityNotFoundException(String.format("File template %s", templateId));
        }
        fileTemplateRepository.deleteByCreatorIdentifierAndIdEquals(String.valueOf(userId), templateId);
        return getTemplatesForUser(userId);
    }

    private FileTemplateData parseFileTemplateForDataTransfer(FileTemplate fileTemplate) {
        FileTemplateData data = new FileTemplateData();
        data.setTemplateId(fileTemplate.getId());
        data.setTemplateMappingList(fileTemplate.getTemplateMappingList());
        return data;
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
