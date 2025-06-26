package ru.yandex.practicum.stellar_burgers_test.orders.creating_order.negative;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellar_burgers_test.orders.OrdersBase;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static ru.yandex.practicum.stellar_burgers_test.orders.OrdersService.createOrder;

@DisplayName("Создание заказа с невалидным хэшем")
public class CreateOrderWithInvalidHashTest extends OrdersBase {

    @Before
    public void setUpTest(){
        setInvalidOrder();
    }

    @Test
    @DisplayName("Создание заказа с невалидным хэшем")
    @Description("Проверка создания заказа с невалидным хэшем")
    public void shouldReturnError500(){
        // Выполнение запроса
        Response response = createOrder(order);

        // Проверка статус кода
        step("Проверка статус кода - " + SC_INTERNAL_SERVER_ERROR,
                () -> response.then().statusCode(SC_INTERNAL_SERVER_ERROR)
        );

    }
}
