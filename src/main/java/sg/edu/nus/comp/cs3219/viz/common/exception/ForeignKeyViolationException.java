package sg.edu.nus.comp.cs3219.viz.common.exception;

public class ForeignKeyViolationException extends RuntimeException {

    public ForeignKeyViolationException(String parentClass, String childClass) {
        super(String.format("Could not complete command as %s has a dependency on %s", childClass, parentClass));
    }

}

