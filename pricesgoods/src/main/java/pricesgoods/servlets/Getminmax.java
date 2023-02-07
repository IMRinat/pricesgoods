package pricesgoods.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

/**
 * Servlet implementation class Getminmax
 */
public class Getminmax extends BaseDBServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public Getminmax() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
    response.setContentType("application/json; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    super.doGet(request, response);
    if (was_cache_response) {return ;}

    String[] pathParts = request.getPathInfo().split("/");
    if (pathParts.length > 3) {
      String stringdate1 = pathParts[1];
      String stringdate2 = pathParts[2];
      String name = pathParts[3];

      JSONArray jsonArray;
      try {
        checkDB();
        jsonArray = myDB.getMinMaxPrice(name, Date.valueOf(stringdate1), Date.valueOf(stringdate2));
        response.getWriter().print(jsonArray.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        save_cache(request,jsonArray.toString());
      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        try {
          e.printStackTrace(response.getWriter());
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    } else {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }


}
