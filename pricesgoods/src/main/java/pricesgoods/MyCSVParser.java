package pricesgoods;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class MyCSVParser {

  private String charset;
  private char delimiter;
  private int skiplines;

  /**
   * 
   * @param charset
   * @param delimiter
   * @param skiplines
   */
  public MyCSVParser(String charset, char delimiter, int skiplines) {
    this.charset = charset;
    this.delimiter = delimiter;
    this.skiplines = skiplines;
  }

  /**
   * 
   * @param filepath
   * @return
   * @throws IOException
   */
  public List<String[]> readAllCSV(String filepath) throws IOException {
    FileReader fileReader = new FileReader(filepath, Charset.forName(charset));
    CSVParser parser = new CSVParserBuilder().withSeparator(delimiter).withIgnoreQuotations(true).build();
    CSVReader readerCsv = new CSVReaderBuilder(fileReader).withSkipLines(skiplines).withCSVParser(parser).build();
    return readerCsv.readAll();
  }
}
