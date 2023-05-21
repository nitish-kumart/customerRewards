package com.example.rewads.api.customerRewards;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class TestPractice {
    @Test
    void name() {

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        Instant i = Instant.now();
        System.out.println(i);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(i, ZoneId.of("America/New_York"));
        System.out.println(zonedDateTime);


    }
}