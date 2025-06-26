package ru.yandex.practicum.stellar_burgers.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegistrationRequestDto {
    private String email;
    private String password;
    private String name;
}
