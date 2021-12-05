package sad.fit2021.bookstoreproject.exception;

public class DuplicatedUsernameException extends RuntimeException {
    private static final long serialVersionUID = 3L;

    public DuplicatedUsernameException(final String message) {
        super(message);
    }
}
