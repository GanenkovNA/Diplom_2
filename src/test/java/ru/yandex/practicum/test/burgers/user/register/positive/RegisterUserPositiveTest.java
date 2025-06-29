package ru.yandex.practicum.test.burgers.user.register.positive;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.burgers.user.register.UserRegistrationResponseDto;
import ru.yandex.practicum.test.burgers.user.UserBase;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_OK;
import static ru.yandex.practicum.infrastructure.allure.CatchTearDownFail.catchTearDownFail;
import static ru.yandex.practicum.infrastructure.allure.PrettyJsonAttachmentInAllure.prettyJsonAttachment;
import static ru.yandex.practicum.test.burgers.user.UserService.registerUser;

@DisplayName("Регистрация уникального пользователя")
public class RegisterUserPositiveTest extends UserBase {

    @Before
    public void setUpTest(){
        createTestUser();
    }

    @Test
    @DisplayName("Регистрация уникального пользователя")
    @Description("Проверка что пользователь может зарегистрироваться с валидными данными")
    public void userShouldBeRegisteredTest() {
        // Выполнение запроса
        Response response = registerUser(testUser);

        // Проверка статус кода
        step("Проверка статус кода - " + SC_OK,
                () -> response.then().statusCode(SC_OK)
        );

        // Парсинг ответа и его вывод в Allure
        UserRegistrationResponseDto responseBody = response.as(UserRegistrationResponseDto.class);
        testUser.setAccessToken(responseBody.getAccessToken());
        testUser.setRefreshToken(responseBody.getRefreshToken());
        prettyJsonAttachment(responseBody);

        // Проверки ответа
        step("Проверка тела",
                () -> {
                    softly.assertThat(responseBody.isSuccess())
                            .as("Проверка `success`")
                            .isTrue();
                    softly.assertThat(responseBody.getUser().getEmail())
                            .as("Проверка `email`")
                            .isEqualTo(testUser.getEmail());
                    softly.assertThat(responseBody.getUser().getName())
                            .as("Проверка `name`")
                            .isEqualTo(testUser.getName());
                    softly.assertThat(responseBody.getAccessToken())
                            .as("Проверка `accessToken`")
                            .isNotNull();
                    softly.assertThat(responseBody.getRefreshToken())
                            .as("Проверка `refreshToken")
                            .isNotNull();
                    softly.assertAll();
                });
    }

    @After
    public void tearDown() {
        if (testUser.getAccessToken() != null) {
            catchTearDownFail(this::deleteTestUser);
        }
    }
}
