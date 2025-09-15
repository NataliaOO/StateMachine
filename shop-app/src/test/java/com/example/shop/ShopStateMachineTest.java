package com.example.shop;

import com.example.statemachine.api.State;
import com.example.statemachine.impl.Create;
import com.example.statemachine.impl.StateMachine;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.shop.ShopLifeCycle.LIFE_CYCLE;
import static com.example.shop.ShopLifeCycle.ShopEvent.*;
import static org.junit.Assert.assertEquals;

public class ShopStateMachineTest {
    @Test
    public void givenLifeCycle_whenStart_thenStateIsStart() {
        // Given
        ShopContext ctx = new ShopContext()
                .userId(42L)
                .state(LIFE_CYCLE.start());

        // Then
        assertEquals("start", ctx.getState().getName());
    }

    @Test
    public void givenStart_whenAddItem_thenFillCart() {
        // Given
        ShopContext ctx = new ShopContext()
                .userId(42L)
                .state(LIFE_CYCLE.start());

        // When
        State<ShopContext> fillCart = StateMachine.process(ADD_ITEM, ctx);

        // Then
        assertEquals("fill-cart", fillCart.getName());
    }

    @Test
    public void givenFillCart_whenAddItemAgain_thenStayInFillCart() {
        // Given
        ShopContext ctx = new ShopContext().userId(42L).state(LIFE_CYCLE.start());
        StateMachine.process(ADD_ITEM, ctx);

        // When
        State<ShopContext> again = StateMachine.process(ADD_ITEM, ctx);

        // Then
        assertEquals("fill-cart", again.getName());
    }

    @Test
    public void givenStart_whenUnsupportedEvent_thenStayInStart() {
        // Given
        ShopContext ctx = new ShopContext()
                .userId(42L)
                .state(LIFE_CYCLE.start());

        // When: событие, для которого нет перехода из START
        State<ShopContext> after = StateMachine.process(CHECKOUT, ctx);

        // Then: остаёмся в START
        assertEquals("start", after.getName());
    }

    @Test
    public void givenFillCartWithExit_whenCheckout_thenExitBeforeEntry() {
        // Given
        List<String> calls = new ArrayList<>();

        State<ShopContext> CHECKOUT = Create.state(s -> s
                .name("checkout")
                .entryAction(a -> a.activity(ctx -> { calls.add("entry"); return ctx; }))
        );

        State<ShopContext> FILL_CART = Create.state(s -> s
                .name("fill-cart")
                .exitAction(a -> a.activity(ctx -> { calls.add("exit"); return ctx; }))
                .transition(t -> t
                        .event(ShopLifeCycle.ShopEvent.CHECKOUT)
                        .nextState(() -> CHECKOUT)
                )
        );

        ShopContext ctx = new ShopContext()
                .userId(42L)
                .state(FILL_CART);

        // When
        State<ShopContext> next = StateMachine.process(ShopLifeCycle.ShopEvent.CHECKOUT, ctx);

        // Then
        assertEquals("checkout", next.getName());
        assertEquals(Arrays.asList("exit", "entry"), calls);
    }

    @Test
    public void givenFillCart_whenCheckout_thenCheckout() {
        // Given: уже в корзине
        ShopContext ctx = new ShopContext()
                .userId(42L)
                .state(LIFE_CYCLE.start());
        StateMachine.process(ADD_ITEM, ctx);

        // When: оформляем заказ
        State<ShopContext> checkout = StateMachine.process(CHECKOUT, ctx);

        // Then: перешли в checkout
        assertEquals("checkout", checkout.getName());
    }

    @Test
    public void givenCheckout_whenPay_thenPayment() {
        // Given: пользователь уже в оформлении
        ShopContext ctx = new ShopContext()
                .userId(42L)
                .state(LIFE_CYCLE.start());
        StateMachine.process(ADD_ITEM, ctx);   // START -> FILL_CART
        StateMachine.process(CHECKOUT, ctx);   // FILL_CART -> CHECKOUT

        // When: оплачивает
        State<ShopContext> payment = StateMachine.process(PAY, ctx);

        // Then: переходим в состояние оплаты
        assertEquals("payment", payment.getName());
    }

    @Test
    public void givenPayment_whenPaid_thenDeliveryState() {
        // Given
        ShopContext ctx = new ShopContext().userId(42L).state(LIFE_CYCLE.start());
        StateMachine.process(ADD_ITEM, ctx);   // START -> FILL_CART
        StateMachine.process(CHECKOUT, ctx);   // FILL_CART -> CHECKOUT
        StateMachine.process(PAY, ctx);        // CHECKOUT -> PAYMENT

        // When
        State<ShopContext> delivery = StateMachine.process(PAID, ctx);

        // Then
        assertEquals("delivery", delivery.getName());
    }

    @Test
    public void givenDelivery_whenDelivered_thenEndState() {
        // Given
        ShopContext ctx = new ShopContext().userId(42L).state(LIFE_CYCLE.start());
        StateMachine.process(ADD_ITEM, ctx);    // START -> FILL_CART
        StateMachine.process(CHECKOUT, ctx);    // FILL_CART -> CHECKOUT
        StateMachine.process(PAY, ctx);         // CHECKOUT -> PAYMENT
        StateMachine.process(PAID, ctx);        // PAYMENT -> DELIVERY

        // When
        State<ShopContext> end = StateMachine.process(DELIVERED, ctx);

        // Then
        assertEquals("end", end.getName());
    }

    @Test
    public void givenEnd_whenAnyEvent_thenStayInEnd() {
        // Given: идём до конца
        ShopContext ctx = new ShopContext().userId(42L).state(LIFE_CYCLE.start());
        StateMachine.process(ADD_ITEM, ctx);
        StateMachine.process(CHECKOUT, ctx);
        StateMachine.process(PAY, ctx);
        StateMachine.process(PAID, ctx);
        StateMachine.process(DELIVERED, ctx);

        // When
        State<ShopContext> after = StateMachine.process(ADD_ITEM, ctx);

        // Then
        assertEquals("end", after.getName());
    }
}
