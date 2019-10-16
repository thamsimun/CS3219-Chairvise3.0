package sg.edu.nus.comp.cs3219.viz.logic;

import com.google.appengine.api.users.User;
import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.entity.UserDetails;
import sg.edu.nus.comp.cs3219.viz.storage.repository.UserDetailsRepository;

/**
 * Handles the sign up logic for signing up a new user to the database
 */
@Component
public class SignUpLogic {

    private UserDetailsRepository userDetailsRepository;

    public SignUpLogic(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    /**
     * Creates a new user profile from the user info and saves into {@code userProfileRepository}.
     * @param user
     * @return userProfile of the new user
     */
    public UserDetails createNewUser(User user) {
        UserDetails newUser = new UserDetails();
        newUser.setUserEmail(user.getEmail());
        newUser.setUserName(user.getNickname());
        return userDetailsRepository.save(newUser);
    }

}
