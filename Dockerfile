FROM amazoncorretto:17

EXPOSE 5500

ADD target/moneyTransferService-0.0.1-SNAPSHOT.jar transfer.jar

CMD ["java", "-jar", "transfer.jar"]