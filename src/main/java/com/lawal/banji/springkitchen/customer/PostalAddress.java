//package com.lawal.banji.springkitchen.customer;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//public class PostalAddress {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(nullable = false, length = 100)
//    private String street;
//
//    @Column(nullable = false, length = 100)
//    private String city;
//
//    @Column(nullable = false, length = 100)
//    String state;
//
//    @Column(nullable = false, length = 5)
//    String zip;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_id", nullable = false)
//    private Customer customer;
//}
