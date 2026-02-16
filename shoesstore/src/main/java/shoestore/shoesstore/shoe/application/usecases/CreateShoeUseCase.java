package shoestore.shoesstore.shoe.application.usecases;

import reactor.core.publisher.Mono;
import shoestore.shoesstore.shoe.application.commands.CreateShoeCommand;
import shoestore.shoesstore.shoe.domain.model.Shoe;


public interface CreateShoeUseCase {
    Mono<Shoe> create(CreateShoeCommand command);
}
