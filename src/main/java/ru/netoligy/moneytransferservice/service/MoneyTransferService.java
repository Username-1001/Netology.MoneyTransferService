package ru.netoligy.moneytransferservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.netoligy.moneytransferservice.exception.InputDataException;
import ru.netoligy.moneytransferservice.exception.TransferException;
import ru.netoligy.moneytransferservice.model.Amount;
import ru.netoligy.moneytransferservice.model.Card;
import ru.netoligy.moneytransferservice.model.Transfer;
import ru.netoligy.moneytransferservice.model.request.ConfirmationBody;
import ru.netoligy.moneytransferservice.model.request.TransferBody;
import ru.netoligy.moneytransferservice.repository.MoneyTransferRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class MoneyTransferService {
    private final MoneyTransferRepository repository;
    private final AtomicInteger operationId = new AtomicInteger(0);
    private final String commission;

    public MoneyTransferService(MoneyTransferRepository repository, @Value("${commission}") String commission) {
        this.repository = repository;
        this.commission = commission;
    }

    public int createTransfer(TransferBody transferBody) {
        Transfer transfer;
        if (checkTransferRequest(transferBody)) {

            transfer = new Transfer(transferBody.cardFromNumber(),
                    transferBody.cardFromValidTill(),
                    transferBody.cardFromCVV(),
                    transferBody.cardToNumber(),
                    operationId.getAndIncrement(),
                    Amount.getAmountNew(transferBody.amount(), commission));

            repository.addTransfer(transfer);
            log.info(String.format("Transfer created: CardFrom: %s. CardTo: %s Value: %s. Commission: %s.",
                    transfer.cardFromNumber(), transfer.cardToNumber(), transfer.amount().getValue(), transfer.amount().getCommissionVal()));
            return transfer.operationId();
        }
        return -1;
    }

    public int confirmTransfer(ConfirmationBody confirmation) {
        Transfer transfer = checkTransferExistsAndGet(confirmation.operationId());
        String code = checkCodeExistsAndGet(confirmation.operationId());
        if (!String.valueOf(code).equals(confirmation.code())) {
            String message = "Wrong confirmation code";
            log.error(message);
            throw new InputDataException(message);
        }
        Card cardFrom = checkCardExistsAndGet(transfer.cardFromNumber());
        Card cardTo = checkCardExistsAndGet(transfer.cardToNumber());

        if (cardFrom.subtract(transfer.amount())) {
            if (cardTo.add(transfer.amount())) {
                repository.updateCard(cardFrom);
                repository.updateCard(cardTo);
                return Integer.parseInt(confirmation.operationId());
            }
        }
        String message = String.format("Error during money transfer. CardFrom: %s. CardTo: %s Value: %s. Commission: %s.",
                cardFrom.getNumber(), cardTo.getNumber(), transfer.amount().getValue(), transfer.amount().getCommissionVal());
        log.error(message);
        throw new TransferException(message);
    }

    private boolean checkTransferRequest(TransferBody transferBody) {
        Card cardFrom = checkCardExistsAndGet(transferBody.cardFromNumber());
        Card cardTo = checkCardExistsAndGet(transferBody.cardToNumber());

        checkInputData(cardFrom, transferBody);
        checkBlock(cardFrom);
        checkBlock(cardTo);
        checkCurrency(cardFrom, cardTo, transferBody.amount().getCurrency());
        checkExpiration(cardFrom);
        checkExpiration(cardTo);
        cardFrom.checkBalanceAndGetTotalVal(Amount.getAmountNew(transferBody.amount(), commission));

        return true;
    }

    private void checkInputData(Card card, TransferBody transferBody) {
        if (!card.getValidTill().equals(transferBody.cardFromValidTill())) {
            String message = "Wrong input: cardFrom valid till";
            log.error(message);
            throw new InputDataException(message);
        }

        if (!card.getCvv().equals(transferBody.cardFromCVV())) {
            String message = "Wrong input: cardFrom CVV";
            log.error(message);
            throw new InputDataException(message);
        }
    }

    private void checkExpiration(Card card) {
        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = LocalDate.parse("01/" + card.getValidTill(), DateTimeFormatter.ofPattern("dd/MM/yy"));
        expirationDate = expirationDate.withDayOfMonth(expirationDate.getMonth().length(currentDate.isLeapYear()));
        if (expirationDate.isBefore(currentDate)) {
            String message = String.format("Card number %s is expired", card.getNumber());
            log.error(message);
            throw new TransferException(message);
        }
    }

    private void checkBlock(Card card) {
        if (card.isBlocked()) {
            String message = String.format("Card number %s is blocked", card.getNumber());
            log.error(message);
            throw new TransferException(message);
        }
    }

    private void checkCurrency(Card cardFrom, Card cardTo, String currency) {
        if (!cardFrom.getCurrency().equals(currency) || !cardTo.getCurrency().equals(currency) || !cardFrom.getCurrency().equals(cardTo.getCurrency())) {
            String message = String.format("Wrong currency. CardFrom: %s. CardTo: %s. TransferRequest: %s",
                    cardFrom.getCurrency(), cardTo.getCurrency(), currency);
            log.error(message);
            throw new InputDataException(message);
        }
    }

    private Card checkCardExistsAndGet(String cardNumber) {
        return repository.getCard(cardNumber)
                .orElseThrow(() -> {
                    String message = "Card number " + cardNumber + " not found";
                    log.error(message);
                    return new InputDataException(message);
                });
    }

    private String checkCodeExistsAndGet(String operationId) {
        return repository.getCode(Integer.parseInt(operationId))
                .orElseThrow(() -> {
                    String message = "Confirmation code for operation " + operationId + " has expired.";
                    log.error(message);
                    return new TransferException(message);
                });
    }

    private Transfer checkTransferExistsAndGet(String operationId) {
        return repository.getTransfer(Integer.parseInt(operationId))
                .orElseThrow(() -> {
                    String message = "Operation " + operationId + " not found.";
                    log.error(message);
                    return new InputDataException(message);
                });
    }
}
