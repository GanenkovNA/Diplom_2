package ru.yandex.practicum.stellar_burgers_test.auth;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import net.datafaker.Faker;
import ru.yandex.practicum.stellar_burgers.auth.UserRegistrationResponseDto;
import ru.yandex.practicum.stellar_burgers_test.StellarBurgerBase;
import ru.yandex.practicum.stellar_burgers.auth.UserDto;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static ru.yandex.practicum.stellar_burgers_test.auth.AuthService.deleteUser;
import static ru.yandex.practicum.stellar_burgers_test.auth.AuthService.registerUser;

public class AuthBase extends StellarBurgerBase {
    protected UserDto testUser;
    protected Faker faker = new Faker();

    @Step("Создание тестового пользователя")
    public void createTestUser(){
        testUser = UserDto.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().name())
                .build();

        Allure.addAttachment("Информация о пользователе", "text/plain",
                "Email: " + testUser.getEmail() + "\nPassword: " + testUser.getPassword());
    }

    @Step("Регистрация тестового пользователя")
    public void registerTestUser(){
        Response response = registerUser(testUser);
        response.then().statusCode(SC_OK);

        UserRegistrationResponseDto responseBody = response.as(UserRegistrationResponseDto.class);
        testUser.setAccessToken(responseBody.getAccessToken());
        testUser.setRefreshToken(responseBody.getRefreshToken());
    }

    @Step("Удаление тестового пользователя")
    public void deleteTestUser() {
        Response response = deleteUser(testUser);
        response.then().statusCode(SC_ACCEPTED);
    }

    public UserDto getUser(){
        return testUser;
    }
}
