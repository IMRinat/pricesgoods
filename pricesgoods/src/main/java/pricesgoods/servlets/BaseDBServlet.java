package pricesgoods.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pricesgoods.MyDB;

public class BaseDBServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  protected MyDB myDB;
  protected static final ArrayList<String> logRequest = new ArrayList<String>();
  protected HashMap<String, String> cacheRequest;
  protected boolean was_cache_response;

  /**
   * 
   */
  public BaseDBServlet() {
    myDB = MyDB.getInstance();
    //logRequest = new ArrayList<String>();
    cacheRequest = new HashMap<String, String>();
  }

  /**
   * 
   * @param request
   * @param response
   */
  protected void save_cache(HttpServletRequest request, String response) {
    cacheRequest.put(request.getRequestURI(), response);
  }

  /**
   * 
   * @param request
   * @return
   */
  protected boolean get_cache(HttpServletRequest request,HttpServletResponse response) {
    String cacheResult = cacheRequest.get(request.getRequestURI());
    if (cacheResult!=null) {
      try {
        response.getWriter().print(cacheResult);
      } catch (IOException e) {
        e.printStackTrace();
      }
      response.setStatus(HttpServletResponse.SC_OK);
      return true;
    }
    return false;
  }

  /**
   * 
   * @throws Exception
   */
  protected void checkDB() throws Exception {
    if (myDB == null) {
      throw new Exception("Base disconnected");
    }
  }

  /**
   * 
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.setCharacterEncoding("UTF-8");
    
    String currentDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    logRequest.add(currentDateTime + "\t" + request.getRequestURI());
    was_cache_response = get_cache(request,response);
  }

  /**
   * 
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    doGet(request, response);
  }

}
