package shoestore.shoesstore.shoe.domain.model;

import java.util.Objects;
import java.util.UUID;

public record ShoeId(UUID value) {
    public ShoeId {
        Objects.requireNonNull(value, "id is required");
    }

    public static ShoeId newId() {
        return new ShoeId(UUID.randomUUID());
    }

    public static ShoeId of(String raw) {
        return new ShoeId(UUID.fromString(raw));
    }
}
