package pt.ipp.estg.data.structures.Exceptions;

public class EmptyBagException extends RuntimeException {
    public EmptyBagException() {
        super("The bag is empty.");
    }

    public EmptyBagException(String message) {
        super(message);
    }
}
