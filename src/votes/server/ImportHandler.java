package votes.server;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ImportHandler extends DefaultHandler {
    private static final Logger log = Logger.getLogger(ImportHandler.class.getName());
    private static final SimpleDateFormat hireDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private Stack<Post> postStack;
    private ArrayList<Post> posts;
    private HashMap<Post,ArrayList<Answer>> answersWithPost;
    private Stack<Answer> answerStack;
    private ArrayList<Answer> answers;
    private HashMap<Post,ArrayList<CommentToAnswer>> commentsWithPost;
    private Stack<CommentToAnswer> commentStack;
    private ArrayList<CommentToAnswer> comments;
    private PersistenceManager pm = null;
    private String characters;

	public ImportHandler(byte[] XML) {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			pm = PMF.get().getPersistenceManager();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new ByteArrayInputStream(XML), this);
			
			for (Post post : posts) {
				ArrayList<Answer> answersToSave = answersWithPost.get(post);
				pm.makePersistentAll(answersToSave);
				List<Long> ids = new ArrayList<Long>();
				for (Answer answer : answersToSave) {
					ids.add(answer.getId());
				}
				post.setAnswers(ids);
			}
			
			pm.makePersistentAll(posts);
			
			comments.clear();
			for (Post post : posts) {
				ArrayList<CommentToAnswer> commentsToSave = commentsWithPost.get(post);
				for (CommentToAnswer commentToAnswer : commentsToSave) {
					commentToAnswer.setIdOfPost(post.getId());
					comments.add(commentToAnswer);
				}
				
			}
			pm.makePersistentAll(comments);

		} catch (Throwable t) {

			t.printStackTrace();

		} finally {

			pm.close();
		}

	}

    public void startDocument() throws SAXException {
		
		postStack = new Stack<Post>();
		posts = new ArrayList<Post>();
		answersWithPost = new HashMap<Post, ArrayList<Answer>>();
	    answerStack = new Stack<Answer>();
	    answers = new ArrayList<Answer>();
	    commentsWithPost = new HashMap<Post, ArrayList<CommentToAnswer>>();
	    commentStack = new Stack<CommentToAnswer>();
	    comments = new ArrayList<CommentToAnswer>();

    }

    public void startElement(String namespaceURI, 
                         String localName,
                         String qualifiedName,
                         Attributes attributes) 
            throws SAXException {

    	if (qualifiedName.equals("post")) {

    		Post post = new Post();
    		post.setPublished(false);
    		//post.setId(Long.parseLong(attributes.getValue("id")));
    		postStack.push(post);

    	} else if (qualifiedName.equals("answer")) {
    		Answer answer = new Answer();
    		answerStack.push(answer);
    		
    	} else if (qualifiedName.equals("comment")) {
    		CommentToAnswer comment = new CommentToAnswer();
    		commentStack.push(comment);
    		
    	}

    }

    @SuppressWarnings("unchecked")
	public void endElement(String namespaceURI, 
    		String simpleName,
    		String qualifiedName) 
    throws SAXException {

    	if (!postStack.isEmpty()) {

    		if (qualifiedName.equals("post")) {
    			
    			posts.add(postStack.pop());


    		} 
    		else if (qualifiedName.equals("content")) {

    			Post post = postStack.pop();
    			post.setContent(characters);
    			postStack.push(post);

    		} 
    		else if (qualifiedName.equals("absoluteRate")) {

    			Post post = postStack.pop();
    			post.setAbsoluteRate(Integer.valueOf(characters));
    			postStack.push(post);

    		} 
    		else if (qualifiedName.equals("rate")) {

    			Post post = postStack.pop();
    			post.setRate(Integer.valueOf(characters));
    			postStack.push(post);

    		} 
    		else if (qualifiedName.equals("date")) {

    			Post post = postStack.pop();
    			try {
    				post.setDate(hireDateFormat.parse(characters));
    			} catch (ParseException e) {

    				log.log(Level.FINE, "Could not parse date {0}", characters);
    			}
    			
    			postStack.push(post);

    		} 
    		else if (qualifiedName.equals("numberOfComment")) {

    			Post post = postStack.pop();
    			post.setNumberOfComment(Integer.valueOf(characters));
    			postStack.push(post);

    		}
    		else if (qualifiedName.equals("answer")) {
    			
    			answers.add(answerStack.pop());

    		} 
    		else if (qualifiedName.equals("answers")) {
    			
    			answersWithPost.put(postStack.peek(), (ArrayList<Answer>)answers.clone());
    			answers.clear();
    		} 
    		else if (qualifiedName.equals("answerContent")) {

    			Answer answer = answerStack.pop();
    			answer.setContent(characters);
    			answerStack.push(answer);

    		}
    		else if (qualifiedName.equals("answerRate")) {

    			Answer answer = answerStack.pop();
    			answer.setRate(Integer.valueOf(characters));
    			answerStack.push(answer);

    		} 
    		else if (qualifiedName.equals("comment")) {
    			
    			comments.add(commentStack.pop());

    		} 
    		else if (qualifiedName.equals("comments")) {
    			
    			commentsWithPost.put(postStack.peek(), (ArrayList<CommentToAnswer>)comments.clone());
    			comments.clear();
    		} 
    		else if (qualifiedName.equals("commentContent")) {

    			CommentToAnswer comment = commentStack.pop();
    			comment.setContent(characters);
    			commentStack.push(comment);

    		}
    		else if (qualifiedName.equals("commentRate")) {

    			CommentToAnswer comment = commentStack.pop();
    			comment.setRate(Integer.valueOf(characters));
    			commentStack.push(comment);

    		} 
    		else if (qualifiedName.equals("commentAuthor")) {

    			CommentToAnswer comment = commentStack.pop();
    			comment.setAuthor(characters);
    			commentStack.push(comment);

    		}
    		else if (qualifiedName.equals("commentDate")) {

    			CommentToAnswer comment = commentStack.pop();
    			try {
    				comment.setDateOfCreate(hireDateFormat.parse(characters));
    			} catch (ParseException e) {

    				log.log(Level.FINE, "Could not parse date {0}", characters);
    			}
    			
    			commentStack.push(comment);

    		} 
    		else if (qualifiedName.equals("commentSpam")) {

    			CommentToAnswer comment = commentStack.pop();
    			comment.setSpam(Boolean.valueOf(characters));
    			commentStack.push(comment);

    		}
    	}

    }

    public void characters(char buf[], int offset, int len) 
    throws SAXException {

    	characters = new String(buf, offset, len);

    }

}
