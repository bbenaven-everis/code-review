package shoestore.shoesstore.shoe.application.commands;

import java.math.BigDecimal;

public record UpdateShoeCommand(
        String name,
        String brand,
        String description,
        BigDecimal priceAmount,
        String priceCurrency,
        Integer stock,
        Boolean active
) { }