package votes.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import votes.shared.AnswerTransported;
import votes.shared.CommentToAnswerTransported;
import votes.shared.PostTransported;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class DownloadDataServlet extends HttpServlet {
	private PersistenceManager pm;
	private static final SimpleDateFormat hireDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<PostTransported> posts = getPosts();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document document = documentBuilder.newDocument();
		Element rootElement = document.createElement("posts");
		document.appendChild(rootElement);
		for (PostTransported post : posts) {
			
			Element postElement = document.createElement("post");
			Element contentElement = document.createElement("content");
			contentElement.appendChild(document.createTextNode(post.getContent()));
			postElement.appendChild(contentElement);
			Element absoluteRateElement = document.createElement("absoluteRate");
			absoluteRateElement.appendChild(document.createTextNode(post.getAbsoluteRate().toString()));
			postElement.appendChild(absoluteRateElement);
			Element rateElement = document.createElement("rate");
			rateElement.appendChild(document.createTextNode(post.getRate().toString()));
			postElement.appendChild(rateElement);
			Element dateElement = document.createElement("date");
			dateElement.appendChild(document.createTextNode(hireDateFormat.format(post.getDate())));
			postElement.appendChild(dateElement);
			Element numberOfCommentElement = document.createElement("numberOfComment");
			numberOfCommentElement.appendChild(document.createTextNode(post.getNumberOfComment().toString()));
			postElement.appendChild(numberOfCommentElement);
			
			Element answersElement = document.createElement("answers");
			postElement.appendChild(answersElement);
			
			for (AnswerTransported answer : post.getAnswers()) {
				Element answerElement = document.createElement("answer");
				answersElement.appendChild(answerElement);
				
				Element answerContentElement = document.createElement("answerContent");
				answerContentElement.appendChild(document.createTextNode(answer.getContent()));
				answerElement.appendChild(answerContentElement);
				
				Element answerRateElement = document.createElement("answerRate");
				answerRateElement.appendChild(document.createTextNode(answer.getRate().toString()));
				answerElement.appendChild(answerRateElement);
				
			}
			
			Element commentsElement = document.createElement("comments");
			postElement.appendChild(commentsElement);
			
			for (CommentToAnswerTransported comment : post.getComments()) {
				Element commentElement = document.createElement("comment");
				commentsElement.appendChild(commentElement);
				
				Element commentContentElement = document.createElement("commentContent");
				commentContentElement.appendChild(document.createTextNode(comment.getContent()));
				commentElement.appendChild(commentContentElement);
				
				Element commentRateElement = document.createElement("commentRate");
				commentRateElement.appendChild(document.createTextNode(comment.getRate().toString()));
				commentElement.appendChild(commentRateElement);
				
				Element authorElement = document.createElement("commentAuthor");
				authorElement.appendChild(document.createTextNode(comment.getAuthor()));
				commentElement.appendChild(authorElement);
				
				Element commentDateElement = document.createElement("commentDate");
				commentDateElement.appendChild(document.createTextNode(hireDateFormat.format(comment.getDate())));
				commentElement.appendChild(commentDateElement);
				
				Element spamElement = document.createElement("commentSpam");
				spamElement.appendChild(document.createTextNode(comment.isSpam().toString()));
				commentElement.appendChild(spamElement);
				
			}
			
			rootElement.appendChild(postElement);
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DOMSource source = new DOMSource(document);
		resp.setContentType("application/xml");
		StreamResult result =  new StreamResult(resp.getOutputStream());
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	private List<PostTransported> getPosts() {
		pm = PMF.get().getPersistenceManager();
		//DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<PostTransported> contents = new ArrayList<PostTransported>();
		try {
			Query q = pm.newQuery(Post.class);
			//q.setOrdering("rate desc, date desc");
			//q.setFilter("published == isPublished");
			//q.declareParameters("Boolean isPublished");
			List<Post> posts = (List<Post>) q.execute();
			for (Post post : posts) {
				List<AnswerTransported> answers = getAnswers((List<Long>)post.getAnswers());
				List<CommentToAnswerTransported> comments = getComments(post.getId());
				PostTransported content = new PostTransported(post.getContent(), post.getDate(), post.getId(), post.getRate(), post.getNumberOfComment(), answers, post.getEmbed(), comments);
				content.setAbsoluteRate(post.getAbsoluteRate());
				contents.add(content);
			}
		} finally {
			pm.close();
		}
		return contents;//(PostTransported[])contents.toArray(new PostTransported[0]);
	}
	
	private List<AnswerTransported> getAnswers(List<Long> list) {
		//PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Key> keys = new ArrayList<Key>();
		for (Long id : list) {
			keys.add(KeyFactory.createKey("Answer", id));
		}
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<AnswerTransported> answers = new ArrayList<AnswerTransported>();
		Map<Key,Entity> entities = datastore.get(keys);
		for (Map.Entry<Key,Entity> e : entities.entrySet()) {
			Entity entity = e.getValue();
			Long id = entity.getKey().getId();
			String content = (String) entity.getProperty("content");
			Long rate = (Long) entity.getProperty("rate");
			answers.add(new AnswerTransported(id, content, rate.intValue()));
		}
		return answers;//(Answer[])answers.toArray(new Answer[10])
	}
	
	private List<CommentToAnswerTransported> getComments(Long id) {
		//DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<CommentToAnswerTransported> contents = new ArrayList<CommentToAnswerTransported>();
		
		Query q = pm.newQuery(CommentToAnswer.class);
		q.setFilter("idOfPost == id");
		q.declareParameters("Long id");
		q.setOrdering("rate desc");
		List<CommentToAnswer> comments = (List<CommentToAnswer>) q.execute(id);
		for (CommentToAnswer comment : comments) {
			CommentToAnswerTransported commentTransported = new CommentToAnswerTransported(comment.getContent(), comment.getId(), comment.getRate(), comment.getIdOfPost(), comment.getAuthor());
			commentTransported.setSpam(comment.isSpam());
			commentTransported.setDate(comment.getDateOfCreate());
			contents.add(commentTransported);
		}
		
		return contents;
	}
	
}
