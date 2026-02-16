package shoestore.shoesstore.shoe.application.usecases;


import reactor.core.publisher.Mono;
import shoestore.shoesstore.shoe.domain.model.Shoe;


public interface GetShoeUseCase {
    Mono<Shoe> getById(String id);
}
