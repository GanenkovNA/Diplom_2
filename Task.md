# Задание 2. Автотесты для API
Протестируй эндпоинты API для [Stellar Burgers](https://stellarburgers.nomoreparties.site/).

Пригодится [документация API](https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf). В ней описаны все эндпоинты сервиса. Тестировать нужно только те, которые указаны в задании. Всё остальное — просто для контекста.

**Создание пользователя:**
- [x] создать уникального пользователя;
- [x] создать пользователя, который уже зарегистрирован;
- [x] создать пользователя и не заполнить одно из обязательных полей.

**Логин пользователя:**
- [x] вход под существующим пользователем;
- [x] вход с неверным логином и паролем.

**Создание заказа:**
- [x] с авторизацией;
- [x] без авторизации;
- [x] с ингредиентами;
- [ ] без ингредиентов;
- [x] с неверным хешем ингредиентов.

## Что нужно сделать
1. Создай отдельный репозиторий для тестов API.
2. Создай Maven-проект.
3. Подключи JUnit 4, REST Assured и Allure.
4. Напиши тесты.
5. Сделай отчёт в Allure.
