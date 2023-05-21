package com.example.rewads.api.customerRewards.domain.outgoing;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class RewardsResponse implements Serializable {
    private Customer customer;
}
