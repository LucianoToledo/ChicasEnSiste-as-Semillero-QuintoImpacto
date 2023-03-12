package com.chicasensistemas.repository;

import com.chicasensistemas.model.entity.PaymentMpPOJOEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentMpRepository extends JpaRepository<PaymentMpPOJOEntity, Long> {
}
