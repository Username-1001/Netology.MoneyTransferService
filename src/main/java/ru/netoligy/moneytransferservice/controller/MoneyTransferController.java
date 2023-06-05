package ru.netoligy.moneytransferservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netoligy.moneytransferservice.model.request.ConfirmationBody;
import ru.netoligy.moneytransferservice.model.request.TransferBody;
import ru.netoligy.moneytransferservice.model.response.SuccessOperationBody;
import ru.netoligy.moneytransferservice.service.MoneyTransferService;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "${cross.origin.host.name}", maxAge = 3600)
public class MoneyTransferController {
    private MoneyTransferService service;

    @PostMapping("/transfer")
    public SuccessOperationBody postTransfer(@RequestBody TransferBody transferBody) {
        return new SuccessOperationBody(String.valueOf(service.createTransfer(transferBody)));
    }

    @PostMapping("/confirmOperation")
    public SuccessOperationBody postConfirmOperation(@RequestBody ConfirmationBody confirmationBody) {
        return new SuccessOperationBody(String.valueOf(service.confirmTransfer(confirmationBody)));
    }
}
