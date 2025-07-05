package ru.yandex.practicum.test.burgers.orders.create.positive;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.burgers.orders.create.CreateOrderResponseDTO;
import ru.yandex.practicum.test.burgers.orders.OrdersBase;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_OK;
import static ru.yandex.practicum.infrastructure.allure.PrettyJsonAttachmentInAllure.prettyJsonAttachment;
import static ru.yandex.practicum.test.burgers.orders.OrdersService.createOrder;

@DisplayName("Создание заказа с ингредиентами без авторизации")
public class CreateOrderTest extends OrdersBase {

    @Before
    public void setUpTest(){
        setValidOrder();
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами без авторизации")
    @Description("Проверка создания заказа с ингредиентами")
    public void shouldCreateOrderTest(){
        // Выполнение запроса
        Response response = createOrder(order);

        // Проверка статус кода
        step("Проверка статус кода - " + SC_OK,
                () -> response.then().statusCode(SC_OK)
        );

        // Парсинг ответа и его вывод в Allure
        CreateOrderResponseDTO responseBody = response.as(CreateOrderResponseDTO.class);
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
                    softly.assertThat(responseBody.getOrder().getNumber())
                            .as("Проверка `number`")
                            .isNotNull();
                    softly.assertAll();
                });
    }
}
