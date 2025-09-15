# State Machine Starter + Shop Example

Лёгкая реализация конечного автомата (FSM) на Java с DSL на лямбдах и Spring Boot автоконфигурацией.
Проект состоит из стартер-библиотеки (ядро FSM + AutoConfiguration) и примерного доменного модуля интернет-магазина.

Диаграмма состояний магазина

### Диаграмма 1 — абстрактная «машина состояний»
```mermaid
stateDiagram-v2
    [*] --> START

    START  --> STATE1: event1 / action1
    STATE1 --> STATE2: event2 / action2
    STATE2 --> STATE3: event4 / action4
    STATE3 --> [*]:    event6 / action6

    STATE2 --> STATE1: event3 / action3
    STATE3 --> STATE2: event5 / action5
```

### Диаграмма 2 — процесс покупки в интернет-магазине
```mermaid
stateDiagram
    [*] --> start

    start --> FILL_CART: добавление товара / пересчёт чека

    state "Заполнение корзины" as FILL_CART {
        [*] --> Fill
        Fill --> Fill: добавление товара / пересчёт чека
    }

    FILL_CART --> CHECKOUT: оформить заказ / резерв на складе
    state "Оформление заказа" as CHECKOUT
    CHECKOUT --> PAYMENT: оплатить заказ
    state "Оплата заказа" as PAYMENT
    PAYMENT --> DELIVERY: оплата произведена / передать в доставку
    state "Доставка заказа" as DELIVERY
    DELIVERY --> end: заказ доставлен

    end --> [*]
```