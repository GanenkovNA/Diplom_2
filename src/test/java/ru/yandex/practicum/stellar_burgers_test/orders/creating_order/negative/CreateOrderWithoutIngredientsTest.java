package ru.yandex.practicum.stellar_burgers_test.orders.creating_order.negative;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellar_burgers.orders.OrderResponseDTO;
import ru.yandex.practicum.stellar_burgers_test.orders.OrdersBase;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static ru.yandex.practicum.infrastructure.allure_custom.PrettyJsonAttachmentInAllure.prettyJsonAttachment;
import static ru.yandex.practicum.stellar_burgers_test.orders.OrdersService.createOrder;

@DisplayName("Создание заказа без ингредиентов")
public class CreateOrderWithoutIngredientsTest extends OrdersBase {

    @Before
    public void setUpTest(){
        setOrder(null);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверка создания заказа без ингредиентов")
    public void shouldReturnErrorWhenOrderWithoutIngredientsField(){
        // Выполнение запроса
        Response response = createOrder(order);

        // Проверка статус кода
        step("Проверка статус кода - " + SC_BAD_REQUEST,
                () -> response.then().statusCode(SC_BAD_REQUEST)
        );

        // Парсинг ответа и его вывод в Allure
        OrderResponseDTO responseBody = response.as(OrderResponseDTO.class);
        prettyJsonAttachment(responseBody);

        // Проверки ответа
        step("Проверка тела",
                () -> {
                    softly.assertThat(responseBody.isSuccess())
                            .as("Проверка `success`")
                            .isFalse();
                    softly.assertThat(responseBody.getMessage())
                            .as("Проверка `message`")
                            .isEqualTo("Ingredient ids must be provided");
                    softly.assertAll();
                });
    }
}
