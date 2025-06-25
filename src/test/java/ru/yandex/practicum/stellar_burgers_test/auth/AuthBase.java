package ru.yandex.practicum.stellar_burgers_test.auth;

import org.junit.Before;
import ru.yandex.practicum.stellar_burgers_test.StellarBurgerBase;
import ru.yandex.practicum.stellar_burgers.auth.UserDto;

public class AuthBase extends StellarBurgerBase {
    protected UserDto user;

    @Before
    public void createTestUser(){
        user = UserDto.builder()
                .email("bobr@dobr.net")
                .password("password")
                .name("userName")
                .build();
    }
}
