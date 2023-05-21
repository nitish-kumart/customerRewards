package com.example.rewads.api.customerRewards.repository;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "customerId")
    private Integer customerId;

    @Column(name = "customerName")
    private String customerName;

    @OneToMany(fetch = FetchType.LAZY,mappedBy="customer")
    private List<CustomerTransaction> customerTransactionList;

}
