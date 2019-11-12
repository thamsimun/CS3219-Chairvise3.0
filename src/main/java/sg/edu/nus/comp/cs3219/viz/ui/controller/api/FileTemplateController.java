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
    public TemplateMappingList importTemplateMapping(@RequestBody TemplateMappingList templateMappingList,
                                                     @CookieValue(value = "userEmail") String email,
                                                     @CookieValue(value = "userPassword") String password) {
        UserInfo user = gateKeeper.verifyLoginAccess(email, password);
        fileTemplateLogic.saveTemplate(templateMappingList, user.getUserId());
        return templateMappingList;
    }

    @GetMapping("/file/mapping")
    public List<FileTemplateData> getTemplateMapping(@CookieValue(value = "userEmail") String email, @CookieValue(value = "userPassword") String password) {
        UserInfo user = gateKeeper.verifyLoginAccess(email, password);
        return fileTemplateLogic.getTemplatesForUser(user.getUserId());
    }

    @DeleteMapping("/file/mapping/{templateId}")
    public List<FileTemplateData> deleteTemplateMapping(@PathVariable Long templateId,
                                                        @CookieValue(value = "userEmail") String email,
                                                        @CookieValue(value = "userPassword") String password) {
        UserInfo user = gateKeeper.verifyLoginAccess(email, password);
        return fileTemplateLogic.deleteTemplateForUser(templateId, user.getUserId());
    }

}