package pricesgoods;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyDB {

  private Connection connection;
  private Statement statement;
  private PreparedStatement preparedStatement;
  private static MyDB object;

  /**
   * Скрытый конструктор
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  private MyDB() throws ClassNotFoundException, SQLException {
    Class.forName(Config.DRIVER);
    connection = DriverManager.getConnection(Config.URL, Config.LOGIN, Config.PASS);
    statement = connection.createStatement();
  }

  /**
   * Одноразовое подключение
   * @return
   */
  public static MyDB getInstance() {
    if (object == null) {
      try {
        object = new MyDB();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return object;
  }


  /**
   * Загрузка цен в базу данных
   * @param name название товара
   * @param lines список строк с ценами
   * @throws Exception 
   */
  public void loadPrices(String name, List<String[]> lines) throws Exception {
    if (connection==null) {
      throw new Exception("Base disconnected");
    }
    ParseRussianDate parserRussianDate = new ParseRussianDate();
    try {
      statement.executeUpdate("DELETE FROM prices Where name = '"+name+"'");
      preparedStatement = connection
          .prepareStatement("INSERT INTO prices (datestart,dateend,avgprice,name) VALUES (?,?,?,?)");
      for (String[] line : lines) {// 0 datestart, 1-dateend, 2-avgprice
        preparedStatement.setDate(1, parserRussianDate.parseRussianDate(line[0]));
        preparedStatement.setDate(2, parserRussianDate.parseRussianDate(line[1]));
        preparedStatement.setDouble(3, Double.parseDouble(line[2].replace(',', '.')));
        preparedStatement.setString(4, name);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Цена на указанную дату
   * @param name
   * @param date
   * @return
   * @throws Exception
   */
  public double getPrice(String name, Date date) throws Exception {
    if (connection==null) {
      throw new Exception("Base disconnected");
    }
    preparedStatement = connection
        .prepareStatement("SELECT avgprice FROM prices WHERE (? BETWEEN datestart and dateend) AND (name=?)");
    preparedStatement.setDate(1, date);
    preparedStatement.setString(2, name);
    ResultSet rs = preparedStatement.executeQuery();
    if (rs.next()) {
      return rs.getDouble("avgprice");
    }
    throw new Exception("Price not found");
  }

  /**
   * Количество дней между датами
   * @param dateBefore
   * @param dateAfter
   * @return
   */
  private int daysBetweenInc(Date dateBefore, Date dateAfter) {
    long days = ChronoUnit.DAYS.between(dateBefore.toLocalDate(), dateAfter.toLocalDate()) + 1;
    return (int) days;
  }

  /**
   * Средняя цена за период
   * @param name
   * @param date1
   * @param date2
   * @return
   * @throws Exception
   */
  public double getAVGPrice(String name, Date date1, Date date2) throws Exception {
    if (connection==null) {
      throw new Exception("Base disconnected");
    }
    String fields1 = "p1.datestart datestart1, p1.dateend dateend1, p1.avgprice avgprice1";
    String fields2 = "p2.datestart datestart2, p2.dateend dateend2, p2.avgprice avgprice2";
    preparedStatement = connection.prepareStatement("SELECT  * FROM \r\n" + "(SELECT " + fields1
        + " FROM prices p1 WHERE (? BETWEEN p1.datestart and p1.dateend) AND (name=?)) s1,\r\n" + "(SELECT " + fields2
        + " FROM prices p2 WHERE (? BETWEEN p2.datestart and p2.dateend) AND (name=?)) s2;\r\n");
    preparedStatement.setDate(1, date1);
    preparedStatement.setString(2, name);
    preparedStatement.setDate(3, date2);
    preparedStatement.setString(4, name);

    ResultSet rs = preparedStatement.executeQuery();
    if (rs.next()) {
      Date ds1 = rs.getDate("datestart1");
      Date ds2 = rs.getDate("datestart2");
      Date de1 = rs.getDate("dateend1");
      Date de2 = rs.getDate("dateend2");
      Double avg1 = rs.getDouble("avgprice1");
      Double avg2 = rs.getDouble("avgprice2");

      int period = daysBetweenInc(date1, date2);
      int period1 = daysBetweenInc(ds1, de1);
      int period2 = daysBetweenInc(ds2, de2);

      double avgday1 = avg1 / period1;
      double avgday2 = avg2 / period2;

      int days1 = daysBetweenInc(date1, de1);
      int days2 = daysBetweenInc(ds2, date2);
      if (ds1.equals(ds2)) {
        double result = avgday1 * period;
        return result;
      } else {
        double result = (avgday1 * days1) + (avgday2 * days2);
        return result;
      }

    }
    throw new Exception("Price not found");
  }

  /**
   * Минимальная и максимальная цена за период
   * @param name
   * @param date1
   * @param date2
   * @return
   * @throws Exception
   */
  public JSONArray getMinMaxPrice(String name, Date date1, Date date2) throws Exception {
    if (connection==null) {
      throw new Exception("Base disconnected");
    }
    preparedStatement = connection
        .prepareStatement("SELECT  min(avgprice) minprice,max(avgprice) maxprice  FROM prices "
            + " WHERE ( datestart  BETWEEN ? AND ? OR dateend  BETWEEN ? AND ?  "
            + " OR ? BETWEEN datestart AND dateend OR ? BETWEEN datestart AND dateend "
            + ") AND (name=?)"
            );
    
    preparedStatement.setDate(1, date1);
    preparedStatement.setDate(2, date2);
    preparedStatement.setDate(3, date1);
    preparedStatement.setDate(4, date2);
    preparedStatement.setDate(5, date1);
    preparedStatement.setDate(6, date2);
    preparedStatement.setString(7, name);

    ResultSet resultSet = preparedStatement.executeQuery();
    JSONArray resultjson = new JSONArray();
    if (resultSet.next()) {
      JSONObject row = new JSONObject();
      row.put("Min Price", resultSet.getObject("minprice"));
      row.put("Max Price", resultSet.getObject("maxprice"));
      resultjson.put(row);

      return resultjson;
    }
    throw new Exception("Price not found");
  }

  /**
   * Статистика базы данных
   * @return
   * @throws Exception
   */
  public JSONArray getStat() throws Exception {
    if (connection==null) {
      throw new Exception("Base disconnected");
    }
    
    preparedStatement = connection.prepareStatement(
        "select name,count(*) countrec,min(datestart) mindate,max(dateend) maxdate from prices  group by name");
    ResultSet resultSet = preparedStatement.executeQuery();

    JSONArray resultjson = new JSONArray();

    while (resultSet.next()) {
      JSONObject row = new JSONObject();
      row.put("Name", resultSet.getObject("name"));
      row.put("Count records", resultSet.getObject("countrec"));
      row.put("Min date", resultSet.getObject("mindate"));
      row.put("Max date", resultSet.getObject("maxdate"));
      resultjson.put(row);
    }
    
    if (resultjson.isEmpty()) {
      throw new Exception("Base empty");
    }
    return resultjson;
  }

}
