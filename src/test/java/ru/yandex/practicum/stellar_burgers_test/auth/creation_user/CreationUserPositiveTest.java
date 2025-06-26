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
import static ru.yandex.practicum.infrastructure.allure_custom.PrettyJsonAttachmentInAllure.prettyJsonAttachment;
import static ru.yandex.practicum.stellar_burgers_test.auth.AuthService.registerUser;

@DisplayName("Успешная регистрация пользователя")
public class CreationUserPositiveTest extends AuthBase {
    private SoftAssertions softly = new SoftAssertions();

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
        step("Проверка тела",
                () -> {
                    softly.assertThat(responseBody.isSuccess())
                            .as("Проверка `success`")
                            .isTrue();
                    softly.assertThat(responseBody.getUser().getEmail())
                            .as("Проверка `email`")
                            .isEqualTo(user.getEmail());
                    softly.assertThat(responseBody.getUser().getName())
                            .as("Проверка `name`")
                            .isEqualTo(user.getName());
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
