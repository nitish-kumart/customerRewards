package com.example.rewads.api.customerRewards.controller;

import com.example.rewads.api.customerRewards.domain.outgoing.Customer;
import com.example.rewads.api.customerRewards.domain.outgoing.Rewards;
import com.example.rewads.api.customerRewards.domain.outgoing.RewardsResponse;
import com.example.rewads.api.customerRewards.service.CustomerRewardsService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Month;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(value = CustomerRewardsController.class)
public class CustomerRewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRewardsService customerRewardsService;

    RewardsResponse rewardsResponse;

    @BeforeEach
    public void setUp() throws Exception {
        Map<Month, Integer> monthlyPoints = new TreeMap<>();
        monthlyPoints.put(Month.MARCH, 29);
        monthlyPoints.put(Month.APRIL, 150);

        Rewards rewards = new Rewards();
        rewards.setMonthlyPoints(monthlyPoints);
        rewards.setTotalPoints(179);

        Customer customer = new Customer();
        customer.setCustomerId("1");
        customer.setCustomerName("Brad Pitt");
        customer.setRewards(rewards);

        rewardsResponse = new RewardsResponse();
        rewardsResponse.setCustomer(customer);
    }

    @Test
    public void testGetAllRewards() throws Exception {
        when(customerRewardsService.getAllRewards()).thenReturn(Collections.singletonList(rewardsResponse));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customerRewards/rewards")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(),readMockCustomerRewardsResponse());
    }

    @Test
    public void testGetRewardsForCustomer() throws Exception {
        when(customerRewardsService.getCustomerRewards(anyString())).thenReturn(rewardsResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customerRewards/customerId")
                .param("customerId", "1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(),readMockCustomerRewardResponse());
    }

    private String readMockCustomerRewardsResponse() throws IOException {
        String path = "src/test/resources/customerRewardsMockResponse.json";
        return FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
    }

    private String readMockCustomerRewardResponse() throws IOException {
        String path = "src/test/resources/customerRewardMockResponse.json";
        return FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
    }
}
