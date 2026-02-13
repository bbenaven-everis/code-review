package shoestore.shoesstore.shoe.domain.model;

import shoestore.shoesstore.shared.errors.ValidationException;

import java.util.Objects;

public record Sku(String value) {

    public Sku {
        Objects.requireNonNull(value, "sku is required");
        String v = value.trim();
        if (v.isEmpty()) throw new ValidationException("sku must not be blank");
        if (v.length() > 40) throw new ValidationException("sku max length is 40");
        if (!v.matches("^[A-Z0-9\\-]+$")) {
            throw new ValidationException("sku must match ^[A-Z0-9\\-]+$");
        }
    }

    public static Sku of(String value) {
        return new Sku(value);
    }
}
