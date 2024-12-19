//package com.lawal.banji.springkitchen.customer;
//
//import jakarta.persistence.*;
//
//@Entity
//public class CreditCard implements PaymentMethod {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String number;
//    private String expirationDate;
//    private String cardType;
//    private String cvv;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_id", nullable = false)
//    private Customer customer;
//
//}
