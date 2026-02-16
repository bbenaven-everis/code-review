package shoestore.shoesstore.shared.errors;

import java.time.Instant;

public record ApiError(
        Instant timestamp,
        String path,
        String code,
        String message
) {
    public static ApiError of(String path, String code, String message) {
        return new ApiError(Instant.now(), path, code, message);
    }
}