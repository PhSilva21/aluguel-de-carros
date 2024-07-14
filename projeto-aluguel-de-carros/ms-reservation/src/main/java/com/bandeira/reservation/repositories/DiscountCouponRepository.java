package com.bandeira.reservation.repositories;

import com.bandeira.reservation.model.DiscountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, Long> {

    DiscountCoupon findByCouponName(String name);
}
