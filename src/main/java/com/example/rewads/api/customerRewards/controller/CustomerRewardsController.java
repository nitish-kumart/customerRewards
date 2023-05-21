package com.example.rewads.api.customerRewards.controller;

import com.example.rewads.api.customerRewards.domain.outgoing.RewardsResponse;
import com.example.rewads.api.customerRewards.service.CustomerRewardsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/customerRewards")
@Slf4j
public class CustomerRewardsController {
    Logger logger = LoggerFactory.getLogger(CustomerRewardsController.class);
    @Autowired
    CustomerRewardsService customerRewardsService;

    /**
     * Api to return rewards for all customer transactions in past 3 months.
     * @return ResponseEntity<List<RewardsResponse>>
     */
    @GetMapping("/rewards")
    public ResponseEntity<List<RewardsResponse>> getAllRewards() {
        List<RewardsResponse> rewardsResponseList = customerRewardsService.getAllRewards();
        logger.info("RewardResponse"+rewardsResponseList.toString());
        return new ResponseEntity<>(rewardsResponseList, HttpStatus.OK);
    }

    /**
     * Api to return all rewards for a customer in the past 3 months.
     * @param customerId
     * @return ResponseEntity<RewardsResponse>
     * @throws EntityNotFoundException
     */
    @GetMapping("/customerId")
    public ResponseEntity<RewardsResponse> getRewardsForCustomer(@RequestParam String customerId) throws EntityNotFoundException {
        logger.info("Get rewards for customer Id {}", customerId);
        RewardsResponse rewardsResponse = customerRewardsService.getCustomerRewards(customerId);
        logger.info("Customer reward response {}", rewardsResponse);
        return new ResponseEntity<>(rewardsResponse, HttpStatus.OK);
    }
}
