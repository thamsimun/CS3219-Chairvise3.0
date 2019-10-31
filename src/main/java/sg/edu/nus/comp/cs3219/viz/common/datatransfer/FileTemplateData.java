package sg.edu.nus.comp.cs3219.viz.common.datatransfer;

import sg.edu.nus.comp.cs3219.viz.common.entity.FileTemplate.TemplateMappingList;

public class FileTemplateData {

    private Long templateId;

    private String creatorIdentifier;

    private TemplateMappingList templateMappingList;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long id) {
        this.templateId = id;
    }

    public TemplateMappingList getTemplateMappingList() {
        return templateMappingList;
    }

    public void setTemplateMappingList(TemplateMappingList templateMappingList) {
        this.templateMappingList = templateMappingList;
    }
}
