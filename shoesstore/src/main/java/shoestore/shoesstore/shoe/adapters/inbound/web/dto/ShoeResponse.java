package shoestore.shoesstore.shoe.adapters.inbound.web.dto;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ShoeResponse(
        UUID id,
        String sku,
        String name,
        String brand,
        String description,
        BigDecimal priceAmount,
        String priceCurrency,
        int stock,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {}