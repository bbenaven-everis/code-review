package shoestore.shoesstore.shoe.application.usecases;

import reactor.core.publisher.Flux;
import shoestore.shoesstore.shoe.domain.model.Shoe;

public interface ListShoesUseCase {
    Flux<Shoe> list();
}