package shoestore.shoesstore.shoe.domain.model;


import shoestore.shoesstore.shared.errors.ValidationException;
import shoestore.shoesstore.shared.utils.Money;

import java.time.Instant;
import java.util.Objects;

public final class Shoe {

    private final ShoeId id;
    private Sku sku;
    private String name;
    private String brand;
    private String description;
    private Money price;
    private int stock;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private Shoe(
            ShoeId id,
            Sku sku,
            String name,
            String brand,
            String description,
            Money price,
            int stock,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = Objects.requireNonNull(id);
        this.sku = Objects.requireNonNull(sku);
        this.name = requireText(name, "name", 120);
        this.brand = requireText(brand, "brand", 80);
        this.description = normalizeOptional(description, 500);
        this.price = Objects.requireNonNull(price);
        this.stock = requireNonNegative(stock, "stock");
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public static Shoe createNew(Sku sku, String name, String brand, String description, Money price, int stock) {
        Instant now = Instant.now();
        return new Shoe(ShoeId.newId(), sku, name, brand, description, price, stock, true, now, now);
    }

    public Shoe update(String name, String brand, String description, Money price, Integer stock, Boolean active) {
        if (name != null) this.name = requireText(name, "name", 120);
        if (brand != null) this.brand = requireText(brand, "brand", 80);
        if (description != null) this.description = normalizeOptional(description, 500);
        if (price != null) this.price = price;
        if (stock != null) this.stock = requireNonNegative(stock, "stock");
        if (active != null) this.active = active;
        this.updatedAt = Instant.now();
        return this;
    }

    public Shoe deactivate() {
        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    private static String requireText(String value, String field, int maxLen) {
        if (value == null) throw new ValidationException(field + " is required");
        String v = value.trim();
        if (v.isEmpty()) throw new ValidationException(field + " must not be blank");
        if (v.length() > maxLen) throw new ValidationException(field + " max length is " + maxLen);
        return v;
    }

    private static String normalizeOptional(String value, int maxLen) {
        if (value == null) return null;
        String v = value.trim();
        if (v.isEmpty()) return null;
        if (v.length() > maxLen) throw new ValidationException("description max length is " + maxLen);
        return v;
    }

    private static int requireNonNegative(int value, String field) {
        if (value < 0) throw new ValidationException(field + " must be >= 0");
        return value;
    }


    public static Shoe rehydrate(
            ShoeId id,
            Sku sku,
            String name,
            String brand,
            String description,
            Money price,
            int stock,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Shoe(id, sku, name, brand, description, price, stock, active, createdAt, updatedAt);
    }


    // Getters (exponemos solo lo necesario)
    public ShoeId id() {
        return id;
    }

    public Sku sku() {
        return sku;
    }

    public String name() {
        return name;
    }

    public String brand() {
        return brand;
    }

    public String description() {
        return description;
    }

    public Money price() {
        return price;
    }

    public int stock() {
        return stock;
    }

    public boolean active() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
