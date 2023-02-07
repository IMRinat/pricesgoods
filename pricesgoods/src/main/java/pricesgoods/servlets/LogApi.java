package pricesgoods.servlets;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogApi
 */
public class LogApi extends BaseDBServlet {
  private static final long serialVersionUID = 1L;

  public LogApi() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    super.doGet(request, response);
    try {
      for (String line :logRequest ) {
        response.getWriter().println(line+"<br>");  
      }
    } catch (IOException e) {
      try {
        e.printStackTrace(response.getWriter());
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
  }

}
