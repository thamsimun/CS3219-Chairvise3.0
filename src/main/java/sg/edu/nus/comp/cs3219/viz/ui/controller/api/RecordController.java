package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.*;
import sg.edu.nus.comp.cs3219.viz.logic.FileLogic;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.logic.RecordLogic;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static sg.edu.nus.comp.cs3219.viz.common.util.Const.*;

@RestController
public class RecordController extends BaseRestController {

    private GateKeeper gateKeeper;

    private RecordLogic recordLogic;

    private FileLogic fileLogic;

    public RecordController(GateKeeper gateKeeper, RecordLogic recordLogic, FileLogic fileLogic) {
        this.gateKeeper = gateKeeper;
        this.recordLogic = recordLogic;
        this.fileLogic = fileLogic;
    }


    @PostMapping(value = "/record/author", consumes = "application/json")
    public ResponseEntity<?> importAuthorRecord(@RequestBody FileWithAuthorRecordData fileWithAuthorData,
                                                @CookieValue(value = "userEmail") String email,
                                                @CookieValue(value = "userPassword") String password) throws URISyntaxException {

        UserInfo userInfo = gateKeeper.verifyLoginAccess(email, password);
        List<AuthorRecord> authorRecordList = fileWithAuthorData.records;
        String fileName = getFileNameIfExist(fileWithAuthorData.fileName);
        FileRecord fileRecord = this.fileLogic.createAndSaveFileRecord(userInfo.getUserId(), fileName, FILE_TYPE_AUTHOR);

        this.recordLogic.removeAndPersistAuthorRecordForUserId(fileRecord, authorRecordList);

        return ResponseEntity.created(new URI("/record/author")).build();
    }

    @PostMapping(value = "/record/review", consumes = "application/json")
    public ResponseEntity<?> importReviewRecord(@RequestBody FileWithReviewRecordData fileWithReviewRecordData,
                                                @CookieValue(value = "userEmail") String email,
                                                @CookieValue(value = "userPassword") String password) throws URISyntaxException {

        UserInfo userInfo = gateKeeper.verifyLoginAccess(email, password);
        List<ReviewRecord> reviewRecordList = fileWithReviewRecordData.records;
        String fileName = getFileNameIfExist(fileWithReviewRecordData.fileName);
        FileRecord fileRecord = this.fileLogic.createAndSaveFileRecord(userInfo.getUserId(), fileName, FILE_TYPE_REVIEW);

        this.recordLogic.removeAndPersistReviewRecordForUserId(fileRecord, reviewRecordList);

        return ResponseEntity.created(new URI("/record/review")).build();
    }

    @PostMapping(value = "/record/submission", consumes = "application/json")
    public ResponseEntity<?> importSubmissionRecord(
            @RequestBody FileWithSubmissionRecordData fileWithSubmissionRecordData,
            @CookieValue(value = "userEmail") String email,
            @CookieValue(value = "userPassword") String password) throws URISyntaxException {

        UserInfo userInfo = gateKeeper.verifyLoginAccess(email, password);
        List<SubmissionRecord> submissionRecordList = fileWithSubmissionRecordData.records;
        String fileName = getFileNameIfExist(fileWithSubmissionRecordData.fileName);
        FileRecord fileRecord = this.fileLogic.createAndSaveFileRecord(userInfo.getUserId(), fileName, FILE_TYPE_SUBMISSION);

        this.recordLogic.removeAndPersistSubmissionRecordForUserId(fileRecord, submissionRecordList);

        return ResponseEntity.created(new URI("/record/review")).build();
    }

    /**
     * Get the file name
     * @param string file name
     * @return file name as a string if exist, else returns default name
     */
    private String getFileNameIfExist(String string) {
        if (string != null && !string.equals("")) {
           return (String) string;
        }
        return "untitled";
    }

    //wrapper classes to parse file data
    public static class FileWithAuthorRecordData {
        @JsonProperty("fileName")
        public String fileName;
        @JsonProperty("records")
        public List<AuthorRecord> records;
    }

    public static class FileWithReviewRecordData {
        @JsonProperty("fileName")
        public String fileName;
        @JsonProperty("records")
        public List<ReviewRecord> records;
    }

    public static class FileWithSubmissionRecordData {
        @JsonProperty("fileName")
        public String fileName;
        @JsonProperty("records")
        public List<SubmissionRecord> records;
    }
}
