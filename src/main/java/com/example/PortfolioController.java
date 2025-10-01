package com.example.cryptoportfolio.controller;


import com.example.cryptoportfolio.service.CryptoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PortfolioController {

    private final CryptoService service;

    public PortfolioController(CryptoService service) {
        this.service = service;
    }

    @GetMapping("/portfolio-value")
    public ResponseEntity<BigDecimal> getPortfolioValue() {
        BigDecimal value = service.portfolioValue();
        return ResponseEntity.ok(value);
    }
}
