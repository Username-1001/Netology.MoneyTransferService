package ru.netoligy.moneytransferservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netoligy.moneytransferservice.DataTest;
import ru.netoligy.moneytransferservice.exception.InputDataException;

import java.math.BigDecimal;

public class CardDebetTest {
    private DataTest dataTest;

    @BeforeEach
    void setUp() {
        dataTest = new DataTest();
    }

    @Test
    void add() {
        Assertions.assertTrue(dataTest.CARD_DEBET_1.add(dataTest.AMOUNT_1));
        Assertions.assertEquals(0, dataTest.CARD_DEBET_1.getBalance().compareTo(new BigDecimal(1100)));
    }
    @Test
    void subtract() {
        Assertions.assertTrue(dataTest.CARD_DEBET_1.subtract(dataTest.AMOUNT_1));
        Assertions.assertEquals(0, dataTest.CARD_DEBET_1.getBalance().compareTo(new BigDecimal(899)));
        Assertions.assertThrows(InputDataException.class, () -> dataTest.CARD_DEBET_1.subtract(dataTest.AMOUNT_2));
    }
}
