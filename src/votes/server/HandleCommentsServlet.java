package votes.server;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandleCommentsServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
		String[] commentIds = req.getParameterValues("comment");
		String action = req.getParameter("action");
		if (action.equals("Publish")) {
			publishComments(commentIds);
		} else {
			deleteComments(commentIds);
		}
		resp.sendRedirect(req.getHeader("Referer"));
	}
	
	private void publishComments(String[] commentIds){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			for (int i = 0; i < commentIds.length; i++) {
				Long id = Long.valueOf(commentIds[i]);
				CommentToAnswer comment = pm.getObjectById(CommentToAnswer.class, id);
				comment.setSpam(false);				
				pm.makePersistent(comment);				
			}
		} finally {
			pm.close();
		}
	}
	
	private void deleteComments(String[] commentIds){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			for (int i = 0; i < commentIds.length; i++) {
				Long id = Long.valueOf(commentIds[i]);
				CommentToAnswer comment = pm.getObjectById(CommentToAnswer.class, id);
				pm.deletePersistent(comment);
			}
		} finally {
			pm.close();
		}
	}
}
