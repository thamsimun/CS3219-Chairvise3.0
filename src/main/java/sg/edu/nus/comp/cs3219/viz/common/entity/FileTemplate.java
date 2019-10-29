package sg.edu.nus.comp.cs3219.viz.common.entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.annotations.GenericGenerator;
import sg.edu.nus.comp.cs3219.viz.logic.AnalysisLogic;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Entity
public class FileTemplate {

    private static final Logger log = Logger.getLogger(AnalysisLogic.class.getSimpleName());

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "sg.edu.nus.comp.cs3219.viz.common.entity.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String creatorIdentifier;

    @Column(columnDefinition = "TEXT")
    private String templateMappingList;

    public void setTemplateMappingList(TemplateMappingList template) {
        try {
            this.templateMappingList = objectMapper.writeValueAsString(template);
        } catch (JsonProcessingException e) {
            log.severe(e.getMessage());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatorIdentifier() {
        return creatorIdentifier;
    }

    public void setCreatorIdentifier(String creatorIdentifier) {
        this.creatorIdentifier = creatorIdentifier;
    }

    public static class TemplateMappingList {
        private List<String> transformations;
        private List<List<String>> mappingList;

        public List<String> getTransformations() {
            return transformations;
        }

        public void setTransformations(List<String> transformations) {
            this.transformations = transformations;
        }

        public List<List<String>> getMappingList() {
            return mappingList;
        }

        public void setMappingList(List<List<String>> mappingList) {
            this.mappingList = mappingList;
        }
    }
}