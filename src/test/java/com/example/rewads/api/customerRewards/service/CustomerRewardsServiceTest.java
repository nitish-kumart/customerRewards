package com.example.rewads.api.customerRewards.service;

import com.example.rewads.api.customerRewards.domain.outgoing.Rewards;
import com.example.rewads.api.customerRewards.domain.outgoing.RewardsResponse;
import com.example.rewads.api.customerRewards.repository.Customer;
import com.example.rewads.api.customerRewards.repository.CustomerRepository;
import com.example.rewads.api.customerRewards.repository.CustomerTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerRewardsServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerRewardsServiceImpl customerRewardsService;

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
                .customerId(1)
                .customerName("Brad Pitt")
                .customerTransactionList(Arrays.asList(customerTransaction1,customerTransaction2))
                .build();
    }

    @Test
    public void getAllRewardsTest() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
        List<RewardsResponse> rewardsResponseList = customerRewardsService.getAllRewards();

        assertEquals(rewardsResponseList.size(), 1);

        RewardsResponse rewardsResponse = rewardsResponseList.get(0);
        assertEquals(rewardsResponse.getCustomer().getCustomerId(), "1");

        Rewards rewards = rewardsResponse.getCustomer().getRewards();
        assertEquals(rewards.getMonthlyPoints().get(Month.MARCH).intValue(), 29);
        assertEquals(rewards.getTotalPoints().intValue(), 179);
    }

    @Test
    public void getCustomerRewardsTest() throws EntityNotFoundException {
        when(customerRepository.findById(anyInt())).thenReturn(ofNullable(customer));
        RewardsResponse rewardsResponse = customerRewardsService.getCustomerRewards(customer.getCustomerId().toString());

        assertEquals(rewardsResponse.getCustomer().getCustomerId(), "1");

        Rewards rewards = rewardsResponse.getCustomer().getRewards();
        assertEquals(rewards.getMonthlyPoints().get(Month.MARCH).intValue(), 29);
        assertEquals(rewards.getTotalPoints().intValue(), 179);
    }

    @Test
    public void getCustomerRewardsExceptionTest(){
        when(customerRepository.findById(anyInt())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> customerRewardsService.getCustomerRewards(customer.getCustomerId().toString()));
    }
}
