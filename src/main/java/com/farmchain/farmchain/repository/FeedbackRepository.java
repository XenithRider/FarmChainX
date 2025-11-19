package com.farmchain.farmchain.repository;

import com.farmchain.farmchain.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProductId(Long productId);

    Optional<Feedback> findByProductIdAndConsumerId(Long productId, Long consumerId);

}
