package ru.yandex.practicum.stellar_burgers_test.orders;

import io.qameta.allure.Step;
import ru.yandex.practicum.stellar_burgers.orders.OrderDTO;
import ru.yandex.practicum.stellar_burgers_test.StellarBurgerBase;

public class OrdersBase extends StellarBurgerBase {
    protected OrderDTO order;
    private static final String[] INGREDIENTS = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"};

    @Step("Создание данных тестового заказа")
    public void setOrder(){
        order = OrderDTO.builder()
                .ingredients(INGREDIENTS)
                .build();
    }
}
