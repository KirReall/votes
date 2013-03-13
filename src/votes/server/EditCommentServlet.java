package votes.server;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditCommentServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
		String content = req.getParameter("content");
		Long id = Long.valueOf(req.getParameter("id"));
		PersistenceManager pm = PMF.get().getPersistenceManager();
		CommentToAnswer comment = pm.getObjectById(CommentToAnswer.class, id);
		if (!content.equals(comment.getContent())){
			comment.setContent(content);
			try {
	            pm.makePersistent(comment);
	        } finally {
	            pm.close();
	        }
		}
		resp.sendRedirect(req.getHeader("Referer"));
	}
}
