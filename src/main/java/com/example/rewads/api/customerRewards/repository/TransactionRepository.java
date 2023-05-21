package com.example.rewads.api.customerRewards.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<CustomerTransaction, Integer> {

    @Override
    List<CustomerTransaction> findAll();

    @Override
    Optional<CustomerTransaction> findById(Integer id);
}
