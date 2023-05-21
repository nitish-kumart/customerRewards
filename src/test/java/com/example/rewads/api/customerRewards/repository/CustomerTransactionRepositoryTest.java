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
public class CustomerTransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;
    private LocalDateTime t1Date;
    private LocalDateTime t2Date;

    CustomerTransaction customerTransaction1;
    CustomerTransaction customerTransaction2;

    @BeforeEach
    public void setUp() throws Exception {
        t1Date = LocalDateTime.now().minusMonths(2);
        t2Date = LocalDateTime.now();

        customerTransaction1 = CustomerTransaction.builder()
                .transactionDate(t1Date)
                .amount(new BigDecimal("79.80")).build();

        customerTransaction2 = CustomerTransaction.builder()
                .transactionDate(t2Date)
                .amount(new BigDecimal("150.80")).build();

        transactionRepository.saveAll(Arrays.asList(customerTransaction1,customerTransaction2));
    }

    @Test
    public void findAllTransactions(){
        List<CustomerTransaction> transactionList = transactionRepository.findAll();

        assertEquals(transactionList.size(), 2);
        CustomerTransaction customerTransaction = transactionList.stream().findFirst().get();
        assertNotNull(customerTransaction.getTransactionId());
        assertTrue(customerTransaction.getTransactionId() > 0);
        assertEquals(customerTransaction.getTransactionDate(),t1Date);
        assertEquals(customerTransaction.getAmount().toString(),"79.80");
    }

    @Test
    public void findCustomersById() throws SQLException {
        CustomerTransaction customerTransaction = transactionRepository.findById(customerTransaction2.getTransactionId()).orElseThrow(() -> new SQLException("Record not Found"));

        assertNotNull(customerTransaction.getTransactionId());
        assertTrue(customerTransaction.getTransactionId() > 0);
        assertEquals(customerTransaction.getAmount().toString(),"150.80");
        assertEquals(customerTransaction.getTransactionDate(), t2Date);
    }
}
