//package com.lawal.banji.springkitchen.customer;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//
//@Entity
//public class Customer implements CustomerEntity{
//
//    @Id
//    private Long id;
//    private String firstname;
//    private String lastname;
//    private String email;
//
//    @OneToMany(mappedBy = "customer_id")
//    private Phone phone;
//    @OneToMany(mappedBy = "customer_id")
//    private PostalAddress postalAddress;
//    @OneToMany(mappedBy = "customer_id")
//    private CreditCard creditCard;
//
//    @Override
//    public Long getId() {
//        return 0L;
//    }
//
//    @Override
//    public String getFirstName() {
//        return "";
//    }
//
//    @Override
//    public String getLastName() {
//        return "";
//    }
//
//    @Override
//    public String getEmail() {
//        return "";
//    }
//
//    @Override
//    public Phone getPhone() {
//        return null;
//    }
//
//    @Override
//    public CreditCard getCreditCard() {
//        return null;
//    }
//
//    @Override
//    public PostalAddress getPostalAddress() {
//        return null;
//    }
//
//    @Override
//    public void setId(Long id) {
//
//    }
//
//    @Override
//    public void setFirstName(String firstName) {
//
//    }
//
//    @Override
//    public void setLastName(String lastName) {
//
//    }
//
//    @Override
//    public void setEmail(String email) {
//
//    }
//
//    @Override
//    public void setPhone(Phone phone) {
//
//    }
//
//    @Override
//    public void setCreditCard(CreditCard creditCard) {
//
//    }
//
//    @Override
//    public void setPostalAddress(PostalAddress postalAddress) {
//
//    }
//}
