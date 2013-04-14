package votes.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import votes.client.PostService;
import votes.shared.AnswerTransported;
import votes.shared.CommentToAnswerTransported;
import votes.shared.PostTransported;
import votes.shared.UserAccountInfo;
import votes.shared.UserVoteTransported;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PostServiceImpl extends RemoteServiceServlet implements
		PostService {

	@Override
	public void addPost(String content, List<String> answers, String embed, UserAccountInfo user) {

		List<Long> ids = new ArrayList<Long>();
		answers.add(AnswerTransported.OTHER);
		for (String answerContent : answers) {
			Long answerId = addAnswer(answerContent);
			if (answerId == null) {

			} else {
				ids.add(answerId);
			}
		}
		
		// String content = req.getParameter("content");
		Date date = new Date();
		Post post = new Post(content, date);
		post.setAnswers(ids);
		post.setPublished(false);
		if (!(user.getService() == UserAccountInfo.UNAUTHORIZED)) {
			UserAccount author = getUserAccount(user.getUid(), user.getService());
			post.setAuthorId(author.getId());
		}
		post.setEmbed(embed);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(post);
			
		} finally {
			pm.close();
		}
	}

	private Long addAnswer(String answerContent) {
		Long id = null;
		Answer answer = new Answer(answerContent);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(answer);
			id = answer.getId();
		} finally {
			pm.close();
		}
		return id;
	}

	@Override
	public Long removePost(Long Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostTransported> getPosts(Long page, String orderField, Boolean my, UserAccountInfo user) {
		//PersistenceManager pm = PMF.get().getPersistenceManager();
		// DatastoreService datastore =
		// DatastoreServiceFactory.getDatastoreService();
		List<PostTransported> contents = new ArrayList<PostTransported>();
		String seconOrderField = "";
		String filter = "";
		if (orderField.equals("date")) {
			seconOrderField = "absoluteRate";
		} else {
			seconOrderField = "date";
		}
		
		Long authorId = UserAccountInfo.UNAUTHORIZED.longValue();
		
		if ((!(user.getService() == UserAccountInfo.UNAUTHORIZED))&&my) {
			authorId = user.getId();		
		}
		List<Post> posts = getPosts(page, orderField, seconOrderField, authorId);

		for (Post post : posts) {
			List<AnswerTransported> answers = getAnswers((List<Long>) post.getAnswers());
			PostTransported content = new PostTransported(
					post.getContent(), post.getDate(), post.getId(),
					post.getRate(), post.getNumberOfComment(), answers, post.getEmbed());
			content.setAbsoluteRate(post.getAbsoluteRate());
			if (!(user.getService() == UserAccountInfo.UNAUTHORIZED)) {
				content.setUserVote(getCurrentUserVoteAnswerId(post, user));
			}
			
			if (!(post.getAuthorId()==null)){
				UserAccount author = getUserAccount(post.getAuthorId());
				UserAccountInfo authorTransported = author.createUserAccountInfo();
				//authorTransported.setUser(true);
				content.setAuthor(authorTransported);
			}
			
			contents.add(content);
		}
		
		return contents;// (PostTransported[])contents.toArray(new
						// PostTransported[0]);
	}

	private List<Post> getPosts(Long page, String orderField, String seconOrderField, Long authorId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Post> posts = new ArrayList<Post>();
		try {
			Query q = pm.newQuery(Post.class);
			q.setOrdering(orderField + " desc, " + seconOrderField + " desc");
			q.setRange((page - 1) * 10, page * 10);
			//q.setFilter("published == isPublished");
			//q.declareParameters("Boolean isPublished");
			if (!authorId.equals(UserAccountInfo.UNAUTHORIZED.longValue())){
				q.setFilter("authorId == authId");
				q.declareParameters("Long authId");
				posts.addAll((List<Post>) q.execute(authorId));
			} else {
				List<Post> postsGeted = (List<Post>) q.execute();
				posts.addAll(postsGeted);
			}
		} finally {
			pm.close();
		}
		return posts;
	}

	private Long getCurrentUserVoteAnswerId(Post post, UserAccountInfo user) {
		if (!(user.getService() == UserAccountInfo.UNAUTHORIZED)) {
			UserVote userVote = getUserVote(post.getId(), user.getId(), false);
			return userVote==null? null: userVote.getAnswerId();
		}
		
		return null;
	}

	private Long getCurrentUserVoteOtherAnswerId(Long postId, UserAccountInfo user) {
		if (!(user.getService() == UserAccountInfo.UNAUTHORIZED)) {
			UserVote userVote = getUserVote(postId, user.getId(), true);
			return userVote==null? null: userVote.getAnswerId();
		}
		
		return null;
	}
	
	private List<AnswerTransported> getAnswers(List<Long> list) {
		// PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Key> keys = new ArrayList<Key>();
		for (Long id : list) {
			keys.add(KeyFactory.createKey("Answer", id));
		}
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		List<AnswerTransported> answers = new ArrayList<AnswerTransported>();
		try {
			Map<Key, Entity> entities = datastore.get(keys);
			for (Map.Entry<Key, Entity> e : entities.entrySet()) {
				Entity entity = e.getValue();
				Long id = entity.getKey().getId();
				String content = (String) entity.getProperty("content");
				Long rate = (Long) entity.getProperty("rate");
				int index = 0;
				for (AnswerTransported answerTransported : answers) {
					if (answerTransported.getId() > id) {
						index = answers.indexOf(answerTransported);
						break;
					}
				}
				answers.add(index,
						new AnswerTransported(id, content, rate.intValue()));
			}
		
		} finally {
			
			
		}
		return answers;
	}

	@Override
	public void increaseRateOfPost(Long Id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Post post = pm.getObjectById(Post.class, Id);
			post.increaseRate();
			pm.makePersistent(post);
		} finally {
			pm.close();
		}
	}

	@Override
	public void decreaseRateOfPost(Long Id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Post post = pm.getObjectById(Post.class, Id);
			post.decreaseRate();
			pm.makePersistent(post);
		} finally {
			pm.close();
		}

	}

	@Override
	public void increaseRateOfAnswer(Long Id, Long postId, UserAccountInfo user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Answer answer = pm.getObjectById(Answer.class, Id);
			answer.increaseRate();
			if (!(user.getService() == UserAccountInfo.UNAUTHORIZED)) {
				addUserVote(answer, user, postId);
			}
			pm.makePersistent(answer);
		} finally {
			pm.close();
		}

	}

	@Override
	public Long getMaxPost() {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"Post");
		query.setKeysOnly();
		//query.addFilter("published", FilterOperator.EQUAL, Boolean.TRUE);
		FetchOptions fetchOptions = FetchOptions.Builder.withOffset(0);
		PreparedQuery preparedQuery = datastore.prepare(query);
		Integer result = preparedQuery.asList(fetchOptions).size();
		/*
		 * com.google.appengine.api.datastore.Query query = new
		 * com.google.appengine.api.datastore.Query("__Stat_Kind__");
		 * query.addFilter("kind_name", FilterOperator.EQUAL, "Post"); Entity
		 * globalStat = datastore.prepare(query).asSingleEntity(); //Long
		 * totalBytes = (Long) globalStat.getProperty("bytes"); Long
		 * totalEntities = (Long) globalStat.getProperty("count");// TODO
		 * Auto-generated method stub
		 */
		Long totalEntities = result.longValue();
		return totalEntities;
	}

	@Override
	public PostTransported getPost(Long id, UserAccountInfo user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		// DatastoreService datastore =
		// DatastoreServiceFactory.getDatastoreService();
		PostTransported content = new PostTransported();
		try {
			Post post = pm.getObjectById(Post.class, id);

			List<AnswerTransported> answers = getAnswers((List<Long>) post
					.getAnswers());
			List<CommentToAnswerTransported> comments = getComments(post
					.getId());
			content = new PostTransported(post.getContent(), post.getDate(),
					post.getId(), post.getRate(), post.getNumberOfComment(),
					answers, post.getEmbed(), comments);
			content.setAbsoluteRate(post.getAbsoluteRate());
			if (!(user.getService() == UserAccountInfo.UNAUTHORIZED)) {
				content.setUserVote(getCurrentUserVoteAnswerId(post, user));
			}
			
			if (!(post.getAuthorId()==null)){
				UserAccount author = getUserAccount(post.getAuthorId());
				UserAccountInfo authorTransported = author.createUserAccountInfo();
				//authorTransported.setUser(true);
				content.setAuthor(authorTransported);
			}
		} finally {
			pm.close();
		}
		return content;// (PostTransported[])contents.toArray(new
						// PostTransported[0]);
	}

	private List<CommentToAnswerTransported> getComments(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		// DatastoreService datastore =
		// DatastoreServiceFactory.getDatastoreService();
		List<CommentToAnswerTransported> contents = new ArrayList<CommentToAnswerTransported>();
		try {
			Query q = pm.newQuery(CommentToAnswer.class);
			q.setFilter("idOfPost == id");
			q.declareParameters("Long id");
			q.setOrdering("dateOfCreate desc, rate desc");
			List<CommentToAnswer> comments = (List<CommentToAnswer>) q
					.execute(id);
			for (CommentToAnswer comment : comments) {
				CommentToAnswerTransported commentTransported = new CommentToAnswerTransported(
						comment.getContent(), comment.getId(),
						comment.getRate(), comment.getIdOfPost(),
						comment.getAuthor());
				commentTransported.setSpam(comment.isSpam());
				commentTransported.setDate(comment.getDateOfCreate());
				if (comment.getAuthorId()!=null) {
					UserAccount user = getUserAccount(comment.getAuthorId());
					UserAccountInfo authorUser = user.createUserAccountInfo();
					commentTransported.setAuthorUser(authorUser);
				}
				contents.add(commentTransported);
				
			}
		} finally {
			pm.close();
		}
		return contents;
	}

	@Override
	public void addCommentToPost(Long idofPost, String content, String author, UserAccountInfo user) {
		// Date date = new Date();
		CommentToAnswer comment = new CommentToAnswer(content, idofPost, author);
		if (!(user.getService() == UserAccountInfo.UNAUTHORIZED)) {
			UserAccount authorUser = getUserAccount(user.getUid(), user.getService());
			comment.setAuthorId(authorUser.getId());
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Post post = pm.getObjectById(Post.class, idofPost);
			post.increaseNumberOfComment();
			pm.makePersistent(post);
			pm.makePersistent(comment);
		} finally {
			pm.close();
		}
	}

	@Override
	public void decreaseRateOfComment(Long Id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			CommentToAnswer comment = pm.getObjectById(CommentToAnswer.class,
					Id);
			comment.decreaseRate();
			pm.makePersistent(comment);
		} finally {
			pm.close();
		}
	}

	@Override
	public void increaseRateOfComment(Long Id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			CommentToAnswer comment = pm.getObjectById(CommentToAnswer.class,
					Id);
			comment.increaseRate();
			pm.makePersistent(comment);
		} finally {
			pm.close();
		}
	}

	@Override
	public void setSpamComment(Long Id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			CommentToAnswer comment = pm.getObjectById(CommentToAnswer.class,
					Id);
			comment.setSpam(true);
			pm.makePersistent(comment);
		} finally {
			pm.close();
		}

	}

	@Override
	public void addOtherAnswer(Long idofPost, String content, UserAccountInfo user) {
		OtherAnswer answer = new OtherAnswer(idofPost, content);
		answer.setRate(1);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(answer);
			if (!(user.getService() == UserAccountInfo.UNAUTHORIZED)) {
				addUserVote(answer, user, idofPost);
			}
		} finally {
			pm.close();
		}

	}

	@Override
	public void increaseRateOfOtherAnswer(Long Id, Long otherId, Long postId,
			Integer totalRating, UserAccountInfo user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			OtherAnswer answer = pm.getObjectById(OtherAnswer.class, Id);
			answer.increaseRate();
			if (!(user.getService() == UserAccountInfo.UNAUTHORIZED)) {
				addUserVote(answer, user, postId);
			}
			if ((answer.getRate() > 8)
					&& (100 * answer.getRate() / totalRating > 4)) {
				Answer newAnswer = new Answer(answer.getContent());
				newAnswer.setRate(answer.getRate());
				pm.makePersistent(newAnswer);				

				Post post = pm.getObjectById(Post.class, postId);
				post.addAnswer(newAnswer.getId());
				pm.makePersistent(post);

				Answer otherAnswer = pm.getObjectById(Answer.class, otherId);
				
				otherAnswer.setRate(otherAnswer.getRate() - answer.getRate());
				pm.makePersistent(otherAnswer);
				
				moveAnswerVote(answer.getId(), newAnswer.getId(), otherAnswer.getId(), postId);
				
				pm.deletePersistent(answer);

			} else {
				pm.makePersistent(answer);
			}
		} finally {
			pm.close();
		}
	}

	private void moveAnswerVote(Long oldId, Long newId, Long otherId, Long postId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(UserVote.class);
		q.setFilter("postId == postId && answerId == answerId && other==other");
		q.declareParameters("Long postId, Long answerId, Boolean other");
		Boolean other = true;
		List<UserVote> userVotes = (List<UserVote>) q.execute(postId, oldId, other);
		Query qOther = pm.newQuery(UserVote.class);
		qOther.setFilter("postId == postId && answerId == answerId && userId == userId");
		qOther.declareParameters("Long postId, Long answerId, Long userId");
		
		for (UserVote userVote : userVotes) {
			userVote.setAnswerId(newId);
			pm.makePersistent(userVote);
			List<UserVote> userVotesOther = (List<UserVote>) qOther.execute(postId, otherId, userVote.getUserId());
			for (UserVote userVoteOther : userVotesOther) {
				pm.deletePersistent(userVoteOther);
			}
		}

	}

	private void addUserVote(OtherAnswer answer, UserAccountInfo user,
			Long postId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		UserVote userVote = new UserVote(postId, user.getId(), answer.getId());
		userVote.setOther(true);
		pm.makePersistent(userVote);
	}

	private void addUserVote(Answer answer, UserAccountInfo user, Long postId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		UserVote userVote = new UserVote(postId, user.getId(), answer.getId());
		pm.makePersistent(userVote);
	}

	@Override
	public List<AnswerTransported> getOtherAnswers(Long Id, UserAccountInfo user) {
		Long selectedAnswer = getCurrentUserVoteOtherAnswerId(Id, user);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		// DatastoreService datastore =
		// DatastoreServiceFactory.getDatastoreService();
		List<AnswerTransported> contents = new ArrayList<AnswerTransported>();
		try {
			Query q = pm.newQuery(OtherAnswer.class);
			q.setFilter("idOfPost == id");
			q.declareParameters("Long id");
			q.setOrdering("rate desc");
			List<OtherAnswer> answers = (List<OtherAnswer>) q.execute(Id);
			for (OtherAnswer answer : answers) {
				AnswerTransported answerTransported = new AnswerTransported(
						answer.getId(), answer.getContent(), answer.getRate());
				if (answerTransported.getId().equals(selectedAnswer)) {
					answerTransported.setSelected(true);
				}
				contents.add(answerTransported);
			}
		} finally {
			pm.close();
		}
		return contents;
	}

	@Override
	public UserAccountInfo getUserAccount(UserAccountInfo userAccount) {
		UserAccount user = getUserAccount(userAccount.getUid(), userAccount.getService());
		if (user==null){
			user = createUser(userAccount);
		} else if (!(user.correspondsTo(userAccount))) {
			user.correspond(userAccount);
			saveUserAccount(user);
		}
		userAccount.setId(user.getId());
		return userAccount;
	}
	
	private void saveUserAccount(UserAccount user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			user = pm.makePersistent(user);
			
		} finally {
			pm.close();
		}
	}

	private UserAccount getUserAccount(String uid, Integer Service) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		UserAccount user;
		try {
			Query q = pm.newQuery(UserAccount.class);
			q.setFilter("this.uid == uid && this.service == service");
			q.declareParameters("String uid, Integer service");
			List<UserAccount> users = (List<UserAccount>) q.execute(
					uid, Service);

			if (users.isEmpty()) {
				user = null;
			} else {
				user = users.get(0);
			}
		} finally {
			pm.close();
		}
		return user;
	}
	
	private UserAccount getUserAccount(Long id){
		if (id==null){
			return null;
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		UserAccount user;
		try {
			user = pm.getObjectById(UserAccount.class, id);
			
		} finally {
			pm.close();
		}
		return user;
	}

	private UserAccount createUser(UserAccountInfo userAccount) {
		UserAccount user = new UserAccount(userAccount);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {			
			pm.makePersistent(user);
		} finally {
			pm.close();
		}	
		return user;
	}

	@Override
	public List<UserVoteTransported> getUserVotes(Long postId,
			List<String> friendsUids, Integer Service) {
		
		List<UserVoteTransported> usersVotes = getUserVotes(postId, friendsUids, Service, false);
		return usersVotes;
	}
	
	private List<UserVoteTransported> getUserVotes(Long postId,
			List<String> friendsUids, Integer Service, Boolean other) {
		List<UserVoteTransported> usersVotes = new ArrayList<UserVoteTransported>();
		for (String friendUid : friendsUids) {
			UserAccount user = getUserAccount(friendUid, Service);
			if (!(user == null)) {
				UserVote userVote = getUserVote(postId, user.getId(), other);
				if (!(userVote == null)) {
					usersVotes.add(new UserVoteTransported(userVote.getPostId(),
							user.createUserAccountInfo(), userVote.getAnswerId()));
				}
			}
		}			
		return usersVotes;
	}

	private UserVote getUserVote(Long postId, Long userId, Boolean other) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		UserVote userVote;
		try {
			Query q = pm.newQuery(UserVote.class);
			q.setFilter("(this.postId == postId)&&(this.userId == userId)&&(this.other==other)");
			q.declareParameters("Long postId, Long userId, Boolean other");
			List<UserVote> userVotes = (List<UserVote>) q.execute(postId,
					userId, other);
			if (userVotes.isEmpty()) {
				userVote = null;
			} else {
				userVote = userVotes.get(0);
			}
		} finally {
			pm.close();
		}
		return userVote;
	}

	@Override
	public List<UserAccountInfo> getFriendsUserAccounts(
			List<UserAccountInfo> friendsAccounts) {
		for (UserAccountInfo userAccountInfo : friendsAccounts) {
			UserAccount user= getUserAccount(userAccountInfo.getUid(), userAccountInfo.getService());
			if (!(user==null)){
				if (!(user.correspondsTo(userAccountInfo))) {
					user.correspond(userAccountInfo);
					saveUserAccount(user);
				}
				userAccountInfo.setId(user.getId());
			}
			
		} 
		return friendsAccounts;
	}

	@Override
	public List<UserVoteTransported> getUserOtherVotes(Long postId,
			List<String> friendsUids, Integer Service) {
		List<UserVoteTransported> usersVotes = getUserVotes(postId, friendsUids, Service, true);
		return usersVotes;
	}

}
