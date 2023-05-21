package com.example.rewads.api.customerRewards.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Override
    List<Customer> findAll();

    @Override
    Optional<Customer> findById(Integer integer);
}
