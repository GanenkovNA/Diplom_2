package ru.yandex.practicum.stellar_burgers.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegistrationResponseDto {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;
    private String message;

    @Data
    public static class User {
        private String email;
        private String name;
    }
}
