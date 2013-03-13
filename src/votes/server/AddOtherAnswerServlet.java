package votes.server;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votes.shared.AnswerTransported;

public class AddOtherAnswerServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Query q = pm.newQuery(Post.class);
			List<Post> posts = (List<Post>) q.execute();
			Boolean withOther;
			for (Post post : posts) {
				withOther = false;
				List<Long> list = post.getAnswers();
				for (Long id : list) {
					Answer answer = pm.getObjectById(Answer.class, id);
					if (answer.getContent().equals(AnswerTransported.OTHER)){
						withOther = true;
					}
				}
				if (!withOther){
					Answer newAnswer = new Answer(AnswerTransported.OTHER);
					pm.makePersistent(newAnswer);
					post.addAnswer(newAnswer.getId());
					pm.makePersistent(post);
				}
			}
		} finally {
			pm.close();
		}
		//resp.sendRedirect(req.getHeader("Referer"));
	}
}
