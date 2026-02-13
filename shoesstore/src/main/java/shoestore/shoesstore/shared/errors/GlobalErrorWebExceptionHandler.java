package shoestore.shoesstore.shared.errors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalErrorWebExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = mapStatus(ex);
        String path = exchange.getRequest().getPath().value();

        ApiError body = ApiError.of(path, status.name(), safeMessage(ex));

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        byte[] bytes = toJsonBytes(body);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    private HttpStatus mapStatus(Throwable ex) {
        if (ex instanceof NotFoundException) return HttpStatus.NOT_FOUND;
        if (ex instanceof ValidationException) return HttpStatus.BAD_REQUEST;
        if (ex instanceof DomainException) return HttpStatus.UNPROCESSABLE_ENTITY;
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String safeMessage(Throwable ex) {
        String msg = ex.getMessage();
        return (msg == null || msg.isBlank()) ? ex.getClass().getSimpleName() : msg;
    }

    private byte[] toJsonBytes(ApiError body) {
        try {
            return objectMapper.writeValueAsBytes(body);
        } catch (JsonProcessingException e) {
            String fallback = "{\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"serialization_error\"}";
            return fallback.getBytes(StandardCharsets.UTF_8);
        }
    }
}