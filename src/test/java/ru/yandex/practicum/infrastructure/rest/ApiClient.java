package ru.yandex.practicum.infrastructure.rest;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.net.SocketTimeoutException;
import java.util.function.Supplier;

import static io.restassured.RestAssured.given;

public class ApiClient {
    // Настройка таймаутов (5 сек на подключение, 10 сек на ответ)
    static {
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(new HttpClientConfig()
                        .setParam("http.connection.timeout", 5000)
                        .setParam("http.socket.timeout", 10000)
                );
    }

    // Общий метод для отправки запросов с обработкой ошибок
    private static Response sendRequest(Supplier<Response> requestSupplier, String actionName){
        try{
            return requestSupplier.get();
        } catch (Exception e){
            Throwable cause = e.getCause();
            if (cause instanceof SocketTimeoutException){
                throw new RuntimeException("Таймаут при выполнении: " + actionName, cause);
            } else {
                throw new RuntimeException("Ошибка в запросе: " + actionName, e);
            }
        }
    }

    // GET-запрос
    public static Response get(String path, String actionName) {
        return sendRequest(() ->
                        given()
                                .contentType(ContentType.JSON)
                                .get(path),
                actionName
        );
    }

    // POST-запрос
    public static Response post(String path, Object body, String actionName) {
        return sendRequest(() ->
                        given()
                                .contentType(ContentType.JSON)
                                .body(body)
                                .post(path),
                actionName
        );
    }

    // POST-запрос с токеном
    public static Response post(String path, String accessToken, Object body, String actionName) {
        return sendRequest(() ->
                        given()
                                .contentType(ContentType.JSON)
                                .header("Authorization", accessToken)
                                .body(body)
                                .post(path),
                actionName
        );
    }

    // DELETE-запрос
    public static Response delete(String path, String accessToken, String actionName) {
        return sendRequest(() ->
                        given()
                                .contentType(ContentType.JSON)
                                .header("Authorization", accessToken)
                                .delete(path),
                actionName
        );
    }

    // PUT-запрос
    public static Response put(String path, Object body, String actionName) {
        return sendRequest(() ->
                        given()
                                .contentType(ContentType.JSON)
                                .body(body)
                                .put(path),
                actionName
        );
    }
}
