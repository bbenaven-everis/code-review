package shoestore.shoesstore.shoe.adapters.outbound.persistence.mapper;

import shoestore.shoesstore.shared.utils.Money;
import shoestore.shoesstore.shoe.adapters.outbound.persistence.entity.ShoeEntity;
import shoestore.shoesstore.shoe.domain.model.Shoe;
import shoestore.shoesstore.shoe.domain.model.ShoeId;
import shoestore.shoesstore.shoe.domain.model.Sku;

import java.util.Currency;

public final class ShoePersistenceMapper {

    private ShoePersistenceMapper() {}

    public static ShoeEntity toEntity(Shoe shoe) {
        ShoeEntity e = new ShoeEntity();
        e.setId(shoe.id().value());
        e.setSku(shoe.sku().value());
        e.setName(shoe.name());
        e.setBrand(shoe.brand());
        e.setDescription(shoe.description());
        e.setPriceAmount(shoe.price().amount());
        e.setPriceCurrency(shoe.price().currency().getCurrencyCode());
        e.setStock(shoe.stock());
        e.setActive(shoe.active());
        e.setCreatedAt(shoe.createdAt());
        e.setUpdatedAt(shoe.updatedAt());
        return e;
    }

    public static Shoe toDomain(ShoeEntity e) {
        return Shoe.rehydrate(
                new ShoeId(e.getId()),
                Sku.of(e.getSku()),
                e.getName(),
                e.getBrand(),
                e.getDescription(),
                new Money(e.getPriceAmount(), Currency.getInstance(e.getPriceCurrency())),
                e.getStock(),
                e.isActive(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}