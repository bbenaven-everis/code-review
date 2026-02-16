package shoestore.shoesstore.shoe.adapters.outbound.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import shoestore.shoesstore.shoe.adapters.outbound.persistence.entity.ShoeEntity;

import java.util.UUID;

public interface R2dbcShoeRepository extends ReactiveCrudRepository<ShoeEntity, UUID> {
    Mono<ShoeEntity> findBySku(String sku);
}