package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.entity.FileTemplate;

import java.util.List;
import java.util.Optional;

public interface FileTemplateRepository extends JpaRepository<FileTemplate, Long> {

    FileTemplate findByIdEquals(long id);

    FileTemplate deleteByIdEquals(long id);

    void deleteByCreatorIdentifierAndIdEquals(String creatorIdentifier, long id);

    Optional<FileTemplate> findByCreatorIdentifierAndIdEquals(String creatorIdentifier, long id);

    List<FileTemplate> findAllByCreatorIdentifierEquals(String identifier);
}
