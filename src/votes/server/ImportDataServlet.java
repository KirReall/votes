package votes.server;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ImportDataServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

    	RequestDispatcher view = request.getRequestDispatcher("/admin/importData.jsp");
    	view.forward(request, response);
	} 
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

	    new ImportHandler(request.getParameter("XML").getBytes("UTF-8"));
	    RequestDispatcher view = request.getRequestDispatcher("/admin/importData.jsp");
	    view.forward(request, response);

    }
	
}

