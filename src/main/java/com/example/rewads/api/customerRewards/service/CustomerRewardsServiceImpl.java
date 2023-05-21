package com.example.rewads.api.customerRewards.service;

import com.example.rewads.api.customerRewards.domain.outgoing.Rewards;
import com.example.rewads.api.customerRewards.domain.outgoing.RewardsResponse;
import com.example.rewads.api.customerRewards.repository.Customer;
import com.example.rewads.api.customerRewards.repository.CustomerRepository;
import com.example.rewads.api.customerRewards.repository.CustomerTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Month;
import java.util.*;

@Service
public class CustomerRewardsServiceImpl implements CustomerRewardsService {
    Logger logger = LoggerFactory.getLogger(CustomerRewardsServiceImpl.class);

    @Autowired
    CustomerRepository customerRepository;

    /**
     * Method to get rewards for all customers in the past 3 months.
     * @return List<RewardsResponse>
     */
    @Override
    public List<RewardsResponse> getAllRewards() {
        List<Customer> customers = customerRepository.findAll();
        List<RewardsResponse> rewardsResponses = fetchCustomerRewards(customers);
        logger.info("List Rewards Response {}", rewardsResponses);
        return rewardsResponses;
    }

    /**
     * Method to return rewards from the past 3 months for a particular customer id.
     * @param customerId
     * @return RewardsResponse
     * @throws EntityNotFoundException
     */
    @Override
    public RewardsResponse getCustomerRewards(String customerId) throws EntityNotFoundException {
        logger.info("Fetching customer rewards from DB for customerId {}", customerId);
        Customer customer = customerRepository.findById(Integer.parseInt(customerId))
                .orElseThrow(() -> new EntityNotFoundException());

        RewardsResponse rewardsResponse = fetchCustomerRewards(Collections.singletonList(customer)).get(0);
        logger.info("Rewards fetched {}", rewardsResponse);
        return rewardsResponse;
    }

    /**
     * Takes in list of customers and sorts them according to the month of transactions and
     * calculates rewards per month as well total rwards for the past 3 months.
     * @param customers
     * @return List<RewardsResponse>
     */
    private List<RewardsResponse> fetchCustomerRewards(List<Customer> customers) {
        List<RewardsResponse> rewardsResponses = new ArrayList<>();
        customers.forEach(customer -> {
            TreeMap<Month, Integer> rewardsPerMonthMap = new TreeMap<>(calculateMonthlyRewards(customer.getCustomerTransactionList()));
            com.example.rewads.api.customerRewards.domain.outgoing.Customer customer1 = new com.example.rewads.api.customerRewards.domain.outgoing.Customer();
            customer1.setCustomerId(customer.getCustomerId().toString());
            customer1.setCustomerName(customer.getCustomerName());

            Rewards rewards = new Rewards();
            rewards.setMonthlyPoints(rewardsPerMonthMap);
            rewards.setTotalPoints(rewards.getMonthlyPoints()
                    .values()
                    .stream()
                    .mapToInt(Integer::intValue).sum());
            customer1.setRewards(rewards);

            RewardsResponse rewardsResponse = new RewardsResponse();
            rewardsResponse.setCustomer(customer1);
            rewardsResponses.add(rewardsResponse);
        });
        return rewardsResponses;
    }

    /**
     * Takes in list of customer transactions and returns and returns a map
     * which has customer reward points per month.
     * @param customerTransactionList
     * @return Map<Month, Integer>
     */
    private Map<Month, Integer> calculateMonthlyRewards(List<CustomerTransaction> customerTransactionList) {
        HashMap<Month, Integer> rewardsMap = new HashMap<>();
        customerTransactionList.forEach(customerTransaction -> {

            switch (customerTransaction.getTransactionDate().getMonth()) {
                case JANUARY:
                    calculateTotalAmountPerMonth(rewardsMap, Month.JANUARY, customerTransaction.getAmount().intValue());
                    break;
                case FEBRUARY:
                    calculateTotalAmountPerMonth(rewardsMap, Month.FEBRUARY, customerTransaction.getAmount().intValue());
                    break;
                case MARCH:
                    calculateTotalAmountPerMonth(rewardsMap, Month.MARCH, customerTransaction.getAmount().intValue());
                    break;
                case APRIL:
                    calculateTotalAmountPerMonth(rewardsMap, Month.APRIL, customerTransaction.getAmount().intValue());
                    break;
                case MAY:
                    calculateTotalAmountPerMonth(rewardsMap, Month.MAY, customerTransaction.getAmount().intValue());
                    break;
                case JUNE:
                    calculateTotalAmountPerMonth(rewardsMap, Month.JUNE, customerTransaction.getAmount().intValue());
                    break;
                case JULY:
                    calculateTotalAmountPerMonth(rewardsMap, Month.JULY, customerTransaction.getAmount().intValue());
                    break;
                case AUGUST:
                    calculateTotalAmountPerMonth(rewardsMap, Month.AUGUST, customerTransaction.getAmount().intValue());
                    break;
                case SEPTEMBER:
                    calculateTotalAmountPerMonth(rewardsMap, Month.SEPTEMBER, customerTransaction.getAmount().intValue());
                    break;
                case OCTOBER:
                    calculateTotalAmountPerMonth(rewardsMap, Month.OCTOBER, customerTransaction.getAmount().intValue());
                    break;
                case NOVEMBER:
                    calculateTotalAmountPerMonth(rewardsMap, Month.NOVEMBER, customerTransaction.getAmount().intValue());
                    break;
                case DECEMBER:
                    calculateTotalAmountPerMonth(rewardsMap, Month.DECEMBER, customerTransaction.getAmount().intValue());
                    break;
            }
        });
        return rewardsMap;
    }

    /**
     * Calculates the cumulative monthly reward points and returns the points.
     * @param rewardsMap
     * @param month
     * @param amount
     * @return Integer
     */
    private Integer calculateTotalAmountPerMonth(HashMap<Month, Integer> rewardsMap, Month month, int amount) {
        if (rewardsMap.containsKey(month)) {
            int points = rewardsMap.get(month);
            points += calculateRewardPoints(amount);
            return rewardsMap.put(month, points);
        } else {
            return rewardsMap.put(month, calculateRewardPoints(amount));
        }
    }

    /**
     * Adjusts rewards points if it's a charge transaction or else if a refund has been issued.
     * @param amount
     * @return int
     */
    private int calculateRewardPoints(int amount) {
        if (amount > 0) {
            return derivePoints(amount);
        } else {
            return -derivePoints(Math.abs(amount));
        }
    }

    /**
     * Calculates point using the following logic:
     * A customer receives 2 points for every dollar spent over $100 in each transaction,
     * plus 1 point for every dollar spent between $50 and $100 in each transaction.
     * (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
     * @param amount
     * @return int
     */
    private int derivePoints(int amount) {
        if (amount > 100) {
            return (amount - 100) * 2 + 50;
        } else if (amount > 50 && amount <= 100) {
            return amount - 50;
        } else {
            return 0;
        }
    }
}
