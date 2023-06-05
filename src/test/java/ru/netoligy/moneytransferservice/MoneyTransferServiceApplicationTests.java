package ru.netoligy.moneytransferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import ru.netoligy.moneytransferservice.model.response.SuccessOperationBody;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;
    private DataTest dataTest;

    public static GenericContainer<?> transferApp = new GenericContainer<>("transfer_app").withExposedPorts(5500);

    @BeforeAll
    public static void setUp() {
        transferApp.start();
    }

    @BeforeEach
    void setUpEach() {
        dataTest = new DataTest();
    }

    @Test
    void transfer() {
        ResponseEntity<SuccessOperationBody> forTransfer = restTemplate
                .postForEntity("http://localhost:" + transferApp.getMappedPort(5500) + "/transfer", dataTest.TRANSFER_1, SuccessOperationBody.class);
        Assertions.assertEquals(Objects.requireNonNull(forTransfer.getBody()).operationId(), dataTest.OPERATION_ID);

        ResponseEntity<SuccessOperationBody> forConfirm = restTemplate
                .postForEntity("http://localhost:" + transferApp.getMappedPort(5500) + "/confirmOperation", dataTest.CONFIRMATION_BODY, SuccessOperationBody.class);
        Assertions.assertEquals(Objects.requireNonNull(forConfirm.getBody()).operationId(), dataTest.OPERATION_ID);
    }
}
