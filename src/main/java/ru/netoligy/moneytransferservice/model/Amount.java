package ru.netoligy.moneytransferservice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import ru.netoligy.moneytransferservice.model.request.AmountBody;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class Amount {
    private final String commission;
    @Setter(AccessLevel.NONE)
    private final BigDecimal value;
    @Setter(AccessLevel.NONE)
    private BigDecimal commissionVal;
    @NotBlank
    @NotNull
    @Pattern(regexp = "[A-Z]{3}")
    private final String currency;

    public Amount(BigDecimal value, String commission, String currency) {
        this.value = value.setScale(2);
        this.currency = currency;
        this.commission = commission;

        commissionVal = value.divide(BigDecimal.valueOf(100)).multiply(new BigDecimal(commission)).setScale(2);
    }

    public static Amount getAmountNew(AmountBody amountBody, String commission) {
        return new Amount(BigDecimal.valueOf(amountBody.getValue(), 2), commission, amountBody.getCurrency());
    }
}
