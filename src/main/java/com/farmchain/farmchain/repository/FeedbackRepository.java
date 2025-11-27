package com.farmchain.farmchain.repository;

import java.util.List;
import java.util.Optional;

import com.farmchain.farmchain.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;



public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProductId(Long productId);

    Optional<Feedback> findByProductIdAndConsumerId(Long productId, Long consumerId);

    boolean existsByProductIdAndConsumerId(Long productId, Long consumerId);
}

