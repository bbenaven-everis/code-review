package shoestore.shoesstore.shoe.application.usecases;

import reactor.core.publisher.Mono;

public interface DeleteShoeUseCase {
    Mono<Void> delete(String id);
}
