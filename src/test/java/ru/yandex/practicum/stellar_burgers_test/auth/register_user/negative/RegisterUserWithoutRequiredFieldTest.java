package ru.yandex.practicum.stellar_burgers_test.auth.register_user.negative;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.stellar_burgers.auth.UserRegistrationResponseDto;
import ru.yandex.practicum.stellar_burgers_test.auth.AuthBase;

import java.util.Arrays;
import java.util.Collection;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static ru.yandex.practicum.infrastructure.allure_custom.PrettyJsonAttachmentInAllure.prettyJsonAttachment;
import static ru.yandex.practicum.stellar_burgers_test.auth.AuthService.registerUser;
import static ru.yandex.practicum.stellar_burgers_test.auth.AuthService.registerUserWithNullField;

@DisplayName("Попытка создания пользователя без обязательного поля")
@RunWith(Parameterized.class)
public class RegisterUserWithoutRequiredFieldTest extends AuthBase {
    String deleteField;

    public RegisterUserWithoutRequiredFieldTest(String deleteField) {
        this.deleteField = deleteField;
    }

    @Parameterized.Parameters()
    public static Collection<String> data() {
        return Arrays.asList(
                "email", "password", "name"
        );
    }

    @Before
    public void setUpTest(){
        createTestUser();
    }

    @Description("Должно вернуться сообщение об ошибке")
    @Test
    public void shouldReturnErrorWhenRequiredFieldMiss(){
        //Удаление поля
        switch (deleteField){
            case "email": {testUser.setEmail(null);}
            case "password": {testUser.setPassword(null);}
            case "name": {testUser.setName(null);}
        }
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
    public void shouldReturnErrorWhenRequiredFieldIsNull() throws JsonProcessingException {
        //Удаление поля
        switch (deleteField){
            case "email": {testUser.setEmail(null);}
            case "password": {testUser.setPassword(null);}
            case "name": {testUser.setName(null);}
        }
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
