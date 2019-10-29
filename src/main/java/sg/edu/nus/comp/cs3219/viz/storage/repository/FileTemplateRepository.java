package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.entity.FileTemplate;

public interface FileTemplateRepository extends JpaRepository<FileTemplate, Long> {

    FileTemplate findByIdEquals(long id);

    FileTemplate deleteByIdEquals(long id);
}
