package com.blinkit.controller;

import com.blinkit.entity.Delivery;
import com.blinkit.entity.DeliveryStatus;
import com.blinkit.entity.Role;
import com.blinkit.exception.ApiException;
import com.blinkit.repository.DeliveryRepository;
import com.blinkit.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryRepository deliveryRepository;
    private final CommonService commonService;

    @GetMapping("/orders")
    public ResponseEntity<List<Delivery>> orders() {
        var user = commonService.currentUser();
        if (user.getRole() != Role.DELIVERY) throw new ApiException("Only delivery partners can view assigned orders");
        return ResponseEntity.ok(deliveryRepository.findByDeliveryPartnerId(user.getId()));
    }

    @PutMapping("/status")
    public ResponseEntity<Delivery> status(@RequestParam Long deliveryId, @RequestParam DeliveryStatus status) {
        Delivery d = deliveryRepository.findById(deliveryId).orElseThrow();
        d.setDeliveryStatus(status);
        return ResponseEntity.ok(deliveryRepository.save(d));
    }
}
