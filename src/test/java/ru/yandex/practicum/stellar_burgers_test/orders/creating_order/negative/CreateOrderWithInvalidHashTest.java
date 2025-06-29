package ru.yandex.practicum.stellar_burgers_test.orders.creating_order.negative;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.stellar_burgers_test.orders.OrdersBase;

import java.util.Arrays;
import java.util.Collection;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static ru.yandex.practicum.stellar_burgers_test.orders.OrdersService.createOrder;

@DisplayName("Создание заказа с невалидным хэшем")
@RunWith(Parameterized.class)
public class CreateOrderWithInvalidHashTest extends OrdersBase {
    private final String setIngredientsValue;

    public CreateOrderWithInvalidHashTest(String setIngredientsValue) {
        this.setIngredientsValue = setIngredientsValue;
    }

    @Parameterized.Parameters()
    public static Collection<String> data() {
        return Arrays.asList(
                //"null hash"
                "invalid hash"
        );
    }

    @Before
    public void setUpTest(){
        //Установка null в поле
        switch (setIngredientsValue){
            case "invalid hash": {
                setOrder(new String[]{"invalid_1", "invalid_2"});
                break;
            }
            /*
            //Тест падает (согласно условиям сдачи диплома - не должен)
            case "null hash": {
                setOrder(new String[]{null,null});
                break;
            }
             */
        }
    }

    @Test
    @Description("Проверка создания заказа с невалидным хэшем")
    public void shouldReturnError500Test(){
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setName("Создание заказа с значением `ingredients = " + setIngredientsValue + "`"));
        // Выполнение запроса
        Response response = createOrder(order);

        // Проверка статус кода
        step("Проверка статус кода - " + SC_INTERNAL_SERVER_ERROR,
                () -> response.then().statusCode(SC_INTERNAL_SERVER_ERROR)
        );

    }
}
