package shoestore.shoesstore.shoe.adapters.inbound.web.handler;

import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import shoestore.shoesstore.shoe.adapters.inbound.web.dto.CreateShoeRequest;
import shoestore.shoesstore.shoe.adapters.inbound.web.dto.UpdateShoeRequest;
import shoestore.shoesstore.shoe.adapters.inbound.web.mapper.ShoeWebMapper;
import shoestore.shoesstore.shoe.application.usecases.*;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class ShoeHandler {

    private final CreateShoeUseCase create;
    private final GetShoeUseCase get;
    private final ListShoesUseCase list;
    private final UpdateShoeUseCase update;
    private final DeleteShoeUseCase delete;
    private final SpringValidatorAdapter validator;

    public ShoeHandler(
            CreateShoeUseCase create,
            GetShoeUseCase get,
            ListShoesUseCase list,
            UpdateShoeUseCase update,
            DeleteShoeUseCase delete,
            Validator validator
    ) {
        this.create = create;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
        this.validator = new SpringValidatorAdapter(validator);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(CreateShoeRequest.class)
                .flatMap(this::validate)
                .flatMap(body -> create.create(ShoeWebMapper.toCommand(body)))
                .map(ShoeWebMapper::toResponse)
                .flatMap(r -> created(req.uriBuilder().path("/{id}").build(r.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(r));
    }

    public Mono<ServerResponse> getById(ServerRequest req) {
        String id = req.pathVariable("id");
        return get.getById(id)
                .map(ShoeWebMapper::toResponse)
                .flatMap(r -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(r));
    }

    public Mono<ServerResponse> list(ServerRequest req) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(list.list().map(ShoeWebMapper::toResponse), Object.class);
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        String id = req.pathVariable("id");
        return req.bodyToMono(UpdateShoeRequest.class)
                .flatMap(this::validate)
                .flatMap(body -> update.update(id, ShoeWebMapper.toCommand(body)))
                .map(ShoeWebMapper::toResponse)
                .flatMap(r -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(r));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        String id = req.pathVariable("id");
        return delete.delete(id)
                .then(noContent().build());
    }

    private <T> Mono<T> validate(T body) {
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(body, body.getClass().getSimpleName());
        validator.validate(body, errors);
        if (errors.hasErrors()) {
            // Delegamos a nuestro GlobalErrorWebExceptionHandler usando ValidationException del dominio:
            String msg = errors.getAllErrors().get(0).getDefaultMessage();
            return Mono.error(new ValidationException(msg));
        }
        return Mono.just(body);
    }
}