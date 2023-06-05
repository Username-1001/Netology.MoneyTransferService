package ru.netoligy.moneytransferservice.model.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record SuccessOperationBody(@NotNull @NotBlank String operationId) {
}
