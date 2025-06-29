package ru.yandex.practicum.test.burgers.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import ru.yandex.practicum.infrastructure.rest.ApiClient;
import ru.yandex.practicum.burgers.user.auth.UserAuthorizationRequestDto;
import ru.yandex.practicum.burgers.user.register.UserRegistrationRequestDto;
import ru.yandex.practicum.burgers.user.UserDto;

public class UserService {
    protected static final String REGISTER_PATH = "/api/auth/register";
    protected static final String AUTHORIZATION_PATH = "/api/auth/login";
    protected static final String DELETE_USER_PATH = "/api/auth/user";

    // Регистрация пользователя
    public static Response registerUser (UserDto user) {
        UserRegistrationRequestDto userData = UserRegistrationRequestDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .build();

        return ApiClient.post(REGISTER_PATH,
                userData,
                "Регистрация пользователя " + user.getEmail()
                );
    }

    public static Response registerUserWithNullField(UserDto user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Игнорируем @JsonInclude и сериализуем null
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        UserRegistrationRequestDto userData = UserRegistrationRequestDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .build();
        String jsonWithNulls = mapper.writeValueAsString(userData);

        return ApiClient.post(REGISTER_PATH,
                jsonWithNulls,
                "Регистрация с null-полем" + user.getEmail());
    }

    public static Response authorizationUser(UserDto user){
        UserAuthorizationRequestDto userDto = UserAuthorizationRequestDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        return ApiClient.post(AUTHORIZATION_PATH,
                userDto,
                "Авторизация пользователя " + user.getEmail());
    }

    // Удаление пользователя
    public static Response deleteUser (UserDto user) {
        return ApiClient.delete(DELETE_USER_PATH,
                user.getAccessToken(),
                "Удаление пользователя " + user.getEmail()
        );
    }
}
