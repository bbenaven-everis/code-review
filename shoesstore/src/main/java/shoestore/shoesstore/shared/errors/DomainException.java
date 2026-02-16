package shoestore.shoesstore.shared.errors;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
