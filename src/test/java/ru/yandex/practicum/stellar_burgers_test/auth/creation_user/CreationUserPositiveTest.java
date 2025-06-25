package ru.yandex.practicum.stellar_burgers_test.auth.creation_user;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.model.Status;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellar_burgers.auth.UserCreationResponseDto;
import ru.yandex.practicum.stellar_burgers_test.auth.AuthBase;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;
import static ru.yandex.practicum.stellar_burgers_test.auth.AuthService.registerUser;

@DisplayName("Успешная регистрация пользователя")
public class CreationUserPositiveTest extends AuthBase {

    @Before
    public void setUpTest(){
        createTestUser();
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка что пользователь может зарегистрироваться с валидными данными")
    public void userCanBeRegistered() {
        // Выполнение запроса
        Response response = registerUser(user);

        // Проверка статус кода
        response.then().statusCode(SC_OK);

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
            try{
                deleteTestUser();
            } catch (AssertionError e) {
                Allure.getLifecycle().updateStep(step -> step.setStatus(Status.BROKEN));
                Allure.addAttachment("Ошибка", "text/plain", e.getMessage());
            }
        }
    }
}
