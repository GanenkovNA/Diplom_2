package ru.yandex.practicum.burgers.orders.create;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrderAsUserResponseDTO {
    private boolean success;
    private String name;
    private Order order;

    @Data
    public static class Order{
        private List<Ingredient> ingredients;
        @JsonProperty("_id")
        private String underscoreId;
        private Owner owner;
        private String status;
        private String name;
        private String createdAt;
        private String updatedAt;
        private int number;
        private int price;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Owner{
        private String name;
        private String email;
        private String createdAt;
        private String updatedAt;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Ingredient {
        @JsonProperty("_id")
        private String underscoreId;
        private String name;
        private String type;
        private int proteins;
        private int fat;
        private int carbohydrates;
        private int calories;
        private int price;
        private String image;
        private String image_mobile;
        private String image_large;
        private int __v;
    }
}
