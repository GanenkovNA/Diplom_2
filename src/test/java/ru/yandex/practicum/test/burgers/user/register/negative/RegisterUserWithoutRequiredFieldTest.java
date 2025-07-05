package ru.yandex.practicum.test.burgers.user.register.negative;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.burgers.user.register.UserRegistrationResponseDto;
import ru.yandex.practicum.test.burgers.user.UserBase;

import java.util.Arrays;
import java.util.Collection;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static ru.yandex.practicum.infrastructure.allure.PrettyJsonAttachmentInAllure.prettyJsonAttachment;
import static ru.yandex.practicum.test.burgers.user.UserService.registerUser;
import static ru.yandex.practicum.test.burgers.user.UserService.registerUserWithNullField;

@DisplayName("Попытка создания пользователя без обязательного поля")
@RunWith(Parameterized.class)
public class RegisterUserWithoutRequiredFieldTest extends UserBase {
    String deleteField;

    public RegisterUserWithoutRequiredFieldTest(String deleteField) {
        this.deleteField = deleteField;
    }

    @Parameterized.Parameters()
    public static Collection<String> data() {
        return Arrays.asList(
                "email",
                "password",
                "name"
        );
    }

    @Before
    public void setUpTest(){
        createTestUser();

        //Установка null в поле
        switch (deleteField){
            case "email": {
                testUser.setEmail(null);
                break;
            }
            case "password": {
                testUser.setPassword(null);
                break;
            }
            case "name": {
                testUser.setName(null);
                break;
            }
        }
    }

    @Description("Должно вернуться сообщение об ошибке")
    @Test
    public void shouldReturnErrorWhenRequiredFieldMissTest(){
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setName("Создание пользователя без поля " + deleteField));

        // Выполнение запроса
        Response response = registerUser(testUser);

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
                            .isEqualTo("Email, password and name are required fields");
                    softly.assertAll();
                });
    }

    @Description("Должно вернуться сообщение об ошибке")
    @Test
    public void shouldReturnErrorWhenRequiredFieldIsNullTest() throws JsonProcessingException {
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setName("Создание пользователя с Null полем " + deleteField));

        // Выполнение запроса
        Response response = registerUserWithNullField(testUser);

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
                            .isEqualTo("Email, password and name are required fields");
                    softly.assertAll();
                });
    }
}
