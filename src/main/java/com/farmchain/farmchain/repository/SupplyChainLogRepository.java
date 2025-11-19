package com.farmchain.farmchain.repository;

import com.farmchain.farmchain.model.SupplyChainLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SupplyChainLogRepository extends JpaRepository<SupplyChainLog, Long> {


    List<SupplyChainLog> findByProductIdOrderByTimestampAsc(Long productId);


    Optional<SupplyChainLog> findTopByProductIdOrderByTimestampDesc(Long productId);

    @Query("""
    	    SELECT log 
    	    FROM SupplyChainLog log 
    	    WHERE log.toUserId = :retailerId 
    	      AND log.id = (
    	            SELECT MAX(l2.id) 
    	            FROM SupplyChainLog l2 
    	            WHERE l2.productId = log.productId
    	        )
    	""")
    List<SupplyChainLog> findPendingForRetailer(Long retailerId);

}
