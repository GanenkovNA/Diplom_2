package ru.yandex.practicum.stellar_burgers_test.orders;

import io.restassured.response.Response;
import ru.yandex.practicum.infrastructure.ApiClient;
import ru.yandex.practicum.stellar_burgers.orders.OrderDTO;
import ru.yandex.practicum.stellar_burgers.orders.OrderRequestDTO;

public class OrdersService {
    public static final String CREATE_PATH = "/api/orders";

    public static Response createOrder(OrderDTO order){
        OrderRequestDTO orderData = OrderRequestDTO.builder()
                .ingredients(order.getIngredients())
                .build();

        return ApiClient.post(CREATE_PATH,
                orderData,
                "Создание заказа");
    }

    public static Response createOrderAsUser(OrderDTO order){
        OrderRequestDTO orderData = OrderRequestDTO.builder()
                .ingredients(order.getIngredients())
                .build();

        return ApiClient.post(CREATE_PATH,
                order.getAccessToken(),
                orderData,
                "Создание заказа");
    }
}
