package ru.netoligy.moneytransferservice.model;

import lombok.extern.slf4j.Slf4j;
import ru.netoligy.moneytransferservice.exception.InputDataException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;


@Slf4j
public class CardDebet extends Card{
    public CardDebet(@NotNull @Pattern(regexp = "\\d{16}") String number, @NotNull @Pattern(regexp = "\\d{3}") String cvv, @NotNull @Pattern(regexp = "[0-1][0-2]/\\d{2}") String validTill, @Min(0) BigDecimal balance, boolean blocked, @NotNull @Pattern(regexp = "[A-Z]{3}") String currency) {
        super(number, cvv, validTill, balance, blocked, currency);
    }

    @Override
    public boolean add(Amount amount) {
        balance = balance.add(amount.getValue().setScale(2));
        return true;
    }

    @Override
    public boolean subtract(Amount amount) {
        BigDecimal subtractTotalVal = checkBalanceAndGetTotalVal(amount);
        balance = balance.subtract(subtractTotalVal).setScale(2);
        return true;
    }
    @Override
    public BigDecimal checkBalanceAndGetTotalVal(Amount amount) {
        BigDecimal subtractTotalVal = amount.getValue()
                .add(amount.getCommissionVal());

        if (subtractTotalVal.compareTo(balance) > 0) {
            String message = String.format("Not enough money. Card: %s. Balance: %s. Value: %s. Commission: %s.",
                    getNumber(), balance, amount.getValue(), amount.getCommissionVal());
            log.error(message);
            throw new InputDataException(message);
        }

        return subtractTotalVal;
    }
}
