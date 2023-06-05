package ru.netoligy.moneytransferservice.model.response;

import javax.validation.constraints.NotNull;

public record ErrorBody(@NotNull String message, int id) {
}
