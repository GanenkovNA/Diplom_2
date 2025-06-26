package ru.yandex.practicum.infrastructure.allure_custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.qameta.allure.Allure;

public class PrettyJsonAttachmentInAllure {
    public static void prettyJsonAttachment(Object responseBody) {
        try{
            Allure.addAttachment(
                    "Response (JSON)",
                    "application/json",
                    new ObjectMapper()
                            .enable(SerializationFeature.INDENT_OUTPUT)
                            .writeValueAsString(responseBody)
            );
        } catch (JsonProcessingException e){
            System.out.println("JSON parsing error:\n" + responseBody);
        }
    }
}
