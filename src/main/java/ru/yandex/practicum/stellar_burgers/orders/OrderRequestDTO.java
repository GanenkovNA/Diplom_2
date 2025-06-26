package ru.yandex.practicum.stellar_burgers.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequestDTO {
    private String[] ingredients;
}
