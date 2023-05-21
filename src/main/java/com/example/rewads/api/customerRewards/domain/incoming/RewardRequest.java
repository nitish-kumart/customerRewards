package com.example.rewads.api.customerRewards.domain.incoming;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class RewardRequest {

    private String customerId;
}
