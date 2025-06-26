package ru.yandex.practicum.infrastructure.allure_custom;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class CatchTearDownFail {
    public static void catchTearDownFail(Runnable tearDown){
        try{
            tearDown.run();
        } catch (AssertionError e) {
            Allure.getLifecycle().updateStep(step -> step.setStatus(Status.BROKEN));
            Allure.addAttachment("Ошибка", "text/plain", e.getMessage());
        }
    }
}
