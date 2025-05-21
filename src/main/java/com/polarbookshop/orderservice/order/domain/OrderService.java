package com.polarbookshop.orderservice.order.domain;

import org.springframework.stereotype.Service;
import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final BookClient bookClient;

    public OrderService(OrderRepository orderRepository, BookClient bookClient) {
        this.orderRepository = orderRepository;
        this.bookClient = bookClient;
    }

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> submitOrder(String isbn, int quantity) {
        return bookClient.getBookByIsbn(isbn)
                .map(book -> buildAcceptedOrder(book, quantity))
                .defaultIfEmpty(buildRejectedOrder(isbn, quantity))
                .flatMap(orderRepository::save);
    }

    public static Order buildAcceptedOrder(Book book, int quantity) {
        return Order.builder()
                .bookIsbn(book.isbn())
                .bookName(book.title())
                .bookPrice(book.price())
                .quantity(quantity)
                .status(OrderStatus.ACCEPTED)
                .build();
    }

    public static Order buildRejectedOrder(String bookIsbn, int quantity) {
        return Order.builder()
                .bookIsbn(bookIsbn)
                .quantity(quantity)
                .status(OrderStatus.REJECTED)
                .build();
    }
}
