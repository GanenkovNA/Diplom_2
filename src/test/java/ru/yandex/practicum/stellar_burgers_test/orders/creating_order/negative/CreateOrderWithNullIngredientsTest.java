package ru.yandex.practicum.stellar_burgers_test.orders.creating_order.negative;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.stellar_burgers.orders.OrderResponseDTO;
import ru.yandex.practicum.stellar_burgers_test.orders.OrdersBase;

import java.util.Arrays;
import java.util.Collection;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static ru.yandex.practicum.infrastructure.allure_custom.PrettyJsonAttachmentInAllure.prettyJsonAttachment;
import static ru.yandex.practicum.stellar_burgers_test.orders.OrdersService.createOrderWithNullField;

@DisplayName("Создание заказа c null-значениями")
@RunWith(Parameterized.class)
public class CreateOrderWithNullIngredientsTest extends OrdersBase {
    private final String setIngredientsValue;

    public CreateOrderWithNullIngredientsTest(String setIngredientsValue) {
        this.setIngredientsValue = setIngredientsValue;
    }

    @Parameterized.Parameters()
    public static Collection<String> data() {
        return Arrays.asList(
                "null",
                "[]"
        );
    }

    @Before
    public void setUpTest(){
        //Установка null в поле
        switch (setIngredientsValue){
            case "null": {
                setOrder(null);
                break;
            }
            case "[]": {
                setOrder(new String[0]);
                break;
            }
        }
    }

    @Test
    @Description("Должно вернуться сообщение об ошибке")
    public void shouldReturnErrorWhenOrderHasNullIngredientsTest() throws JsonProcessingException {
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setName("Создание заказа с значением `ingredients = " + setIngredientsValue + "`"));

        // Выполнение запроса
        Response response = createOrderWithNullField(order);

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
