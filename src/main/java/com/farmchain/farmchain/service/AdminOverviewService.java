package com.farmchain.farmchain.service;

import com.farmchain.farmchain.dto.AdminOverview;
import com.farmchain.farmchain.repository.FeedbackRepository;
import com.farmchain.farmchain.repository.ProductRepository;
import com.farmchain.farmchain.repository.SupplyChainLogRepository;
import com.farmchain.farmchain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminOverviewService {

    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final SupplyChainLogRepository supplyChainLogRepo;
    private final FeedbackRepository feedbackRepo;

    public AdminOverviewService(UserRepository userRepo,
                                ProductRepository productRepo,
                                SupplyChainLogRepository supplyChainLogRepo,
                                FeedbackRepository feedbackRepo) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.supplyChainLogRepo = supplyChainLogRepo;
        this.feedbackRepo = feedbackRepo;
    }

    public AdminOverview getOverview() {
        return new AdminOverview(
                userRepo.count(),
                productRepo.count(),
                supplyChainLogRepo.count(),
                feedbackRepo.count());
    }

}
