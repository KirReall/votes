package votes.server;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RenewAllPostsServlet extends HttpServlet { 
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Calendar cal = Calendar.getInstance();
		Integer days=Integer.valueOf(req.getParameter("days"));
		try {
			Query q = pm.newQuery(Post.class);
			List<Post> posts = (List<Post>) q.execute();
			for (Post post : posts) {
				
				Date date = post.getDate();
				cal.setTime(date);
				cal.add(Calendar.DAY_OF_MONTH, days);
				date = cal.getTime();
				post.setDate(date);
				pm.makePersistent(post);
				
			}
			Query q2 = pm.newQuery(CommentToAnswer.class);
			List<CommentToAnswer> comments = (List<CommentToAnswer>) q2.execute();
			for (CommentToAnswer comment : comments) {
				
				Date date = comment.getDateOfCreate();
				cal.setTime(date);
				cal.add(Calendar.DAY_OF_MONTH, days);
				date = cal.getTime();
				comment.setDateOfCreate(date);
				pm.makePersistent(comment);
				
			}
		} finally {
			pm.close();
		}
		//resp.sendRedirect(req.getHeader("Referer"));
	}
}
