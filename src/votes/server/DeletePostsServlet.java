package votes.server;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeletePostsServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
		String[] postIds = req.getParameterValues("post");
		String action = req.getParameter("action");
		if (action.equals("Publish")) {
			publishPosts(postIds);
		} else {
			deletePosts(postIds);
		}
		resp.sendRedirect(req.getHeader("Referer"));
	}
	
	private void publishPosts(String[] postIds){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			for (int i = 0; i < postIds.length; i++) {
				Long id = Long.valueOf(postIds[i]);
				Post post = pm.getObjectById(Post.class, id);
				post.setPublished(true);				
				pm.makePersistent(post);				
			}
		} finally {
			pm.close();
		}
	}
	
	private void deletePosts(String[] postIds){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			for (int i = 0; i < postIds.length; i++) {
				Long id = Long.valueOf(postIds[i]);
				Post post = pm.getObjectById(Post.class, id);
				Boolean denial = false;
				for (Long answerId : post.getAnswers()) {
					Answer answer = pm.getObjectById(Answer.class, answerId);
					try {
						pm.deletePersistent(answer);
					} catch (Exception e) {
						denial = true;
					}
				}		
				
				if (!denial) {
					pm.deletePersistent(post);
				}				
			}
		} finally {
			pm.close();
		}
	}
}
