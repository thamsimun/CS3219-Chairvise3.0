package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.web.bind.annotation.*;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.FileTemplateData;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.FileTemplate.TemplateMappingList;
import sg.edu.nus.comp.cs3219.viz.logic.*;

import java.util.List;

@RestController
public class FileTemplateController extends BaseRestController {

    private GateKeeper gateKeeper;

    private FileTemplateLogic fileTemplateLogic;

    public FileTemplateController(GateKeeper gateKeeper, FileTemplateLogic fileTemplateLogic) {
        this.gateKeeper = gateKeeper;
        this.fileTemplateLogic = fileTemplateLogic;
    }

    @PostMapping("/file/mapping")
    public TemplateMappingList importTemplateMapping(@RequestBody TemplateMappingList templateMappingList) {
        UserInfo user = gateKeeper.verifyLoginAccess();
        fileTemplateLogic.saveTemplate(templateMappingList, user.getUserId());
        return templateMappingList;
    }

    @GetMapping("/file/mapping")
    public List<FileTemplateData> getTemplateMapping() {
        UserInfo user = gateKeeper.verifyLoginAccess();
        return fileTemplateLogic.getTemplatesForUser(user.getUserId());
    }

    @DeleteMapping("/file/mapping")
    public List<FileTemplateData> deleteTemplateMapping(long templateId) {
        UserInfo user = gateKeeper.verifyLoginAccess();
        return fileTemplateLogic.deleteTemplateForUser(templateId, user.getUserId());
    }

}