package shoestore.shoesstore.shoe.application.usecases;

import reactor.core.publisher.Mono;
import shoestore.shoesstore.shoe.application.commands.UpdateShoeCommand;
import shoestore.shoesstore.shoe.domain.model.Shoe;

public interface UpdateShoeUseCase {
    Mono<Shoe> update(String id, UpdateShoeCommand command);
}
