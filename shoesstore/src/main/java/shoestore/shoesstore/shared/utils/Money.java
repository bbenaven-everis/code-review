package shoestore.shoesstore.shared.utils;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;


public record Money(BigDecimal amount, Currency currency) {

    public Money {
        Objects.requireNonNull(amount, "amount is required");
        Objects.requireNonNull(currency, "currency is required");
        if (amount.scale() > 2) {
            throw new IllegalArgumentException("amount scale must be <= 2");
        }
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("amount must be >= 0");
        }
    }

    public static Money of(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }
}
