package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationSection;
import sg.edu.nus.comp.cs3219.viz.common.entity.UserDetails;
import sg.edu.nus.comp.cs3219.viz.common.exception.UserNotFoundException;
import sg.edu.nus.comp.cs3219.viz.storage.repository.PresentationRepository;
import sg.edu.nus.comp.cs3219.viz.storage.repository.UserDetailsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //TODO Fix hard coding
    private List<Presentation.FileMappings> parseFileMappings(List<Presentation.FileMappings> mappings) {
        List<Presentation.FileMappings> newList = new ArrayList<>();
        mappings.forEach(x -> {
            String toReplace = "";
            if (x.getFileName().toLowerCase().contains("author")) {
                toReplace = "author_record";
            } else if (x.getFileName().toLowerCase().contains("review")) {
                toReplace = "review_record";
            } else if (x.getFileName().toLowerCase().contains("submission")) {
                toReplace = "submission_record";
            }
            x.setFileName(toReplace);
            newList.add(x);
        });
        return newList;
    }

    private UserDetails getUserDetails(long userId) throws UserNotFoundException {
        Optional<UserDetails> details = userDetailsRepository.findByUserId(userId);
        if (!details.isPresent()) {
            throw new UserNotFoundException(userId);
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
}
