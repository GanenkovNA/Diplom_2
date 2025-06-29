package ru.yandex.practicum.burgers.user.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthorizationResponseDto {
    private boolean success;
    private String accessToken;
    private String refreshToken;
    private User user;
    private String message;

    @Data
    public static class User {
        private String email;
        private String name;
    }
}
