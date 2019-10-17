package sg.edu.nus.comp.cs3219.viz.common.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super(String.format("Could not find user with the user id: %d", userId));
    }

}
