package sg.edu.nus.comp.cs3219.viz.logic;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AccessLevel;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.entity.UserDetails;
import sg.edu.nus.comp.cs3219.viz.common.exception.UnauthorisedException;
import sg.edu.nus.comp.cs3219.viz.common.util.Const;
import sg.edu.nus.comp.cs3219.viz.storage.repository.PresentationAccessControlRepository;
import sg.edu.nus.comp.cs3219.viz.storage.repository.UserDetailsRepository;

import java.util.Optional;

@Component
public class GateKeeper {

    private PresentationAccessControlRepository presentationAccessControlRepository;
    private UserDetailsRepository userDetailsRepository;
    private SignUpLogic signUpLogic;

    public GateKeeper(PresentationAccessControlRepository presentationAccessControlRepository,
                      UserDetailsRepository userDetailsRepository, SignUpLogic signUpLogic) {
        this.presentationAccessControlRepository = presentationAccessControlRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.signUpLogic = signUpLogic;
    }

    private static UserService userService = UserServiceFactory.getUserService();

    public Optional<UserInfo> getCurrentLoginUser() {
        User user = userService.getCurrentUser();

        if (user == null) {
            return Optional.empty();
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserEmail(user.getEmail());
        userInfo.setUserNickname(user.getNickname());
        userInfo.setUserId(getUserIdIfExistElseCreate(user));
        return Optional.of(userInfo);
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

    public UserInfo verifyLoginAccess() {
        return getCurrentLoginUser().orElseThrow(UnauthorisedException::new);
    }

    public void verifyDeletionAccessForPresentation(Presentation presentation) {
        if (presentation == null) {
            throw new UnauthorisedException();
        }

        UserInfo currentUser = getCurrentLoginUser()
                .orElseThrow(UnauthorisedException::new);

        if (!(currentUser.getUserId() == presentation.getUserId())) {
            throw new UnauthorisedException();
        }
    }

    public void verifyAccessForPresentation(Presentation presentation, AccessLevel accessLevel) {
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

        UserInfo currentUser = getCurrentLoginUser()
                .orElseThrow(UnauthorisedException::new);

        // creator can always access their own presentation
        if (presentation.getUserId() == currentUser.getUserId()) {
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

    public long getUserIdIfExistElseCreate(User user) {
        Optional<UserDetails> userProfile = userDetailsRepository.findByUserEmail(user.getEmail());
        if (userProfile.isPresent()) {
            return userProfile.get().getUserId();
        } else {
            //creates a new user profile
            UserDetails newUser = signUpLogic.createNewUser(user);
            return newUser.getUserId();
        }
    }


}
