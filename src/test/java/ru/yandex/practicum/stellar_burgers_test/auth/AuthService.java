package ru.yandex.practicum.stellar_burgers_test.auth;

import io.restassured.response.Response;
import ru.yandex.practicum.infrastructure.ApiClient;
import ru.yandex.practicum.stellar_burgers.auth.UserCreationRequestDto;
import ru.yandex.practicum.stellar_burgers.auth.UserDto;

public class AuthService {
    protected static final String REGISTER_PATH = "/api/auth/register";
    protected static final String DELETE_USER_PATH = "/api/auth/user";

    // Регистрация пользователя
    public static Response registerUser (UserDto user) {
        UserCreationRequestDto userData = UserCreationRequestDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .build();

        return ApiClient.post(REGISTER_PATH,
                userData,
                "Регистрация пользователя " + user.getEmail()
                );
    }

    // Альтернатива: токен в header (более правильный способ)
    public static Response deleteUser (UserDto user) {
        return ApiClient.delete(DELETE_USER_PATH,
                user.getAccessToken(),
                "Удаление пользователя " + user.getEmail()
        );
    }
}
