package com.example.rewads.api.customerRewards.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    public void setUp() throws Exception {

        CustomerTransaction customerTransaction1 = CustomerTransaction.builder()
                .transactionId(1)
                .transactionDate(LocalDateTime.now().minusMonths(2))
                .amount(new BigDecimal("79.80")).build();

        CustomerTransaction customerTransaction2 = CustomerTransaction.builder()
                .transactionId(2)
                .transactionDate(LocalDateTime.now().minusMonths(1))
                .amount(new BigDecimal("150.80")).build();

        customer = Customer.builder()
                .customerName("Brad Pitt")
                .customerTransactionList(Arrays.asList(customerTransaction1,customerTransaction2))
                .build();
        customerRepository.save(customer);
    }

    @Test
    public void findAllCustomers(){
        List<Customer> customerList = customerRepository.findAll();

        assertEquals(customerList.size(), 1);
        Customer customer1 = customerList.stream().findFirst().get();
        assertNotNull(customer1.getCustomerId());
        assertTrue(customer1.getCustomerId() > 0);
        assertEquals(customer1.getCustomerName(),"Brad Pitt");
        assertEquals(customer1.getCustomerTransactionList().size(), 2);
    }

    @Test
    public void findCustomersById() throws SQLException {
        Customer retrieveCustomer = customerRepository.findById(customer.getCustomerId()).orElseThrow(() -> new SQLException("Record not Found"));

        assertNotNull(retrieveCustomer.getCustomerId());
        assertTrue(retrieveCustomer.getCustomerId() > 0);
        assertEquals(retrieveCustomer.getCustomerName(),"Brad Pitt");
        assertEquals(retrieveCustomer.getCustomerTransactionList().size(), 2);
    }

}
