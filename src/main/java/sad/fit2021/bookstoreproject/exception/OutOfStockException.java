package sad.fit2021.bookstoreproject.exception;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(final String message){
        super(message);
    }
}
