package ru.netoligy.moneytransferservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.netoligy.moneytransferservice.DataTest;
import ru.netoligy.moneytransferservice.exception.InputDataException;
import ru.netoligy.moneytransferservice.exception.TransferException;
import ru.netoligy.moneytransferservice.repository.MoneyTransferRepository;

import java.util.Optional;

import static org.mockito.Mockito.when;
@SpringBootTest
public class MoneyTransferServiceTest {
    private MoneyTransferService service;
    @MockBean
    private MoneyTransferRepository repository;
    private DataTest dataTest;

    @BeforeEach
    void setUp() {
        dataTest = new DataTest();
        service = new MoneyTransferService(repository, dataTest.COMMISSION_1);
    }

    @Test
    void createTransferSuccess() {
        when(repository.getCard(dataTest.TRANSFER_BODY_1.cardFromNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_1));
        when(repository.getCard(dataTest.TRANSFER_BODY_1.cardToNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_2));

        Assertions.assertEquals(0, service.createTransfer(dataTest.TRANSFER_BODY_1));
    }

    @Test
    void createTransferInvalidInputData() {
        when(repository.getCard(dataTest.TRANSFER_BODY_1.cardFromNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_3));
        when(repository.getCard(dataTest.TRANSFER_BODY_1.cardToNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_2));

        Assertions.assertThrows(InputDataException.class, () -> service.createTransfer(dataTest.TRANSFER_BODY_1));
    }

    @Test
    void createTransferCardExpired() {
        when(repository.getCard(dataTest.TRANSFER_BODY_4.cardFromNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_4));
        when(repository.getCard(dataTest.TRANSFER_BODY_4.cardToNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_3));

        Assertions.assertThrows(TransferException.class, () -> service.createTransfer(dataTest.TRANSFER_BODY_4));
    }

    @Test
    void createTransferCardBlocked() {
        when(repository.getCard(dataTest.TRANSFER_BODY_5.cardFromNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_5));
        when(repository.getCard(dataTest.TRANSFER_BODY_5.cardToNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_3));

        Assertions.assertThrows(TransferException.class, () -> service.createTransfer(dataTest.TRANSFER_BODY_5),
                String.format("Card number %s is blocked", dataTest.CARD_NUMBER_5));
    }

    @Test
    void confirmTransferSucceess() {
        when(repository.getTransfer(0)).
                thenReturn(Optional.of(dataTest.TRANSFER_1));
        when(repository.getCode(0)).
                thenReturn(Optional.of("0000"));
        when(repository.getCard(dataTest.TRANSFER_1.cardFromNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_1));
        when(repository.getCard(dataTest.TRANSFER_1.cardToNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_2));

        Assertions.assertEquals(0, service.confirmTransfer(dataTest.CONFIRMATION_BODY));
    }

    @Test
    void confirmTransferWrongCode() {
        when(repository.getTransfer(0)).
                thenReturn(Optional.of(dataTest.TRANSFER_1));
        when(repository.getCode(0)).
                thenReturn(Optional.of("1111"));
        when(repository.getCard(dataTest.TRANSFER_1.cardFromNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_1));
        when(repository.getCard(dataTest.TRANSFER_1.cardToNumber())).
                thenReturn(Optional.of(dataTest.CARD_DEBET_2));

        Assertions.assertThrows(InputDataException.class, () -> service.confirmTransfer(dataTest.CONFIRMATION_BODY));
    }
}
