package com.example.study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@ToString(exclude = {"user" , "item"})
@ToString(exclude = {"orderGroup" , "item"})
@EntityListeners(AuditingEntityListener.class)
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String status;

    private LocalDateTime arrivalDate;

    private Integer quantity;

    private BigDecimal totalPrice;


    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

//    private Long orderGroupId;

    // OrderDetail N : 1 Item
    @ManyToOne
    private Item item;

//    private Long itemId;

    @ManyToOne
    private OrderGroup orderGroup;

//    private LocalDateTime orderAt;
//
//    // N : 1         디테일이 N, user가 1
//    @ManyToOne
//    private User user;   // user_id를 알아서 찾아감..
//
//    //  N : 1   디테일 N, item 1
//    @ManyToOne
//    private Item item;
}
