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
public class Presentation {

    private static final Logger log = Logger.getLogger(AnalysisLogic.class.getSimpleName());

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "sg.edu.nus.comp.cs3219.viz.common.entity.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserDetails userDetails;

    private String creatorIdentifier;

    @Column(columnDefinition = "TEXT")
    private String fileMappings;

    public List<FileMappings> getFileMappings() {
        try {
            return objectMapper.readValue(fileMappings, new TypeReference<List<FileMappings>>() {
            });
        } catch (IOException e) {
            log.severe(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void setFileMappings(List<FileMappings> fileMappings) {
        try {
            this.fileMappings = objectMapper.writeValueAsString(fileMappings);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUserId() {
        return this.userDetails.getUserId();
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getCreatorIdentifier() {
        return creatorIdentifier;
    }

    public void setCreatorIdentifier(String creatorIdentifier) {
        this.creatorIdentifier = creatorIdentifier;
    }

    public static class FileMappings {
        private String fileName;
        private int fileNumber;
        private String fileType;

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String name) {
            this.fileName = name;
        }

        public int getFileNumber() {
            return fileNumber;
        }

        public void setFileNumber(int fileNumber) {
            this.fileNumber = fileNumber;
        }
    }
}
