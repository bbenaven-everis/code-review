package shoestore.shoesstore.shared.errors;

public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
}