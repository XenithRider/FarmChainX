package com.farmchain.farmchain.repository;



import com.farmchain.farmchain.model.AdminPromotionRequest;
import com.farmchain.farmchain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdminPromotionRequestRepository extends JpaRepository<AdminPromotionRequest, Long> {

    List<AdminPromotionRequest> findByApprovedFalseAndRejectedFalse();

    boolean existsByUserAndApprovedFalseAndRejectedFalse(User user);

    AdminPromotionRequest findByUserAndApprovedFalseAndRejectedFalse(User user);
}
