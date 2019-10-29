package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.FileTemplateData;
import sg.edu.nus.comp.cs3219.viz.common.entity.FileTemplate;
import sg.edu.nus.comp.cs3219.viz.common.exception.EntityNotFoundException;
import sg.edu.nus.comp.cs3219.viz.storage.repository.FileTemplateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class FileTemplateLogic {

    private FileTemplateRepository fileTemplateRepository;

    public FileTemplateLogic(FileTemplateRepository fileTemplateRepository) {
        this.fileTemplateRepository = fileTemplateRepository;
    }

    public void saveTemplate(FileTemplate.TemplateMappingList templateMappingList, long userId) {
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

}
