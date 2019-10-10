package sg.edu.nus.comp.cs3219.viz.ui.controller.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.FileInfo;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.logic.FileLogic;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;

import java.util.List;

@RestController
public class FileController {

    private GateKeeper gateKeeper;

    private FileLogic fileLogic;

    public FileController(GateKeeper gateKeeper, FileLogic fileLogic) {
        this.gateKeeper = gateKeeper;
        this.fileLogic = fileLogic;
    }

    @GetMapping("/file")
    public List<FileInfo> getFileRecords() {
        UserInfo user = gateKeeper.verifyLoginAccess();
        return fileLogic.retrieveFileRecordsUsingUserId(user.getUserId());
    }
}
