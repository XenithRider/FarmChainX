package com.farmchain.farmchain.controller;

import com.farmchain.farmchain.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/api/verify")
public class VerificationController {

    private final ProductService productService;

    public VerificationController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> verifyProduct(@PathVariable Long productId, Principal principal) {

        System.out.println("🟢 [VERIFY] API called for Product ID: " + productId);

        // 🧩 Case 1: Not logged in → Public View
        if (principal == null) {
            System.out.println("🔵 [VERIFY] User not logged in → Returning Public View");
            return ResponseEntity.ok(productService.getPublicView(productId));
        }

        System.out.println("🟡 [VERIFY] Logged in as: " + principal.getName());

        var authentication = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();

        System.out.println("🟣 [VERIFY] Roles: " + authentication.getAuthorities());

        boolean isAuthorized = authentication.getAuthorities().stream()
                .anyMatch(auth -> {
                    String role = auth.getAuthority();
                    System.out.println("   🔍 Checking role: " + role);
                    return role.equalsIgnoreCase("ROLE_DISTRIBUTER")
                            || role.equalsIgnoreCase("ROLE_RETAILER")
                            || role.equalsIgnoreCase("ROLE_ADMIN");
                });

        // 🧩 Case 2: Authorized Roles → Authorized View
        if (isAuthorized) {
            System.out.println("✅ [VERIFY] Authorized role detected → Returning Authorized View");
            return ResponseEntity.ok(productService.getAuthorizedView(productId, principal.getName()));
        }

        // 🧩 Case 3: Logged in but not authorized (Farmer / Consumer) → Public View
        System.out.println("⚪ [VERIFY] Logged in but not authorized → Returning Public View");
        return ResponseEntity.ok(productService.getPublicView(productId));
    }
}