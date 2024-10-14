package com.ing.market.repository;

import com.ing.market.model.Orders;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findByIdAndStatusAndCustomerId(long id, String status, long customerId);

    List<Orders> findByCustomerIdAndStatusAndCreateDateBetween(long customerId, String status, Instant startDate,
                                                               Instant endDate, Pageable pageable);

}
