package com.example.rewads.api.customerRewards.repository;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "customerTransactions")
public class CustomerTransaction {
    @Id
    @GeneratedValue
    @Column(name = "transactionId")
    private Integer transactionId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transactionDate")
    @OrderBy("name ASC")
    private LocalDateTime transactionDate;

    // Mapping the column of this table
    @ManyToOne
    //Adding the name
    @JoinColumn(name = "customerId")
    Customer customer;
}
