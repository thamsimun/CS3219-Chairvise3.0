package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.PresentationData;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.entity.UserDetails;
import sg.edu.nus.comp.cs3219.viz.common.exception.EntityNotFoundException;
import sg.edu.nus.comp.cs3219.viz.storage.repository.PresentationRepository;
import sg.edu.nus.comp.cs3219.viz.storage.repository.UserDetailsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static sg.edu.nus.comp.cs3219.viz.common.util.Const.*;

@Component
public class PresentationLogic {

    private final PresentationRepository presentationRepository;
    private final UserDetailsRepository userDetailsRepository;

    public PresentationLogic(PresentationRepository presentationRepository, UserDetailsRepository userDetailsRepository) {
        this.presentationRepository = presentationRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public List<Presentation> findAllForUser(UserInfo userInfo) {
        return presentationRepository.findByUserDetailsUserId(userInfo.getUserId());
    }

    public Optional<Presentation> findById(Long id) {
        return presentationRepository.findById(id);
    }

    public boolean checkIfPresentationContainsFileNumber(int fileNumber, long userId) {
        List<Presentation> presentations = presentationRepository.findByUserDetailsUserId(userId);
        return presentations.stream().anyMatch(p -> p.getFileMappings().stream().anyMatch(f -> f.getFileNumber() == fileNumber));
    };

    public Presentation saveForUser(Presentation presentation, UserInfo userInfo) {
        Presentation newPresentation = new Presentation();
        newPresentation.setName(presentation.getName());
        newPresentation.setDescription(presentation.getDescription());
        UserDetails userDetails = getUserDetails(userInfo.getUserId());
        newPresentation.setUserDetails(userDetails);
        newPresentation.setCreatorIdentifier(userInfo.getUserEmail());
        newPresentation.setFileMappings(parseFileMappings(presentation.getFileMappings()));

        return presentationRepository.save(newPresentation);
    }

    private List<Presentation.FileMappings> parseFileMappings(List<Presentation.FileMappings> mappings) {
        List<Presentation.FileMappings> newList = new ArrayList<>();
        mappings.forEach(x -> {
            String toReplace = "";
            switch (x.getFileType()) {
                case FILE_TYPE_AUTHOR:
                    toReplace = "author_record";
                    break;
                case FILE_TYPE_REVIEW:
                    toReplace = "review_record";
                    break;
                case FILE_TYPE_SUBMISSION:
                    toReplace = "submission_record";
                    break;
            }
            x.setFileName(toReplace);
            newList.add(x);
        });
        return newList;
    }

    private UserDetails getUserDetails(long userId) throws EntityNotFoundException {
        Optional<UserDetails> details = userDetailsRepository.findByUserId(userId);
        if (!details.isPresent()) {
            throw new EntityNotFoundException(String.format("User %s", userId));
        }
        return details.get();
    }

    public Presentation updatePresentation(Presentation oldPresentation, Presentation newPresentation) {
        oldPresentation.setName(newPresentation.getName());
        oldPresentation.setDescription(newPresentation.getDescription());
        oldPresentation.setFileMappings(newPresentation.getFileMappings());
        return presentationRepository.save(oldPresentation);
    }

    public void deleteById(Long id) {
        presentationRepository.deleteById(id);
    }

    public PresentationData getPresentationData(Presentation p) {
        PresentationData data = new PresentationData();
        data.setCreatorIdentifier(p.getCreatorIdentifier());
        data.setId(p.getId());
        data.setName(p.getName());
        return data;
    }
}
