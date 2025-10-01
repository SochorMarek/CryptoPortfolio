package com.example.cryptoportfolio.service;

import com.example.cryptoportfolio.model.Crypto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    // thread-safe-ish list; CopyOnWriteArrayList to vyhnout se ConcurrentModification ve čtení/psaní
    private final List<Crypto> cryptos = new CopyOnWriteArrayList<>();

    public CryptoService() {
        // volitelně přidej nějaké demo hodnoty (lze zakomentovat)
        // cryptos.add(new Crypto("Bitcoin", "BTC", 60000.0, 0.5));
        // cryptos.add(new Crypto("Ethereum", "ETH", 3500.0, 2.0));
    }

    public Crypto addCrypto(Crypto crypto) {
        cryptos.add(crypto);
        return crypto;
    }

    public List<Crypto> getAll(String sortBy) {
        List<Crypto> result = new ArrayList<>(cryptos);

        if (sortBy == null || sortBy.isBlank()) {
            return result;
        }

        switch (sortBy.toLowerCase(Locale.ROOT)) {
            case "price":
                result.sort(Comparator.comparing(Crypto::getPrice, Comparator.nullsLast(Double::compareTo)));
                break;
            case "name":
                result.sort(Comparator.comparing(Crypto::getName, Comparator.nullsLast(String::compareToIgnoreCase)));
                break;
            case "quantity":
                result.sort(Comparator.comparing(Crypto::getQuantity, Comparator.nullsLast(Double::compareTo)));
                break;
            default:
                // neznámé řazení -> vrátíme neseřazeno
                break;
        }
        return result;
    }

    public Optional<Crypto> findById(Integer id) {
        return cryptos.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Optional<Crypto> update(Integer id, Crypto updated) {
        Optional<Crypto> existingOpt = findById(id);
        existingOpt.ifPresent(existing -> {
            if (updated.getName() != null) existing.setName(updated.getName());
            if (updated.getSymbol() != null) existing.setSymbol(updated.getSymbol());
            if (updated.getPrice() != null) existing.setPrice(updated.getPrice());
            if (updated.getQuantity() != null) existing.setQuantity(updated.getQuantity());
        });
        return existingOpt;
    }

    public BigDecimal portfolioValue() {
        return cryptos.stream()
                .map(Crypto::value)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clearAll() {
        cryptos.clear();
    }

    // pro testování: nastav seznam
    public void setCryptos(List<Crypto> list) {
        cryptos.clear();
        cryptos.addAll(list);
    }
}