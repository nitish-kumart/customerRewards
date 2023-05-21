package com.example.rewads.api.customerRewards.domain.outgoing;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Month;
import java.util.Map;

@Component
@Data
public class Rewards implements Serializable {
    private Map<Month, Integer> monthlyPoints;
    private Integer totalPoints;
}
