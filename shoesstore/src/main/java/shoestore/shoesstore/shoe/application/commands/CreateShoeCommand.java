package shoestore.shoesstore.shoe.application.commands;

import java.math.BigDecimal;


public record CreateShoeCommand(
        String sku,
        String name,
        String brand,
        String description,
        BigDecimal priceAmount,
        String priceCurrency,
        Integer stock
) { }