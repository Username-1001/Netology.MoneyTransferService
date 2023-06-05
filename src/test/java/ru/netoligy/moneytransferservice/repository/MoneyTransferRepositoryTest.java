package ru.netoligy.moneytransferservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netoligy.moneytransferservice.DataTest;

import java.util.List;

public class MoneyTransferRepositoryTest {
    private MoneyTransferRepository repository;
    DataTest dataTest;
    @BeforeEach
    void setUp() {
        dataTest = new DataTest();
        repository = new MoneyTransferRepository(dataTest.APPLICAION_MODE_PROD);
    }
    @Test
    void addAndGetCard() {
        repository.addCard(dataTest.CARD_DEBET_3);
        Assertions.assertFalse(repository.getCard(dataTest.CARD_NUMBER_3).isEmpty());
        Assertions.assertEquals(dataTest.CARD_DEBET_3, repository.getCard(dataTest.CARD_NUMBER_3).get());
    }
    @Test
    void addTransfer() {
        repository.addTransfer(dataTest.TRANSFER_1);
        Assertions.assertFalse(repository.getTransfer(dataTest.TRANSFER_1.operationId()).isEmpty());
        Assertions.assertEquals(dataTest.TRANSFER_1, repository.getTransfer(dataTest.TRANSFER_1.operationId()).get());
        Assertions.assertFalse(repository.getCode(dataTest.TRANSFER_1.operationId()).isEmpty());
        Assertions.assertEquals(4, repository.getCode(dataTest.TRANSFER_1.operationId()).get().length());
    }

    @Test
    void blockAndUpdateCard() {
        repository.addCard(dataTest.CARD_DEBET_3);
        repository.blockCard(dataTest.CARD_NUMBER_3);
        Assertions.assertTrue(repository.getCard(dataTest.CARD_NUMBER_3).get().isBlocked());
    }

    @Test
    void getCardAll() {
        repository.addCard(dataTest.CARD_DEBET_1);
        repository.addCard(dataTest.CARD_DEBET_2);
        Assertions.assertEquals(2, repository.getCardAll().size());
        Assertions.assertTrue(repository.getCardAll().containsAll(List.of(dataTest.CARD_DEBET_1, dataTest.CARD_DEBET_2)));
    }

    @Test
    void getTransferAll() {
        repository.addTransfer(dataTest.TRANSFER_1);
        repository.addTransfer(dataTest.TRANSFER_2);
        Assertions.assertEquals(2, repository.getTransferAll().size());
        Assertions.assertTrue(repository.getTransferAll().containsAll(List.of(dataTest.TRANSFER_1, dataTest.TRANSFER_2)));
    }
}
