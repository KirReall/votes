<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.jdo.Query" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="votes.server.PMF" %>
<%@ page import="votes.server.Post" %>
<%@ page import="votes.server.Answer" %>
<%@ page import="votes.server.CommentToAnswer" %>
<%@ page import="javax.jdo.JDOHelper" %>

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/votes.css" />
  </head>
  <body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user == null) {
    	response.sendRedirect("/admin.jsp");
%>
<!--   	<p>Hello! <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>, please.</p>-->
<%	
    } else {
%>
    	<p>Hello, <%= user.getNickname() %>! (You can
    	<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
		String pageParameter = request.getParameter("list");
		String spamParameter = request.getParameter("spam");
		if ((pageParameter=="")||(pageParameter==null)||(spamParameter=="")||(spamParameter==null)){
			response.sendRedirect("/admin/comments.jsp?spam=true&list=1");
		} else {
			Integer numberOfPage = Integer.valueOf(pageParameter);
			Boolean spam = Boolean.valueOf(spamParameter);
			PersistenceManager pm = PMF.get().getPersistenceManager(); //JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
			Query q = pm.newQuery(CommentToAnswer.class);
			q.setFilter("spam == isSpam");
			q.declareParameters("Boolean isSpam");
			//q.setOrdering(orderField+" desc, "+seconOrderField+" desc");
			q.setRange((numberOfPage-1)*20, numberOfPage*20+1);
			List<CommentToAnswer> comments = (List<CommentToAnswer>) q.execute(spam);
			//List<Post> posts = (List<Post>) pm.newQuery(query).execute();
			if (comments.isEmpty()) {
%>
			 	<p>Новых постов нет</p>
<%
			} else {
				if (numberOfPage>1){
					%><a href="comments.jsp?spam=<%=spam.toString()%>&list=<%=String.valueOf(numberOfPage -1)%>">prev</a><%
				}
				if (comments.size() == 21){
					%><a href="comments.jsp?spam=<%=spam.toString()%>&list=<%=String.valueOf(numberOfPage +1)%>">next</a><%
					//posts.remove(21);
				}
				%><form action="/votes/handlecomments" method="post"><%
				for (int i = 0; i < Math.min(comments.size(),20); i++){	
					CommentToAnswer comment = comments.get(i);
					%><div><input type="checkbox" name="comment" value="<%= comment.getId() %>">
						<div><textarea name="content" rows="3" cols="40"><%= comment.getContent() %></textarea></div>
						<!--<div><input type="submit" value="Edit Post" /></div> -->
					<a href="editComment.jsp?comment=<%=comment.getId()%>">edit</a></div><%
				}
				%><div><input type="submit" name="action" value="Del" onclick="return(confirm('Вы уверены?'))"></div>
				<div><input type="submit" name="action" value="Publish" onclick="return(confirm('Вы уверены?'))"></div>
				</form><%
			}
			pm.close();
    	}
    }
%>


  </body>
</html>