package ru.netoligy.moneytransferservice.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ConfirmationBody(@NotNull @NotBlank String operationId,
                               @NotNull @NotBlank String code) {
}
