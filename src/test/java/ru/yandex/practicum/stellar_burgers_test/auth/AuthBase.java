package ru.yandex.practicum.stellar_burgers_test.auth;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.stellar_burgers_test.StellarBurgerBase;
import ru.yandex.practicum.stellar_burgers.auth.UserDto;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static ru.yandex.practicum.stellar_burgers_test.auth.AuthService.deleteUser;

public class AuthBase extends StellarBurgerBase {
    protected UserDto user;

    // JavaFaker/DataFaker !!!
    @Step("Создание тестового пользователя")
    public void createTestUser(){
        user = UserDto.builder()
                .email("bobr@dobr.net")
                .password("password")
                .name("userName")
                .build();

        Allure.addAttachment("Информация о пользователе", "text/plain", "Email: " + user.getEmail() + "\nPassword: " + user.getPassword());
    }

    @Step("Удаление тестового пользователя")
    public void deleteTestUser() {
        Response response = deleteUser(user);
        response.then().statusCode(SC_ACCEPTED);
    }
}
