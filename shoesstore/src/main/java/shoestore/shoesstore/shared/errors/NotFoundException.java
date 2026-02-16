package shoestore.shoesstore.shared.errors;

public class NotFoundException extends DomainException {
    public NotFoundException(String message) {
        super(message);
    }
}
