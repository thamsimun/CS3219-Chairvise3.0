package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.AuthorRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.ReviewRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.SubmissionRecord;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.logic.RecordLogic;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RecordController extends BaseRestController {

    private GateKeeper gateKeeper;

    private RecordLogic recordLogic;

    public RecordController(GateKeeper gateKeeper, RecordLogic recordLogic) {
        this.gateKeeper = gateKeeper;
        this.recordLogic = recordLogic;
    }

    @PostMapping("/record/author")
    public ResponseEntity<?> importAuthorRecord(@RequestBody List<AuthorRecord> authorRecordList) throws URISyntaxException {
        UserInfo userInfo = gateKeeper.verifyLoginAccess();

        String fileName = getFileNameIfExist(authorRecordList.get(0));
        //if successfully gotten the file name, then remove the string from the list
        if (!fileName.equals("")) {
            authorRecordList.remove(0);
        }
        this.recordLogic.removeAndPersistAuthorRecordForDataSet(userInfo.getUserEmail(), authorRecordList);

        return ResponseEntity.created(new URI("/record/author")).build();
    }

    @PostMapping("/record/review")
    public ResponseEntity<?> importReviewRecord(@RequestBody List<ReviewRecord> reviewRecordList) throws URISyntaxException {
        UserInfo userInfo = gateKeeper.verifyLoginAccess();

        String fileName = getFileNameIfExist(reviewRecordList.get(0));
        //if successfully gotten the file name, then remove the string from the list
        if (!fileName.equals("")) {
            reviewRecordList.remove(0);
        }
        this.recordLogic.removeAndPersistReviewRecordForDataSet(userInfo.getUserEmail(), reviewRecordList);

        return ResponseEntity.created(new URI("/record/review")).build();
    }

    @PostMapping("/record/submission")
    public ResponseEntity<?> importSubmissionRecord(@RequestBody List<SubmissionRecord> submissionRecords) throws URISyntaxException {
        UserInfo userInfo = gateKeeper.verifyLoginAccess();

        String fileName = getFileNameIfExist(submissionRecords.get(0));
        //if successfully gotten the file name, then remove the string from the list
        if (!fileName.equals("")) {
            submissionRecords.remove(0);
        }
        this.recordLogic.removeAndPersistSubmissionRecordForDataSet(userInfo.getUserEmail(), submissionRecords);

        return ResponseEntity.created(new URI("/record/review")).build();
    }

    /**
     * Get the file name
     * @param r
     * @return file name as a string if exist, else returns empty string
     */
    private String getFileNameIfExist(Object r) {
        if (r instanceof String) {
           return (String) r;
        }
        return "";
    }
}
