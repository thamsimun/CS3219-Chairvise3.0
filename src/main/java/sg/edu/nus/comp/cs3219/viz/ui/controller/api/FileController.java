package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.FileInfo;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.exception.ForeignKeyViolationException;
import sg.edu.nus.comp.cs3219.viz.logic.FileLogic;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.logic.PresentationLogic;
import sg.edu.nus.comp.cs3219.viz.logic.RecordLogic;

import java.util.List;

@RestController
public class FileController extends BaseRestController {

    private GateKeeper gateKeeper;

    private FileLogic fileLogic;

    private RecordLogic recordLogic;

    private PresentationLogic presentationLogic;

    public FileController(GateKeeper gateKeeper, FileLogic fileLogic, RecordLogic recordLogic, PresentationLogic presentationLogic) {
        this.gateKeeper = gateKeeper;
        this.fileLogic = fileLogic;
        this.recordLogic = recordLogic;
        this.presentationLogic = presentationLogic;
    }

    @GetMapping("/file")
    public List<FileInfo> getFileRecords(@CookieValue(value = "userEmail") String email, @CookieValue(value = "userPassword") String password) {
        UserInfo user = gateKeeper.verifyLoginAccess(email, password);
        return fileLogic.retrieveFileRecordsUsingUserId(user.getUserId());
    }

    @Transactional
    @PostMapping("/file")
    public List<FileInfo> deleteFileRecord(@RequestBody FileInfo fileInfo,
                                           @CookieValue(value = "userEmail") String email,
                                           @CookieValue(value = "userPassword") String password) throws ForeignKeyViolationException {
        UserInfo user = gateKeeper.verifyLoginAccess(email, password);
        if (presentationLogic.checkIfPresentationContainsFileNumber(fileInfo.getFileNumber(), user.getUserId())) {
            throw new ForeignKeyViolationException("file_record", "presentation");
        };
        recordLogic.removeRecordForUserIdFileId(user.getUserId(), fileInfo);
        fileLogic.deleteFileRecord(user.getUserId(), fileInfo);
        return getFileRecords(email, password);
    }

}
