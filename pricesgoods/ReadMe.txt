�������� ������� pricesgoods

�������:
���������� ���������� �������� ������ � ���� �� ����� ����� ����� 
������ � ������������ � ��� ������ �� API.

������� �� Java � eclipse 
���������� �� ���-������� tomcat


�������� � ����� attachments:
1. ���� � ������� "otkrytye_dannye_-_cena_na_neft_31.csv"
�� https://data.gov.ru/opendata/7710349494-urals/

2. Sql ���� "pricesgood.sql" ��� ������� ���� ������ pricesgood MySQl 

3. Html ����  "testfront.html" ��� ������������ API

��������� ����������� � ���� � ����� 
"pricesgoods\src\main\java\pricesgoods\Config.java"

�������� ������:
  pricesgoods\src\main\java\pricesgoods\ 
    -Config.java - ����� � ����������� ����������� � �� MySQL � � ����� � ����� csv
    -MyCSVParser.java - ����� ������ csv
    -MyDB.java - ����� ��� ����������� � �� � ���������� ��������
    -ParseRussianDate.java  - ����� ��� �������� ��� �� �������, �������� "15.���.13"
  pricesgoods\src\main\java\pricesgoods\servlets\ 
  ��������:
    -BaseDBServlet.java - ������� ������� �� �������� ����������� ���������
    -Getprice.java    - /getprice       - ���� �� ����
    -Getavgprice.java - /getavgprice    - ������� ���� �� ������
    -Getminmax.java   - /getminmaxprice - ��� � ���� ���� �� ������
    -Stat.java        - /stat           - ���������� ���� ������ (����� �����, ��� � ���� ���� ��� ������� ������)
    -LogApi.java      - /logapi         - ��� ������ api �������
    -Initfilecsv.java - /initfilecsv    - �������� ������ �� ����� � ����

    ������� ������
    http://localhost:8080/pricesgoods/stat
    http://localhost:8080/pricesgoods/logapi
    http://localhost:8080/pricesgoods/initfilecsv
    http://localhost:8080/pricesgoods/getprice/2013-04-10/urals
    http://localhost:8080/pricesgoods/getavgprice/2013-04-10/2013-04-16/urals
    http://localhost:8080/pricesgoods/getminmaxprice/2013-04-10/2013-04-16/urals

    ����:
    - ���������� �������� � ������� � ����� ���� � api;
    - ����������� ��������;
    - �������� ��� �������;

    ���:
    - ���������� �������� � ������� (�������, ����� ��������� � ������);
    - Docker ���� ��� ������������� �������;
    - Unit �����;
    - GraphQL ��� API.
