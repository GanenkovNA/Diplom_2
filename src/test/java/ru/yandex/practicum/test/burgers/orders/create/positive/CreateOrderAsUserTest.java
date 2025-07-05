package ru.yandex.practicum.test.burgers.orders.create.positive;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.burgers.user.UserDto;
import ru.yandex.practicum.burgers.orders.create.CreateOrderAsUserResponseDTO;
import ru.yandex.practicum.test.burgers.user.UserBase;
import ru.yandex.practicum.test.burgers.orders.OrdersBase;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_OK;
import static ru.yandex.practicum.infrastructure.allure.CatchTearDownFail.catchTearDownFail;
import static ru.yandex.practicum.infrastructure.allure.PrettyJsonAttachmentInAllure.prettyJsonAttachment;
import static ru.yandex.practicum.test.burgers.orders.OrdersService.createOrderAsUser;

@DisplayName("Создание заказа с ингредиентами с авторизацией")
public class CreateOrderAsUserTest extends OrdersBase {
    private final UserBase testUserBase = new UserBase();
    private UserDto testUser;

    @Before
    public void setUpTest(){
        setValidOrder();
        testUserBase.createTestUser();
        testUserBase.registerTestUser();
        testUser = testUserBase.getUser();
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами с авторизацией")
    @Description("Проверка создания заказа с ингредиентами как пользователь")
    public void shouldCreateOrderAsUserTest(){
        // Выполнение запроса
        Response response = createOrderAsUser(order, testUser.getAccessToken());

        // Проверка статус кода
        step("Проверка статус кода - " + SC_OK,
                () -> response.then().statusCode(SC_OK)
        );

        // Парсинг ответа и его вывод в Allure
        CreateOrderAsUserResponseDTO responseBody = response.as(CreateOrderAsUserResponseDTO.class);
        prettyJsonAttachment(responseBody);

        // Проверки ответа
        step("Проверка тела",
                () -> {
                    softly.assertThat(responseBody.isSuccess())
                            .as("Проверка `success`")
                            .isTrue();
                    softly.assertThat(responseBody.getName())
                            .as("Проверка `name`")
                            .isNotNull();
                    softly.assertThat(responseBody.getOrder().getIngredients())
                            .as("Проверка `ingredients`")
                            .isNotNull();
                    softly.assertThat(responseBody.getOrder().getUnderscoreId())
                            .as("Проверка `_id`")
                            .isNotNull();
                    softly.assertThat(responseBody.getOrder().getOwner().getName())
                            .as("Проверка `owner.name`")
                            .isEqualTo(testUser.getName());
                    softly.assertThat(responseBody.getOrder().getOwner().getEmail())
                            .as("Проверка `owner.email`")
                            .isEqualTo(testUser.getEmail());
                    softly.assertThat(responseBody.getOrder().getNumber())
                            .as("Проверка `number`")
                            .isNotNull();
                    softly.assertThat(responseBody.getOrder().getPrice())
                            .as("Проверка `price`")
                            .isNotNull();
                    softly.assertAll();
                });
    }

    @After
    public void tearDown(){
        if (testUser.getAccessToken() != null) {
            catchTearDownFail(testUserBase::deleteTestUser);
        }
    }
}
