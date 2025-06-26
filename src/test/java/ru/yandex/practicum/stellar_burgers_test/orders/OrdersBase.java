package ru.yandex.practicum.stellar_burgers_test.orders;

import io.qameta.allure.Step;
import ru.yandex.practicum.stellar_burgers.orders.OrderDTO;
import ru.yandex.practicum.stellar_burgers_test.StellarBurgerBase;

public class OrdersBase extends StellarBurgerBase {
    protected OrderDTO order;
    private static final String[] INGREDIENTS = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"};
    private static final String[] INVALID_INGREDIENTS = {"invalid_1", "invalid_2"};

    public void setOrder(String[] ingredients){
        order = OrderDTO.builder()
                .ingredients(ingredients)
                .build();
    }

    @Step("Создание данных тестового заказа")
    public void setValidOrder(){
        setOrder(INGREDIENTS);
    }

    @Step("Создание данных тестового заказа с невалидным хэшем")
    public void setInvalidOrder(){
        setOrder(INVALID_INGREDIENTS);
    }
}
