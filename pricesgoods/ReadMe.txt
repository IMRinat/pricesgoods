Название проекта pricesgoods

Задание:
Необходимо распарсить открытые данные о цене на сырую нефть марки 
“Юралс” и предоставить к ним доступ по API.

Написан на Java в eclipse 
Запускался на веб-сервере tomcat


Вложения в папке attachments:
1. Файл с данными "otkrytye_dannye_-_cena_na_neft_31.csv"
из https://data.gov.ru/opendata/7710349494-urals/

2. Sql файл "pricesgood.sql" для импорта базы данных pricesgood MySQl 

3. Html файл  "testfront.html" для тестирования API

Настройки подключения к базе в файле 
"pricesgoods\src\main\java\pricesgoods\Config.java"

Описание файлов:
  pricesgoods\src\main\java\pricesgoods\ 
    -Config.java - класс с настройками подключения к БД MySQL и с путем к файлу csv
    -MyCSVParser.java - класс парсер csv
    -MyDB.java - класс для подключения к БД и выполнения запросов
    -ParseRussianDate.java  - класс для парсинга дат на русском, например "15.мар.13"
  pricesgoods\src\main\java\pricesgoods\servlets\ 
  Сервлеты:
    -BaseDBServlet.java - базовый сервлет от которого наследуются остальные
    -Getprice.java    - /getprice       - цена на дату
    -Getavgprice.java - /getavgprice    - средняя цена за период
    -Getminmax.java   - /getminmaxprice - мин и макс цена за период
    -Stat.java        - /stat           - статистика базы данных (колво строк, мин и макс дата для каждого товара)
    -LogApi.java      - /logapi         - лог вызова api сервиса
    -Initfilecsv.java - /initfilecsv    - загрузка данных из файла в базу

    примеры вызова
    http://localhost:8080/pricesgoods/stat
    http://localhost:8080/pricesgoods/logapi
    http://localhost:8080/pricesgoods/initfilecsv
    http://localhost:8080/pricesgoods/getprice/2013-04-10/urals
    http://localhost:8080/pricesgoods/getavgprice/2013-04-10/2013-04-16/urals
    http://localhost:8080/pricesgoods/getminmaxprice/2013-04-10/2013-04-16/urals

    Есть:
    - Сохранение запросов к сервису и вывод лога в api;
    - Кэширование запросов;
    - Фронтенд для сервиса;

    Нет:
    - Статистика запросов к сервису (сколько, время обработки и прочее);
    - Docker файл для развертывания сервиса;
    - Unit тесты;
    - GraphQL для API.
