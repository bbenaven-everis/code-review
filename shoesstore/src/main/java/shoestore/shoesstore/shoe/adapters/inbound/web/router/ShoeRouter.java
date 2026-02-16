package shoestore.shoesstore.shoe.adapters.inbound.web.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.web.reactive.function.server.ServerResponse;
import shoestore.shoesstore.shoe.adapters.inbound.web.handler.ShoeHandler;


@Configuration
public class ShoeRouter {

    @Bean
    RouterFunction<ServerResponse> shoeRoutes(ShoeHandler handler) {
        return route(POST("/api/shoes"), handler::create)
                .andRoute(GET("/api/shoes"), handler::list)
                .andRoute(GET("/api/shoes/{id}"), handler::getById)
                .andRoute(PUT("/api/shoes/{id}"), handler::update)
                .andRoute(DELETE("/api/shoes/{id}"), handler::delete);
    }
}
