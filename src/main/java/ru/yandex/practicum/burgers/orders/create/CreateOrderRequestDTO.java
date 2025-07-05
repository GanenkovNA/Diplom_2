package ru.yandex.practicum.burgers.orders.create;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrderRequestDTO {
    private String[] ingredients;
}
