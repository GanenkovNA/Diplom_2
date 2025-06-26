package ru.yandex.practicum.stellar_burgers.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDTO {
    private boolean success;
    private String name;
    private Order order;

    @Data
    public static class Order{
        private int number;
    }
}
