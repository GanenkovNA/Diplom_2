package ru.yandex.practicum.test.burgers.orders;

import io.qameta.allure.Step;
import ru.yandex.practicum.burgers.orders.OrderDTO;
import ru.yandex.practicum.test.burgers.StellarBurgerBase;

public class OrdersBase extends StellarBurgerBase {
    protected OrderDTO order;
    private static final String[] INGREDIENTS = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"};

    public void setOrder(String[] ingredients){
        order = OrderDTO.builder()
                .ingredients(ingredients)
                .build();
    }

    @Step("Создание данных тестового заказа")
    public void setValidOrder(){
        setOrder(INGREDIENTS);
    }
}
