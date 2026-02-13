package shoestore.shoesstore.shoe.adapters.outbound.persistence;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shoestore.shoesstore.shoe.adapters.outbound.persistence.mapper.ShoePersistenceMapper;
import shoestore.shoesstore.shoe.adapters.outbound.persistence.repository.R2dbcShoeRepository;
import shoestore.shoesstore.shoe.domain.model.Shoe;
import shoestore.shoesstore.shoe.domain.model.ShoeId;
import shoestore.shoesstore.shoe.domain.model.Sku;
import shoestore.shoesstore.shoe.domain.ports.ShoeRepositoryPort;


@Component
@ConditionalOnProperty(name = "app.persistence", havingValue = "r2dbc", matchIfMissing = true)
public class ShoeRepositoryAdapter implements ShoeRepositoryPort {

    private final R2dbcShoeRepository repository;

    public ShoeRepositoryAdapter(R2dbcShoeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Shoe> save(Shoe shoe) {
        return repository.save(ShoePersistenceMapper.toEntity(shoe))
                .map(ShoePersistenceMapper::toDomain);
    }

    @Override
    public Mono<Shoe> findById(ShoeId id) {
        return repository.findById(id.value()).map(ShoePersistenceMapper::toDomain);
    }

    @Override
    public Mono<Shoe> findBySku(Sku sku) {
        return repository.findBySku(sku.value()).map(ShoePersistenceMapper::toDomain);
    }

    @Override
    public Flux<Shoe> findAll() {
        return repository.findAll().map(ShoePersistenceMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(ShoeId id) {
        return repository.deleteById(id.value());
    }
}