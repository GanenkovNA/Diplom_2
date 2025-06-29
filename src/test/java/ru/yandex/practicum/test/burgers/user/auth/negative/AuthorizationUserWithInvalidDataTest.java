package ru.yandex.practicum.test.burgers.user.auth.negative;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.burgers.user.UserDto;
import ru.yandex.practicum.burgers.user.register.UserRegistrationResponseDto;
import ru.yandex.practicum.test.burgers.user.UserBase;

import java.util.Arrays;
import java.util.Collection;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static ru.yandex.practicum.infrastructure.allure.CatchTearDownFail.catchTearDownFail;
import static ru.yandex.practicum.infrastructure.allure.PrettyJsonAttachmentInAllure.prettyJsonAttachment;
import static ru.yandex.practicum.test.burgers.user.UserService.authorizationUser;

@DisplayName("Попытка авторизации пользователя с невалидными данными")
@RunWith(Parameterized.class)
public class AuthorizationUserWithInvalidDataTest extends UserBase {
    String changeField;
    UserDto testUserWithInvalidData;

    public AuthorizationUserWithInvalidDataTest(String changeField) {
        this.changeField = changeField;
    }

    @Parameterized.Parameters()
    public static Collection<String> data() {
        return Arrays.asList(
                "email", "password"
        );
    }

    @Before
    public void setUpTest(){
        createTestUser();
        registerTestUser();
    }

    @Description("Должно вернуться сообщение об ошибке")
    @Test
    public void shouldReturnErrorWhenDataInvalidTest(){
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setName("Попытка авторизации пользователя с изменённым полем " + changeField));

        //Изменение поля
        step("Изменение поля " + changeField, () -> {
            switch (changeField){
                case "email": {
                    testUserWithInvalidData = testUser.withEmail(faker.internet().emailAddress());
                    break;
                }
                case "password": {
                    testUserWithInvalidData = testUser.withPassword(faker.internet().password());
                    break;
                }
            }
            Allure.addAttachment("Информация о пользователе", "text/plain",
                    "Email: " + testUserWithInvalidData.getEmail() + "\nPassword: " + testUserWithInvalidData.getPassword());
        });

        // Выполнение запроса
        Response response = authorizationUser(testUserWithInvalidData);

        // Проверка статус кода
        step("Проверка статус кода - " + SC_UNAUTHORIZED,
                () -> response.then().statusCode(SC_UNAUTHORIZED)
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
                            .isEqualTo("email or password are incorrect");
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
