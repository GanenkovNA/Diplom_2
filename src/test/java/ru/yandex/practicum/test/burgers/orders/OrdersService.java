package ru.yandex.practicum.test.burgers.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import ru.yandex.practicum.infrastructure.rest.ApiClient;
import ru.yandex.practicum.burgers.orders.OrderDTO;
import ru.yandex.practicum.burgers.orders.create.CreateOrderRequestDTO;

public class OrdersService {
    public static final String CREATE_PATH = "/api/orders";

    public static Response createOrder(OrderDTO order){
        CreateOrderRequestDTO orderData = CreateOrderRequestDTO.builder()
                .ingredients(order.getIngredients())
                .build();

        return ApiClient.post(CREATE_PATH,
                orderData,
                "Создание заказа");
    }

    public static Response createOrderWithNullField(OrderDTO order) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Игнорируем @JsonInclude и сериализуем null
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        CreateOrderRequestDTO orderData = CreateOrderRequestDTO.builder()
                .ingredients(order.getIngredients())
                .build();
        String jsonWithNulls = mapper.writeValueAsString(orderData);

        return ApiClient.post(CREATE_PATH,
                jsonWithNulls,
                "Создание заказа");
    }

    public static Response createOrderAsUser(OrderDTO order, String accessToken){
        CreateOrderRequestDTO orderData = CreateOrderRequestDTO.builder()
                .ingredients(order.getIngredients())
                .build();

        return ApiClient.post(CREATE_PATH,
                accessToken,
                orderData,
                "Создание заказа");
    }
}
