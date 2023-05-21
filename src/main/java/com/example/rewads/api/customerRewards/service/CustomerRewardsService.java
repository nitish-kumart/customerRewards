package com.example.rewads.api.customerRewards.service;

import com.example.rewads.api.customerRewards.domain.outgoing.RewardsResponse;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface CustomerRewardsService {
    public List<RewardsResponse> getAllRewards();
    public RewardsResponse getCustomerRewards(String customerId) throws EntityNotFoundException;
}
