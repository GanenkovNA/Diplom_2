package ru.yandex.practicum.stellar_burgers_test;

import io.restassured.RestAssured;
import org.junit.Before;

public class StellarBurgerBase {
    private static final String BASE_URI="https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }
}
