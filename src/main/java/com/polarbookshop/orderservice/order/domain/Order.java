package com.polarbookshop.orderservice.order.domain;

import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Table("Orders")
@Getter
@AllArgsConstructor
@Builder
public class Order {

    @Id
    private Long id;

    String bookIsbn;
    String bookName;
    Double bookPrice;
    Integer quantity;
    OrderStatus status;

    @CreatedDate
    Instant createdDate;

    @LastModifiedDate
    Instant lastModifiedDate;

    @Version
    int version;

}
