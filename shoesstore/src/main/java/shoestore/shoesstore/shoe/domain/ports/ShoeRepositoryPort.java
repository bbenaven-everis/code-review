package shoestore.shoesstore.shoe.domain.ports;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shoestore.shoesstore.shoe.domain.model.Shoe;
import shoestore.shoesstore.shoe.domain.model.ShoeId;
import shoestore.shoesstore.shoe.domain.model.Sku;

public interface ShoeRepositoryPort {
    Mono<Shoe> save(Shoe shoe);
    Mono<Shoe> findById(ShoeId id);
    Mono<Shoe> findBySku(Sku sku);
    Flux<Shoe> findAll();
    Mono<Void> deleteById(ShoeId id);
}
