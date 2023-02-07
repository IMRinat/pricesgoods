package pricesgoods.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pricesgoods.Config;
import pricesgoods.MyCSVParser;

/**
 * Servlet implementation class initfilecsv
 */
public class Initfilecsv extends BaseDBServlet {
  private static final long serialVersionUID = 1L;
  private MyCSVParser myCSVParser;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public Initfilecsv() {
    super();
    myCSVParser = new MyCSVParser("windows-1251", ';', 1);
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    super.doGet(request, response);
    if (was_cache_response) {return ;}

    try {
      checkDB();
      List<String[]> all = myCSVParser.readAllCSV(Config.FILEPATHCSV);//читаем файл
      myDB.loadPrices("urals", all); // загружаем в базу
      response.getWriter().println("Done " + all.size() + " records");
      response.setStatus(HttpServletResponse.SC_OK);
      save_cache(request,"Done " + all.size() + " records");
    } catch (Exception e) {
      try {
        e.printStackTrace(response.getWriter());
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
  }
}
