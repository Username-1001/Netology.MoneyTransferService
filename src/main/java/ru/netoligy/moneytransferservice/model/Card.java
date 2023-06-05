package ru.netoligy.moneytransferservice.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class Card {
    @NotNull
    @Pattern(regexp = "\\d{16}")
    private final String number;
    @NotNull
    @Pattern(regexp = "\\d{3}")
    private final String cvv;
    @NotNull
    @Pattern(regexp = "[0-1][0-2]/\\d{2}")
    private final String validTill;
    @Setter(AccessLevel.PROTECTED)
    @Min(0)
    protected BigDecimal balance = BigDecimal.valueOf(0, 2);
    private boolean blocked = false;
    @NotNull
    @Pattern(regexp = "[A-Z]{3}")
    private final String currency;
    public abstract boolean add(Amount amount);
    public abstract boolean subtract(Amount amount);
    public abstract BigDecimal checkBalanceAndGetTotalVal(Amount amount);
}
