package com.example.shop;

import com.example.statemachine.api.Event;
import com.example.statemachine.api.State;
import com.example.statemachine.impl.Create;

public class ShopLifeCycle {

    /** События процесса покупки */
    public enum ShopEvent implements Event {
        ADD_ITEM,        // добавить товар
        CHECKOUT,        // перейти к оформлению
        PAY,             // инициировать оплату
        PAID,            // оплата подтверждена
        DELIVERED;       // заказ доставлен

        @Override public String getName() { return name(); }
        @Override public String getDescription() { return null; }
    }

    // Состояния
    // START
    private final State<ShopContext> START = Create.state(s -> s
            .name("start")
            .description("Начало")
            // START --(ADD_ITEM)--> FILL_CART
            .transition(t -> t.event(ShopEvent.ADD_ITEM).nextState(this::fillCart))
    );

    // FILL_CART
    private final State<ShopContext> FILL_CART = Create.state(s -> s
            .name("fill-cart")
            .description("Заполнение корзины")
            .entryAction(a -> a.name("fill-cart-entry").activity(ctx -> ctx))
            .exitAction(a -> a.name("fill-cart-exit").activity(ctx -> ctx))
            // петля по ADD_ITEM
            .transition(t -> t.event(ShopEvent.ADD_ITEM).nextState(this::fillCart))
            // FILL_CART --(CHECKOUT)--> CHECKOUT
            .transition(t -> t.event(ShopEvent.CHECKOUT).nextState(this::checkout))
    );

    // CHECKOUT
    private final State<ShopContext> CHECKOUT = Create.state(s -> s
            .name("checkout")
            .description("Оформление заказа")
            .entryAction(a -> a.name("checkout-entry").activity(ctx -> ctx))
            .exitAction(a -> a.name("checkout-exit").activity(ctx -> ctx))
            // CHECKOUT --(PAY)--> PAYMENT
            .transition(t -> t.event(ShopEvent.PAY).nextState(this::payment))
    );

    // PAYMENT
    private final State<ShopContext> PAYMENT = Create.state(s -> s
            .name("payment")
            .description("Оплата заказа")
            .entryAction(a -> a.name("payment-entry").activity(ctx -> ctx))
            .exitAction(a -> a.name("payment-exit").activity(ctx -> ctx))
            // PAYMENT --(PAID)--> DELIVERY
            .transition(t -> t.event(ShopEvent.PAID).nextState(this::delivery))
    );

    // DELIVERY
    private final State<ShopContext> DELIVERY = Create.state(s -> s
            .name("delivery")
            .description("Доставка заказа")
            .entryAction(a -> a.name("delivery-entry").activity(ctx -> ctx))
            // DELIVERY --(DELIVERED)--> END
            .transition(t -> t.event(ShopEvent.DELIVERED).nextState(this::end))
    );

    // END
    private final State<ShopContext> END = Create.state(s -> s
            .name("end")
            .description("Завершение процесса")
    );

    /** Точка входа для тестов/клиентов */
    public State<ShopContext> start() { return START; }
    public State<ShopContext> end()   { return END;   }

    private State<ShopContext> fillCart() { return FILL_CART; }
    private State<ShopContext> checkout() { return CHECKOUT; }
    private State<ShopContext> payment()  { return PAYMENT; }
    private State<ShopContext> delivery() { return DELIVERY; }

    public static final ShopLifeCycle LIFE_CYCLE = new ShopLifeCycle();
}
