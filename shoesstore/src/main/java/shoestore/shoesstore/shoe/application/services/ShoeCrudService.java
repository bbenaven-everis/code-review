package shoestore.shoesstore.shoe.application.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shoestore.shoesstore.shared.errors.NotFoundException;
import shoestore.shoesstore.shared.errors.ValidationException;
import shoestore.shoesstore.shared.utils.Money;
import shoestore.shoesstore.shoe.application.commands.CreateShoeCommand;
import shoestore.shoesstore.shoe.application.commands.UpdateShoeCommand;
import shoestore.shoesstore.shoe.application.usecases.*;
import shoestore.shoesstore.shoe.domain.model.Shoe;
import shoestore.shoesstore.shoe.domain.model.ShoeId;
import shoestore.shoesstore.shoe.domain.model.Sku;
import shoestore.shoesstore.shoe.domain.ports.ShoeRepositoryPort;

import java.util.Objects;

@Service
public class ShoeCrudService implements CreateShoeUseCase, GetShoeUseCase, ListShoesUseCase, UpdateShoeUseCase, DeleteShoeUseCase {

    private final ShoeRepositoryPort repository;

    public ShoeCrudService(ShoeRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Shoe> create(CreateShoeCommand command) {
        validateCreate(command);

        Sku sku = Sku.of(command.sku().trim().toUpperCase());
        Money money = Money.of(command.priceAmount(), command.priceCurrency().trim().toUpperCase());
        int stock = Objects.requireNonNullElse(command.stock(), 0);

        return repository.findBySku(sku)
                .flatMap(existing -> Mono.<Shoe>error(new ValidationException("sku already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    Shoe shoe = Shoe.createNew(sku, command.name(), command.brand(), command.description(), money, stock);
                    return repository.save(shoe);
                }));
    }

    @Override
    public Mono<Shoe> getById(String id) {
        return repository.findById(ShoeId.of(id))
                .switchIfEmpty(Mono.error(new NotFoundException("shoe not found: " + id)));
    }

    @Override
    public Flux<Shoe> list() {
        return repository.findAll();
    }

    @Override
    public Mono<Shoe> update(String id, UpdateShoeCommand command) {
        return repository.findById(ShoeId.of(id))
                .switchIfEmpty(Mono.error(new NotFoundException("shoe not found: " + id)))
                .flatMap(shoe -> {
                    Money money = null;
                    if (command.priceAmount() != null || command.priceCurrency() != null) {
                        if (command.priceAmount() == null || command.priceCurrency() == null) {
                            return Mono.error(new ValidationException("priceAmount and priceCurrency must be provided together"));
                        }
                        money = Money.of(command.priceAmount(), command.priceCurrency().trim().toUpperCase());
                    }
                    shoe.update(command.name(), command.brand(), command.description(), money, command.stock(), command.active());
                    return repository.save(shoe);
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        // buena práctica: soft delete a nivel dominio si quieres auditar; aquí hard delete por simplicidad.
        return repository.findById(ShoeId.of(id))
                .switchIfEmpty(Mono.error(new NotFoundException("shoe not found: " + id)))
                .flatMap(shoe -> repository.deleteById(shoe.id()));
    }

    private void validateCreate(CreateShoeCommand c) {
        if (c == null) throw new ValidationException("body is required");
        if (c.sku() == null || c.sku().isBlank()) throw new ValidationException("sku is required");
        if (c.name() == null || c.name().isBlank()) throw new ValidationException("name is required");
        if (c.brand() == null || c.brand().isBlank()) throw new ValidationException("brand is required");
        if (c.priceAmount() == null) throw new ValidationException("priceAmount is required");
        if (c.priceCurrency() == null || c.priceCurrency().isBlank()) throw new ValidationException("priceCurrency is required");
    }
}