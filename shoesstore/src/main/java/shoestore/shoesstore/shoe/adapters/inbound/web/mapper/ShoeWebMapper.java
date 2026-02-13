package shoestore.shoesstore.shoe.adapters.inbound.web.mapper;



import shoestore.shoesstore.shoe.adapters.inbound.web.dto.CreateShoeRequest;
import shoestore.shoesstore.shoe.adapters.inbound.web.dto.ShoeResponse;
import shoestore.shoesstore.shoe.adapters.inbound.web.dto.UpdateShoeRequest;
import shoestore.shoesstore.shoe.application.commands.CreateShoeCommand;
import shoestore.shoesstore.shoe.application.commands.UpdateShoeCommand;
import shoestore.shoesstore.shoe.domain.model.Shoe;

public final class ShoeWebMapper {

    private ShoeWebMapper() {}

    public static CreateShoeCommand toCommand(CreateShoeRequest r) {
        return new CreateShoeCommand(
                r.sku(),
                r.name(),
                r.brand(),
                r.description(),
                r.priceAmount(),
                r.priceCurrency(),
                r.stock()
        );
    }

    public static UpdateShoeCommand toCommand(UpdateShoeRequest r) {
        return new UpdateShoeCommand(
                r.name(),
                r.brand(),
                r.description(),
                r.priceAmount(),
                r.priceCurrency(),
                r.stock(),
                r.active()
        );
    }


    public static ShoeResponse toResponse(Shoe s) {
        return new ShoeResponse(
                s.id().value(),
                s.sku().value(),
                s.name(),
                s.brand(),
                s.description(),
                s.price().amount(),
                s.price().currency().getCurrencyCode(),
                s.stock(),
                s.active(),
                s.createdAt(),
                s.updatedAt()
        );
    }
}