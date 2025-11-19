package com.farmchain.farmchain.controller;

import com.farmchain.farmchain.dto.FeedbackRequest;
import com.farmchain.farmchain.model.Feedback;
import com.farmchain.farmchain.repository.UserRepository;
import com.farmchain.farmchain.service.FeedbackService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserRepository userRepository;

    public FeedbackController(FeedbackService feedbackService, UserRepository userRepository) {
        this.feedbackService = feedbackService;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/{id}/feedback")
    public Feedback addFeedback(@PathVariable Long id, @RequestBody FeedbackRequest feedback) {

        // ✅ logged-in user from JWT
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();  // this is always safe

        // ✅ fetch consumerId from DB
        Long consumerId = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();

        return feedbackService.addFeedback(id, consumerId, feedback);
    }

    @GetMapping("/{id}/feedback")
    public List<Feedback> getFeedback(@PathVariable Long id) {
        return feedbackService.getFeedbackForProduct(id);
    }
}
