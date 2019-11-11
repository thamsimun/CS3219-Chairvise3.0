package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
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

    public UserInfo createUserDetails(String userEmail, String userPassword) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserEmail(userEmail);
        userDetails.setUserPassword(userPassword);
        saveUserDetails(userDetails);
        return convertUserDetailsToUserInfo(getUserDetails(userEmail));
    }

    private UserInfo convertUserDetailsToUserInfo(UserDetails details) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(details.getUserId());
        userInfo.setUserPassword(details.getUserPassword());
        userInfo.setUserEmail(details.getUserEmail());
        return userInfo;
    }

    public void saveUserDetails(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    //internal use
    private UserDetails getUserDetails(String email) throws EntityNotFoundException {
        Optional<UserDetails> details = userDetailsRepository.findByUserEmail(email);
        if (!details.isPresent()) {
            throw new EntityNotFoundException(String.format("User %s", email));
        }
        return details.get();
    }

    //internal use
    private UserDetails getUserDetails(long userId) throws EntityNotFoundException {
        Optional<UserDetails> details = userDetailsRepository.findByUserId(userId);
        if (!details.isPresent()) {
            throw new EntityNotFoundException(String.format("User %s", userId));
        }
        return details.get();
    }

    public UserInfo getUserInfo(String email) {
        UserDetails details = getUserDetails(email);
        return convertUserDetailsToUserInfo(details);
    }
}
