package shoestore.shoesstore.shoe.adapters.outbound.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shoestore.shoesstore.shoe.domain.model.Shoe;
import shoestore.shoesstore.shoe.domain.model.ShoeId;
import shoestore.shoesstore.shoe.domain.model.Sku;
import shoestore.shoesstore.shoe.domain.ports.ShoeRepositoryPort;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ConditionalOnProperty(name = "app.persistence", havingValue = "memory")
public class InMemoryShoeRepositoryAdapter implements ShoeRepositoryPort {

    private final Map<ShoeId, Shoe> byId = new ConcurrentHashMap<>();
    private final Map<String, ShoeId> skuIndex = new ConcurrentHashMap<>();

    @Override
    public Mono<Shoe> save(Shoe shoe) {
        Objects.requireNonNull(shoe, "shoe");
        byId.put(shoe.id(), shoe);
        skuIndex.put(shoe.sku().value(), shoe.id());
        return Mono.just(shoe);
    }

    @Override
    public Mono<Shoe> findById(ShoeId id) {
        Shoe shoe = byId.get(id);
        return shoe == null ? Mono.empty() : Mono.just(shoe);
    }

    @Override
    public Mono<Shoe> findBySku(Sku sku) {
        ShoeId id = skuIndex.get(sku.value());
        if (id == null) return Mono.empty();
        Shoe shoe = byId.get(id);
        return shoe == null ? Mono.empty() : Mono.just(shoe);
    }

    @Override
    public Flux<Shoe> findAll() {
        return Flux.fromIterable(byId.values())
                .sort(Comparator.comparing(s -> s.createdAt())); // orden estable para tests
    }

    @Override
    public Mono<Void> deleteById(ShoeId id) {
        Shoe removed = byId.remove(id);
        if (removed != null) skuIndex.remove(removed.sku().value());
        return Mono.empty();
    }
}