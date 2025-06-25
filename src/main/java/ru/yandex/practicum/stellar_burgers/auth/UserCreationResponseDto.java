package ru.yandex.practicum.stellar_burgers.auth;

import lombok.Data;

@Data
public class UserCreationResponseDto {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

    @Data
    public static class User {
        private String email;
        private String name;
    }
}
