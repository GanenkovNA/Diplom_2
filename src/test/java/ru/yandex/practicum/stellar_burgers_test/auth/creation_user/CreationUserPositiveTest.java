package ru.yandex.practicum.stellar_burgers_test.auth.creation_user;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.model.Status;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellar_burgers.auth.UserCreationResponseDto;
import ru.yandex.practicum.stellar_burgers_test.auth.AuthBase;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_OK;
import static ru.yandex.practicum.infrastructure.AllureMethods.prettyJsonAttachment;
import static ru.yandex.practicum.stellar_burgers_test.auth.AuthService.registerUser;

@DisplayName("Успешная регистрация пользователя")
public class CreationUserPositiveTest extends AuthBase {
    SoftAssertions softly = new SoftAssertions();

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
        step("Проверка статус кода - " + SC_OK,
                () -> response.then().statusCode(SC_OK)
        );

        // Парсинг ответа и его вывод в Allure
        UserCreationResponseDto responseBody = response.as(UserCreationResponseDto.class);
        user.setAccessToken(responseBody.getAccessToken());
        prettyJsonAttachment(responseBody);

        // Проверки ответа
        step("Проверка значения `success`",
                () -> softly
                        .assertThat(responseBody.isSuccess())
                        .isTrue()
        );
        step("Проверка значения `email`",
                () -> softly
                        .assertThat(responseBody.getUser().getEmail())
                        .isEqualTo(user.getEmail())
        );
        step("Проверка значения `name`",
                () -> softly
                        .assertThat(responseBody.getUser().getName())
                        .isEqualTo(user.getName())
                        //.isEqualTo("asd")
        );
        step("Проверка значения `accessToken`",
                () -> softly
                        .assertThat(responseBody.getAccessToken())
                        .isNotNull()
        );

        softly.assertAll();
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
