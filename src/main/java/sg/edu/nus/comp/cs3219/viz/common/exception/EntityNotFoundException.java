package sg.edu.nus.comp.cs3219.viz.common.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(String.format("Could not find entity requested: %s", message));
    }

}
