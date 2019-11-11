package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.exception.EntityNotFoundException;
import sg.edu.nus.comp.cs3219.viz.common.util.Config;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.ui.controller.data.AuthInfo;

import java.util.Optional;

@RestController
public class AuthInfoController extends BaseRestController {

    private GateKeeper gateKeeper;

    public AuthInfoController(GateKeeper gateKeeper) {
        this.gateKeeper = gateKeeper;
    }

    @GetMapping("/auth")
    public AuthInfo getAuthInfo(@RequestParam(value = "redirectUrl", required = false) String redirectUrl,
                                @CookieValue(value = "userEmail") String email,
                                @CookieValue(value = "userPassword") String password) {
        String redirect = redirectUrl == null ? Config.APP_URL : redirectUrl;
        UserInfo userInfo = gateKeeper.getCurrentLoginUser(email, password).orElse(null);

        AuthInfo authInfo = new AuthInfo();
        authInfo.setLogin(userInfo != null);
        // development server doesn't have urlPrefix while production server has
        String urlPrefix = Config.isDevServer() ? Config.APP_URL : "";
        if (authInfo.isLogin()) {
            authInfo.setLogoutUrl(urlPrefix + gateKeeper.getLogoutUrl(redirect));
            authInfo.setUserInfo(userInfo);
        } else {
            authInfo.setLoginUrl(urlPrefix + gateKeeper.getLoginUrl(redirect));
        }

        return authInfo;
    }

    @PostMapping("/auth")
    public AuthInfo signUpUser(@RequestParam(value = "redirectUrl", required = false) String redirectUrl,
                               @CookieValue(value = "userEmail") String email, @CookieValue(value = "userPassword") String password) {

        String redirect = redirectUrl == null ? Config.APP_URL : redirectUrl;
        UserInfo info = null;
        try {
            gateKeeper.getCurrentLoginUser(email, password);
            throw new DuplicateKeyException(String.format("User email already used: %s", email));
        } catch (EntityNotFoundException e) {
            info = gateKeeper.createNewUser(email, password);
        }

        AuthInfo authInfo = new AuthInfo();
        authInfo.setLogin(info != null);
        // development server doesn't have urlPrefix while production server has
        String urlPrefix = Config.isDevServer() ? Config.APP_URL : "";
        if (authInfo.isLogin()) {
            authInfo.setLogoutUrl(urlPrefix + gateKeeper.getLogoutUrl(redirect));
            authInfo.setUserInfo(info);
        } else {
            authInfo.setLoginUrl(urlPrefix + gateKeeper.getLoginUrl(redirect));
        }

        return authInfo;
    }


}
