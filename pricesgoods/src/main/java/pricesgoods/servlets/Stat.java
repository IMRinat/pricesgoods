package pricesgoods.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

/**
 * Servlet implementation class Stat
 */
public class Stat extends BaseDBServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public Stat() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    response.setContentType("application/json; charset=UTF-8");
    super.doGet(request, response);
    if (was_cache_response) {return ;}

    try {
      checkDB();
      JSONArray jsonArray = myDB.getStat();//получаем статистику по базе
      response.getWriter().print(jsonArray.toString());
      response.setStatus(HttpServletResponse.SC_OK);
      save_cache(request,jsonArray.toString());
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
