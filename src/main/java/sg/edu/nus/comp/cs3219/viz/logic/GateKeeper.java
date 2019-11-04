package sg.edu.nus.comp.cs3219.viz.logic;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AccessLevel;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.exception.EntityNotFoundException;
import sg.edu.nus.comp.cs3219.viz.common.exception.UnauthorisedException;
import sg.edu.nus.comp.cs3219.viz.common.util.Const;
import sg.edu.nus.comp.cs3219.viz.storage.repository.PresentationAccessControlRepository;

import java.util.Optional;

@Component
public class GateKeeper {

    private PresentationAccessControlRepository presentationAccessControlRepository;
    private UserDetailsLogic userDetailsLogic;

    public GateKeeper(PresentationAccessControlRepository presentationAccessControlRepository,
                      UserDetailsLogic userDetailsLogic) {
        this.presentationAccessControlRepository = presentationAccessControlRepository;
        this.userDetailsLogic = userDetailsLogic;
    }

    private static UserService userService = UserServiceFactory.getUserService();

    public Optional<UserInfo> getCurrentLoginUser(String email, String password) {
        User user = userService.getCurrentUser();

        if (user == null && email.equals("")) {
            return Optional.empty();
        }

        UserInfo userInfo = new UserInfo();

        //if is a google api user
        if (user != null) {
            userInfo = handleGoogleUsers(user.getEmail());
        } else {
            userInfo = handleNormalUsers(email, password);
        }
        return Optional.of(userInfo);
    }

    public UserInfo handleGoogleUsers(String email) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserEmail(email);
        userInfo.setUserPassword("${PLACEHOLDER_PASSWORD}");
        try {
            //see if user is registered
            return userDetailsLogic.getUserInfo(email);
        } catch (EntityNotFoundException e) {
            //register user
            return createNewUser(email, userInfo.getUserPassword());
        }
    }

    public UserInfo handleNormalUsers(String email, String password) {
        //if someone tries to log in with the google api password
        if (password.equals("${PLACEHOLDER_PASSWORD}")) {
            throw new UnauthorisedException();
        }
        //check if user email and password correct
        if (!verifyUserCredentials(email, password)) {
            throw new UnauthorisedException();
        };
        return userDetailsLogic.getUserInfo(email);
    }

    public String getLoginUrl(String redirectPage) {
        User user = userService.getCurrentUser();

        if (user == null) {
            return userService.createLoginURL(redirectPage);
        }
        return redirectPage;
    }

    public String getLogoutUrl(String redirectPage) {
        return userService.createLogoutURL(redirectPage);
    }

    public UserInfo verifyLoginAccess(String email, String password) {
        return getCurrentLoginUser(email, password).orElseThrow(UnauthorisedException::new);
    }

    public void verifyDeletionAccessForPresentation(Presentation presentation, String email, String password) {
        if (presentation == null) {
            throw new UnauthorisedException();
        }

        UserInfo currentUser = getCurrentLoginUser(email, password)
                .orElseThrow(UnauthorisedException::new);

        if (!(currentUser.getUserId() == presentation.getUserId())) {
            throw new UnauthorisedException();
        }
    }

    public void verifyAccessForPresentation(Presentation presentation, AccessLevel accessLevel, String email, String password) {
        if (presentation == null) {
            throw new UnauthorisedException();
        }

        // check public access
        if (presentationAccessControlRepository.existsByPresentationAndUserIdentifierEqualsAndAccessLevelEquals(presentation, Const.SpecialIdentifier.PUBLIC, accessLevel)) {
            return;
        }
        // can_write means can_read
        if (accessLevel == AccessLevel.CAN_READ &&
                presentationAccessControlRepository.existsByPresentationAndUserIdentifierEqualsAndAccessLevelEquals(presentation, Const.SpecialIdentifier.PUBLIC, AccessLevel.CAN_WRITE)) {
            return;
        }

        UserInfo currentUser = getCurrentLoginUser(email, password)
                .orElseThrow(UnauthorisedException::new);

        // creator can always access their own presentation
        if (presentation.getCreatorIdentifier().equals(currentUser.getUserEmail())) {
            return;
        }

        if (presentationAccessControlRepository.existsByPresentationAndUserIdentifierEqualsAndAccessLevelEquals(presentation, currentUser.getUserEmail(), accessLevel)) {
            return;
        }

        // can_write means can_read
        if (accessLevel == AccessLevel.CAN_READ &&
                presentationAccessControlRepository.existsByPresentationAndUserIdentifierEqualsAndAccessLevelEquals(presentation, currentUser.getUserEmail(), AccessLevel.CAN_WRITE)) {
            return;
        }

        throw new UnauthorisedException();
    }

    public UserInfo createNewUser(String email, String password) {
        return userDetailsLogic.createUserDetails(email, password);
    }

    public boolean verifyUserCredentials(String email, String password) {
        UserInfo userInfo = userDetailsLogic.getUserInfo(email);
        return userInfo.getUserPassword().equals(password);
    }


}
