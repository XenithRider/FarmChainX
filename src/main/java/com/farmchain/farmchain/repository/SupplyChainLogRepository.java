package com.farmchain.farmchain.repository;

import com.farmchain.farmchain.model.SupplyChainLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplyChainLogRepository extends JpaRepository<SupplyChainLog , Long> {

    List<SupplyChainLog> findByProductIdOrderByTimestampAsc(Long productId);
    Optional<SupplyChainLog> findTopByProductIdOrderByTimestampDesc(Long productId);

}
