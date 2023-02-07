package pricesgoods;

public class Config {
  public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  public static final String DB = "pricesgood";
  public static final String LOGIN = "root";
  public static final String PASS = "root";
  public static final String HOST = "localhost";
  public static final String PORT = "3306";
  public static final String PROTOCOL = "jdbc:mysql://";
  public static final String URL = PROTOCOL+HOST+":"+PORT+"/" + DB;
  
  public static final String FILEPATHCSV = "E:\\temp\\otkrytye_dannye_-_cena_na_neft_31.csv";

}
