package sg.edu.nus.comp.cs3219.viz.ui.controller.api;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.FileInfo;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.logic.FileLogic;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.logic.RecordLogic;

import java.util.List;

@RestController
public class FileController extends BaseRestController {

    private GateKeeper gateKeeper;

    private FileLogic fileLogic;

    private RecordLogic recordLogic;

    public FileController(GateKeeper gateKeeper, FileLogic fileLogic, RecordLogic recordLogic) {
        this.gateKeeper = gateKeeper;
        this.fileLogic = fileLogic;
        this.recordLogic = recordLogic;
    }

    @GetMapping("/file")
    public List<FileInfo> getFileRecords() {
        UserInfo user = gateKeeper.verifyLoginAccess();
        return fileLogic.retrieveFileRecordsUsingUserId(user.getUserId());
    }

    @Transactional
    @PostMapping("/file")
    public List<FileInfo> deleteFileRecord(@RequestBody FileInfo fileInfo) {
        UserInfo user = gateKeeper.verifyLoginAccess();
        recordLogic.removeRecordForUserIdFileId(user.getUserId(), fileInfo);
        fileLogic.deleteFileRecord(user.getUserId(), fileInfo);
        return getFileRecords();
    }
}
