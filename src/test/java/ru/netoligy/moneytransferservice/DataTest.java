package ru.netoligy.moneytransferservice;

import ru.netoligy.moneytransferservice.model.Amount;
import ru.netoligy.moneytransferservice.model.CardDebet;
import ru.netoligy.moneytransferservice.model.Transfer;
import ru.netoligy.moneytransferservice.model.request.AmountBody;
import ru.netoligy.moneytransferservice.model.request.ConfirmationBody;
import ru.netoligy.moneytransferservice.model.request.TransferBody;

import java.math.BigDecimal;

public class DataTest {
    public final String CARD_NUMBER_1 = "1111222233334444";
    public final String CARD_VALID_TILL_1 = "11/23";
    public final String CARD_CVV_1 = "123";
    public final CardDebet CARD_DEBET_1 = new CardDebet(CARD_NUMBER_1, CARD_CVV_1, CARD_VALID_TILL_1, BigDecimal.valueOf(100000, 2), false, "RUR");

    public final String CARD_NUMBER_2 = "4444333322221111";
    public final String CARD_VALID_TILL_2 = "12/24";
    public final String CARD_CVV_2 = "321";
    public final CardDebet CARD_DEBET_2 = new CardDebet(CARD_NUMBER_2, CARD_CVV_2, CARD_VALID_TILL_2, BigDecimal.valueOf(100000, 2), false, "RUR");
    public final String CARD_NUMBER_3 = "3333333333333333";
    public final String CARD_VALID_TILL_3 = "01/25";
    public final String CARD_CVV_3 = "333";
    public final CardDebet CARD_DEBET_3 = new CardDebet(CARD_NUMBER_3, CARD_CVV_3, CARD_VALID_TILL_3, BigDecimal.valueOf(100000, 2), false, "RUR");
    public final String CARD_NUMBER_4 = "4444444444444444";
    public final String CARD_VALID_TILL_4 = "01/22";
    public final String CARD_CVV_4 = "444";
    public final CardDebet CARD_DEBET_4 = new CardDebet(CARD_NUMBER_4, CARD_CVV_4, CARD_VALID_TILL_4, BigDecimal.valueOf(100000, 2), false, "RUR");
    public final String CARD_NUMBER_5 = "5555555555555555";
    public final String CARD_VALID_TILL_5 = "01/25";
    public final String CARD_CVV_5 = "555";
    public final CardDebet CARD_DEBET_5 = new CardDebet(CARD_NUMBER_5, CARD_CVV_5, CARD_VALID_TILL_5, BigDecimal.valueOf(100000, 2), true, "RUR");
    public final String APPLICAION_MODE_PROD = "prod";
    public final String OPERATION_ID = "0";
    public final String CODE = "0000";
    public final AmountBody AMOUNT_BODY = new AmountBody(100, "RUR");
    public final Amount AMOUNT_1 = new Amount(new BigDecimal(100), "1","RUR");
    public final Amount AMOUNT_2 = new Amount(new BigDecimal(1000), "1","RUR");

    public final ConfirmationBody CONFIRMATION_BODY = new ConfirmationBody(OPERATION_ID, CODE);
    public final TransferBody TRANSFER_BODY_1 = new TransferBody(CARD_NUMBER_1,
            CARD_VALID_TILL_1,
            CARD_CVV_1,
            CARD_NUMBER_2,
            AMOUNT_BODY);
    public final TransferBody TRANSFER_BODY_4 = new TransferBody(CARD_NUMBER_4,
            CARD_VALID_TILL_4,
            CARD_CVV_4,
            CARD_NUMBER_3,
            AMOUNT_BODY);
    public final TransferBody TRANSFER_BODY_5 = new TransferBody(CARD_NUMBER_5,
            CARD_VALID_TILL_5,
            CARD_CVV_5,
            CARD_NUMBER_3,
            AMOUNT_BODY);
    public final Transfer TRANSFER_1 = new Transfer(CARD_NUMBER_1,
            CARD_VALID_TILL_1,
            CARD_CVV_1,
            CARD_NUMBER_2,
            0,
            AMOUNT_1);
    public final Transfer TRANSFER_2 = new Transfer(CARD_NUMBER_2,
            CARD_VALID_TILL_2,
            CARD_CVV_2,
            CARD_NUMBER_3,
            1,
            AMOUNT_1);
    public final String COMMISSION_1 = "1";
}
