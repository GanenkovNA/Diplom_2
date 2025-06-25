package ru.yandex.practicum.stellar_burgers_test.auth.creation_user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import ru.yandex.practicum.stellar_burgers.auth.UserCreationRequestDto;
import ru.yandex.practicum.stellar_burgers.auth.UserCreationResponseDto;
import ru.yandex.practicum.stellar_burgers_test.auth.AuthBase;

import static org.junit.Assert.*;
import static ru.yandex.practicum.stellar_burgers_test.auth.AuthService.deleteUser;
import static ru.yandex.practicum.stellar_burgers_test.auth.AuthService.registerUser;

public class CreationUserPositiveTest extends AuthBase {
    private String accessToken;
    private UserCreationRequestDto testUser;

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка что пользователь может зарегистрироваться с валидными данными")
    public void userCanBeRegistered() {
        // Выполнение запроса
        Response response = registerUser(user);

        // Проверка статус кода
        response.then().statusCode(200);

        // Парсинг ответа
        UserCreationResponseDto responseBody = response.as(UserCreationResponseDto.class);
        user.setAccessToken(responseBody.getAccessToken());

        // Проверки ответа
        assertTrue(responseBody.isSuccess());
        assertEquals(user.getEmail(), responseBody.getUser().getEmail());
        assertEquals(user.getName(), responseBody.getUser().getName());
        assertNotNull(user.getAccessToken());
    }

    @After
    public void tearDown() {
        if (user.getAccessToken() != null) {
            deleteUser(user);
        }
    }
}
