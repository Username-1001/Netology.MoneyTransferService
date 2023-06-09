# Курсовой проект «Сервис перевода денег»

## Описание проекта

Приложение — REST-сервис. Сервис предоставляет интерфейс для перевода денег с одной карты на другую по заранее описанной спецификации.

Веб-приложение (FRONT) может подключаться к разработанному сервису и использовать его функционал для перевода денег.

## Описание приложения

- Сервис предоставляет REST-интерфейс для интеграции с FRONT.
- Сервис реализует все методы перевода с одной банковской карты на другую, описанные [в протоколе](https://github.com/netology-code/jd-homeworks/blob/master/diploma/MoneyTransferServiceSpecification.yaml).
- Все изменения записываются в файл /log/file.log — лог переводов с указанием:
- даты;
- времени;
- карты, с которой было списание;
- карты зачисления;
- суммы;
- комиссии;
- результата операции, если был.

## Описание реализации

- Приложение разработано с использованием Spring Boot.
- Использован сборщик пакетов maven.
- Для запуска используется Docker, Docker Compose .
- Код покрыт юнит-тестами с использованием mockito.
- Добавлены интеграционные тесты с использованием testcontainers.
- При значении application.mode=test в application.properties, в репозиторий добавляются тестовые данные карт, b всегда генерируется одинаковый код подтверждения операции, в соответсвии с работой демо приложения (FRONT).
- Размер комисси вынесен в application.properties

## Описание интеграции с FRONT

Используется уже развёрнутое демо-приложение [по адресу](https://serp-ya.github.io/card-transfer/).