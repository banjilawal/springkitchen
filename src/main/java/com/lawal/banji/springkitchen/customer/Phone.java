//package com.lawal.banji.springkitchen.customer;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//public class Phone {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(nullable = false, length = 3)
//    private String areaCode;
//
//    @Column(nullable = false, length = 3)
//    private String exchange;
//
//    @Column(nullable = false, length = 4)
//    private String number;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_id", nullable = false)
//    private Customer customer;
//}
