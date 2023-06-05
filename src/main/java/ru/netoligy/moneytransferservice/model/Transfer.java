package ru.netoligy.moneytransferservice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record Transfer(@NotNull @Pattern(regexp = "\\d{16}") String cardFromNumber,
                       @NotNull @Pattern(regexp = "[0-1][0-2]/\\d{2}") String cardFromValidTill,
                       @NotNull @Pattern(regexp = "\\d{3}") String cardFromCVV,
                       @NotNull @Pattern(regexp = "\\d{16}") String cardToNumber,
                       int operationId,
                       Amount amount) {
}
