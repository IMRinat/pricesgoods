package pricesgoods.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class getprice
 */
public class Getprice extends BaseDBServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public Getprice() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    super.doGet(request, response);
    if (was_cache_response) {return ;}
    

    String[] pathParts = request.getPathInfo().split("/");
    if (pathParts.length > 2) {
      String stringdate = pathParts[1];
      String name = pathParts[2];

      try {
        checkDB();
        double avgprice = myDB.getPrice(name, Date.valueOf(stringdate));// получаем цену;
        response.getWriter().println(avgprice);
        response.setStatus(HttpServletResponse.SC_OK);
        save_cache(request,Double.toString(avgprice));
      } catch (Exception e) {
        try {
          e.printStackTrace(response.getWriter());
        } catch (IOException e1) {
          e1.printStackTrace();
        }
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      }
    } else {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

}
