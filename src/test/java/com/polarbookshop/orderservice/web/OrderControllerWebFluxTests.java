package com.polarbookshop.orderservice.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.polarbookshop.orderservice.order.domain.Order;
import com.polarbookshop.orderservice.order.domain.OrderService;
import com.polarbookshop.orderservice.order.web.OrderController;
import com.polarbookshop.orderservice.order.web.OrderRequest;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@WebFluxTest(OrderController.class)
public class OrderControllerWebFluxTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private OrderService orderService;

    @Test
    void whenBookNotAvailableThenRejectOrder() {
        var orderRequest = new OrderRequest("1234567890", 3);
        var expectedOrder = OrderService.buildRejectedOrder(
                orderRequest.isbn(), orderRequest.quantity());
        given(orderService.submitOrder(orderRequest.isbn(), orderRequest.quantity()))
                .willReturn(Mono.just(expectedOrder));

        webTestClient
                .post()
                .uri("/orders")
                .bodyValue(orderRequest)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Order.class).value(
                        actualOrder -> {
                            assertNotNull(actualOrder);
                            assertEquals(expectedOrder.getStatus(), actualOrder.getStatus());
                        });
    }
}
