package ru.yandex.practicum.test.burgers;

import io.restassured.RestAssured;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;

public class StellarBurgerBase {
    private static final String BASE_URI="https://stellarburgers.nomoreparties.site";
    protected final SoftAssertions softly = new SoftAssertions();

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }
}
