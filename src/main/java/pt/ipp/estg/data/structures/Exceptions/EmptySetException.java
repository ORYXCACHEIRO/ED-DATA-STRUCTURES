package pt.ipp.estg.data.structures.Exceptions;

public class EmptySetException extends RuntimeException {
    public EmptySetException() {
        super("The set is empty.");
    }

    public EmptySetException(String message) {
        super(message);
    }
}
