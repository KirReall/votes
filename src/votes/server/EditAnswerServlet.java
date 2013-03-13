package votes.server;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditAnswerServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
		String content = req.getParameter("content");
		Integer rate = Integer.valueOf(req.getParameter("rate"));
		Long id = Long.valueOf(req.getParameter("id"));
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Answer answer = pm.getObjectById(Answer.class, id);
		if ((!content.equals(answer.getContent()))||(answer.getRate()!=rate)){
			answer.setContent(content);
			answer.setRate(rate);
			try {
	            pm.makePersistent(answer);
	        } finally {
	            pm.close();
	        }
		}
		resp.sendRedirect(req.getHeader("Referer"));
	}
}
