package ru.yandex.practicum.test.burgers.user.register.negative;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.burgers.user.register.UserRegistrationResponseDto;
import ru.yandex.practicum.test.burgers.user.UserBase;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static ru.yandex.practicum.infrastructure.allure.CatchTearDownFail.catchTearDownFail;
import static ru.yandex.practicum.infrastructure.allure.PrettyJsonAttachmentInAllure.prettyJsonAttachment;
import static ru.yandex.practicum.test.burgers.user.UserService.registerUser;

@DisplayName("Создание пользователя, который уже зарегистрирован")
public class RegisterExistUserTest extends UserBase {
    private Response response;

    @Before
    public void setUpTest(){
        createTestUser();
        registerTestUser();
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Должно вернуться сообщение о существовании такого пользователя")
    public void shouldReturnThatUserExistTest(){
        // Выполнение запроса
        step("Повторная регистрация пользователя",
                () -> response = registerUser(testUser)
        );

        // Проверка статус кода
        step("Проверка статус кода - " + SC_FORBIDDEN,
                () -> response.then().statusCode(SC_FORBIDDEN)
        );

        // Парсинг ответа и его вывод в Allure
        UserRegistrationResponseDto responseBody = response.as(UserRegistrationResponseDto.class);
        prettyJsonAttachment(responseBody);

        // Проверки ответа
        step("Проверка тела",
                () -> {
                    softly.assertThat(responseBody.isSuccess())
                            .as("Проверка `success`")
                            .isFalse();
                    softly.assertThat(responseBody.getMessage())
                            .as("Проверка `message`")
                            .isEqualTo("User already exists");
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
