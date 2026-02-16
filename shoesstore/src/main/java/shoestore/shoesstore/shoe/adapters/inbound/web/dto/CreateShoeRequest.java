package shoestore.shoesstore.shoe.adapters.inbound.web.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateShoeRequest(
        @NotBlank String sku,
        @NotBlank String name,
        @NotBlank String brand,
        @Size(max = 500) String description,
        @NotNull @DecimalMin(value = "0.00") BigDecimal priceAmount,
        @NotBlank @Size(min = 3, max = 3) String priceCurrency,
        @Min(0) Integer stock
) {}