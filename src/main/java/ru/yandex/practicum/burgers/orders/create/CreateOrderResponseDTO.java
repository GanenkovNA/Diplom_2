package ru.yandex.practicum.burgers.orders.create;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrderResponseDTO {
    private boolean success;
    private String name;
    private Order order;
    private String message;

    @Data
    public static class Order{
        private int number;
    }
}
