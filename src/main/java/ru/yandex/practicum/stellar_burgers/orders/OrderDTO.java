package ru.yandex.practicum.stellar_burgers.orders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {
    private String[] ingredients;
}
