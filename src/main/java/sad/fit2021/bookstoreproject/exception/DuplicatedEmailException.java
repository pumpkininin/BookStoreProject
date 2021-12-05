package sad.fit2021.bookstoreproject.exception;

public class DuplicatedEmailException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    public DuplicatedEmailException(final String message) {
        super(message);
    }
}
