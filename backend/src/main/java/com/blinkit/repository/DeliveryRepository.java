package com.blinkit.repository;

import com.blinkit.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByDeliveryPartnerId(Long deliveryPartnerId);
}
