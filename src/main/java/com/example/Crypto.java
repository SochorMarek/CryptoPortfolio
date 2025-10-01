package com.example.cryptoportfolio.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Crypto {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private Integer id;

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "symbol must not be blank")
    private String symbol;

    @NotNull(message = "price must not be null")
    @Min(value = 0, message = "price must be >= 0")
    private Double price;

    @NotNull(message = "quantity must not be null")
    @Min(value = 0, message = "quantity must be >= 0")
    private Double quantity;

    public Crypto() {
        this.id = ID_GENERATOR.getAndIncrement();
    }

    public Crypto(String name, String symbol, Double price, Double quantity) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }

    // getters & setters

    public Integer getId() {
        return id;
    }

    // id setter omitted to prevent manual id manipulation

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal value() {
        BigDecimal p = BigDecimal.valueOf(price != null ? price : 0.0);
        BigDecimal q = BigDecimal.valueOf(quantity != null ? quantity : 0.0);
        return p.multiply(q);
    }
}