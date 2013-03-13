package votes.server;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditPostServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
		String content = req.getParameter("content");
		Boolean published = "true".equals(req.getParameter("published"));
		Long id = Long.valueOf(req.getParameter("id"));
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Post post = pm.getObjectById(Post.class, id);
		if ((!content.equals(post.getContent()))||(post.getPublished()!=published)){
			post.setContent(content);
			post.setPublished(published);
			try {
	            pm.makePersistent(post);
	        } finally {
	            pm.close();
	        }
		}
		resp.sendRedirect(req.getHeader("Referer"));
	}
}
