package ru.yandex.practicum.stellar_burgers.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String accessToken;
    private String refreshToken;
}
