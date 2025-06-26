package ru.yandex.practicum.stellar_burgers_test.auth.login_user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellar_burgers.auth.UserAuthorizationResponseDto;
import ru.yandex.practicum.stellar_burgers_test.auth.AuthBase;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_OK;
import static ru.yandex.practicum.infrastructure.allure_custom.CatchTearDownFail.catchTearDownFail;
import static ru.yandex.practicum.infrastructure.allure_custom.PrettyJsonAttachmentInAllure.prettyJsonAttachment;
import static ru.yandex.practicum.stellar_burgers_test.auth.AuthService.authorizationUser;

@DisplayName("Авторизация существующего пользователя")
public class AuthorizationUserPositiveTest extends AuthBase {
    @Before
    public void setUpTest(){
        createTestUser();
        registerTestUser();
    }

    @DisplayName("Авторизация существующего пользователя")
    @Description("Проверка что пользователь может авторизоваться с валидными данными")
    @Test
    public void shouldLoginExistUser(){
        // Выполнение запроса
        Response response = authorizationUser(testUser);

        // Проверка статус кода
        step("Проверка статус кода - " + SC_OK,
                () -> response.then().statusCode(SC_OK)
        );

        // Парсинг ответа и его вывод в Allure
        UserAuthorizationResponseDto responseBody = response.as(UserAuthorizationResponseDto.class);
        testUser.setAccessToken(responseBody.getAccessToken());
        testUser.setRefreshToken(responseBody.getRefreshToken());
        prettyJsonAttachment(responseBody);

        step("Проверка тела",
                () -> {
                    softly.assertThat(responseBody.isSuccess())
                            .as("Проверка `success`")
                            .isTrue();
                    softly.assertThat(responseBody.getAccessToken())
                            .as("Проверка `accessToken`")
                            .isNotNull();
                    softly.assertThat(responseBody.getRefreshToken())
                            .as("Проверка `refreshToken")
                            .isNotNull();
                    softly.assertThat(responseBody.getUser().getEmail())
                            .as("Проверка `email`")
                            .isEqualTo(testUser.getEmail());
                    softly.assertThat(responseBody.getUser().getName())
                            .as("Проверка `name`")
                            .isEqualTo(testUser.getName());
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
