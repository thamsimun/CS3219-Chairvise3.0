package sg.edu.nus.comp.cs3219.viz.logic;

import com.google.appengine.api.users.User;
import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.entity.UserProfile;
import sg.edu.nus.comp.cs3219.viz.storage.repository.UserProfileRepository;

/**
 * Handles the sign up logic for signing up a new user to the database
 */
@Component
public class SignUpLogic {

    private UserProfileRepository userProfileRepository;

    public SignUpLogic(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    /**
     * Creates a new user profile from the user info and saves into {@code userProfileRepository}.
     * @param user
     * @return userProfile of the new user
     */
    public UserProfile createNewUser(User user) {
        UserProfile newUser = new UserProfile();
        newUser.setUserEmail(user.getEmail());
        newUser.setUserName(user.getNickname());
        return userProfileRepository.save(newUser);
    }

}
