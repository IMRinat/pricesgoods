package pricesgoods;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class ParseRussianDate {
  private DateTimeFormatter formatter;

  public ParseRussianDate() {
    String[] russianShortMonths = { "янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя",
        "дек" };
    Map<Long, String> monthsHashMap = new HashMap<>();
    for (long i = 0; i < russianShortMonths.length; i++) {
      monthsHashMap.put(i + 1, russianShortMonths[(int) i]); // 1 янв, 2 фев и т.д.
    }

    formatter = new DateTimeFormatterBuilder().appendPattern("dd.").appendText(ChronoField.MONTH_OF_YEAR, monthsHashMap)
        .appendPattern(".yy").toFormatter();

  }

  public Date parseRussianDate(String dateInString) {
    LocalDate localDate = LocalDate.parse(dateInString, formatter);
    return  Date.valueOf(localDate);
  }

}
