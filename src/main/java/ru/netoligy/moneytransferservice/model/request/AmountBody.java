package ru.netoligy.moneytransferservice.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class AmountBody {
    @Setter(AccessLevel.NONE)
    @Min(0)
    private int value;
    @NotBlank
    @NotNull
    @Pattern(regexp = "[A-Z]{3}")
    private final String currency;
}
