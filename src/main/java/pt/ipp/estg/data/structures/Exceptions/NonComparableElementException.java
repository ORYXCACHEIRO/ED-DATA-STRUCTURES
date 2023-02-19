package pt.ipp.estg.data.structures.Exceptions;

public class NonComparableElementException extends RuntimeException {
    public NonComparableElementException(String collection) {
        super("The " + collection + " requires comparable elements.");
    }
}
