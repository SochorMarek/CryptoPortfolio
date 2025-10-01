package com.example;

import com.example.cryptoportfolio.model.Crypto;
import com.example.cryptoportfolio.service.CryptoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cryptos")
public class CryptoController {

    private final CryptoService service;

    public CryptoController(CryptoService service) {
        this.service = service;
    }

    // POST /cryptos
    @PostMapping
    public ResponseEntity<?> addCrypto(@Valid @RequestBody Crypto crypto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Crypto created = service.addCrypto(crypto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET /cryptos?sort=price|name|quantity
    @GetMapping
    public ResponseEntity<List<Crypto>> getAll(@RequestParam(required = false) String sort) {
        List<Crypto> list = service.getAll(sort);
        return ResponseEntity.ok(list);
    }

    // GET /cryptos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return service.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Crypto with id " + id + " not found"));
    }

    // PUT /cryptos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @Valid @RequestBody Crypto updated,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return service.update(id, updated)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Crypto with id " + id + " not found"));
    }

    // GET /cryptos/portfolio-value
    @GetMapping("/portfolio-value")
    public ResponseEntity<BigDecimal> getPortfolioValue() {
        return ResponseEntity.ok(service.portfolioValue());
    }
}