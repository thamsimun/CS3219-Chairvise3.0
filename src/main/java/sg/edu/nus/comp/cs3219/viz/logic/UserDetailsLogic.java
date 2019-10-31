package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.entity.UserDetails;
import sg.edu.nus.comp.cs3219.viz.common.exception.EntityNotFoundException;
import sg.edu.nus.comp.cs3219.viz.storage.repository.UserDetailsRepository;

import java.util.Optional;

@Component
public class UserDetailsLogic {
    private UserDetailsRepository userDetailsRepository;

    public UserDetailsLogic(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public UserDetails createUserDetails(String userName, String userEmail) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserEmail(userEmail);
        userDetails.setUserName(userName);
        return userDetails;
    }

    public void saveUserDetails(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    public UserDetails createAndSaveUserDetails(String userName, String userEmail) {
        UserDetails userDetails = createUserDetails(userName, userEmail);
        saveUserDetails(userDetails);
        return userDetails;
    }

    private UserDetails getUserDetails(long userId) throws EntityNotFoundException {
        Optional<UserDetails> details = userDetailsRepository.findByUserId(userId);
        if (!details.isPresent()) {
            throw new EntityNotFoundException(String.format("User %s", userId));
        }
        return details.get();
    }
}
