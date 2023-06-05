package ru.netoligy.moneytransferservice.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.netoligy.moneytransferservice.exception.InputDataException;
import ru.netoligy.moneytransferservice.model.Card;
import ru.netoligy.moneytransferservice.model.CardDebet;
import ru.netoligy.moneytransferservice.model.Transfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Repository
public class MoneyTransferRepository {
    private final ConcurrentHashMap<String, Card> cards = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Transfer> transfers = new ConcurrentHashMap<>();
    private final Cache<Integer, String> codes = CacheBuilder.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build();
    private final String applicationMode;

    public MoneyTransferRepository(@Value("${application.mode}") String applicationMode) {
        this.applicationMode = applicationMode;
        if (applicationMode.equals("test")) {
            addCard(new CardDebet("1111222233334444", "123", "11/23", BigDecimal.valueOf(100000, 2), false, "RUR"));
            addCard(new CardDebet("4444333322221111", "321", "12/24", BigDecimal.valueOf(100000, 2), false, "RUR"));
        }
    }

    public void addCard(Card card) {
        cards.put(card.getNumber(), card);
    }

    public Optional<Card> getCard(String cardNumber) {
        return Optional.ofNullable(cards.get(cardNumber));
    }
    public List<Card> getCardAll() {
        return cards.values().stream().toList();
    }

    public void blockCard(String cardNumber) {
        Card card = getCard(cardNumber).orElseThrow(() -> new InputDataException("Card not found"));
        card.setBlocked(true);
        updateCard(card);
    }

    public void removeCard(String cardNumber) {
        cards.remove(cardNumber);
    }
    public void removeCardAll(String cardNumber) {
        cards.clear();
    }

    public void addTransfer(Transfer transfer) {
        transfers.put(transfer.operationId(), transfer);
        if (applicationMode.equals("test")) {
            codes.put(transfer.operationId(), "0000");
        } else {
            codes.put(transfer.operationId(),
                    String.format("%04d", new Random().ints(0, 9999).findFirst().getAsInt()));
        }
    }

    public Optional<Transfer> getTransfer(int operationId) {
        return Optional.ofNullable(transfers.get(operationId));
    }

    public void removeTransfer(int operationId) {
        transfers.remove(operationId);
        codes.invalidate(operationId);
    }

    public List<Transfer> getTransferAll() {
        return transfers.values().stream().toList();
    }

    public Optional<String> getCode(int operationId) {
        return Optional.ofNullable(codes.getIfPresent(operationId));
    }

    public void updateCard(Card card) {
        cards.replace(card.getNumber(), card);
    }
}
