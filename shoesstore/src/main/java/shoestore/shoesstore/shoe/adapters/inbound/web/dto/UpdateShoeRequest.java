package shoestore.shoesstore.shoe.adapters.inbound.web.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record UpdateShoeRequest(
        @Size(max = 120) String name,
        @Size(max = 80) String brand,
        @Size(max = 500) String description,
        @DecimalMin(value = "0.00") BigDecimal priceAmount,
        @Size(min = 3, max = 3) String priceCurrency,
        @Min(0) Integer stock,
        Boolean active
) {}
