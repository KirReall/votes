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
    	<div><input type="text" size="4" value="1" onkeypress="var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode; if ( keyCode == 13 ) { var page_no = parseInt(this.value, 10); if ( !isNaN(page_no)) { window.location.href = 'edit.jsp?post='+page_no+''; return false;} else { this.value = 1; return false;} }"></div>
<%
		String parmetr = request.getParameter("list");
		if ((parmetr=="")||(parmetr==null)){
			response.sendRedirect("/admin/list.jsp?list=1");
		} else {
			Integer numberOfPage = Integer.valueOf(parmetr);
			
			PersistenceManager pm = PMF.get().getPersistenceManager(); //JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
			Query q = pm.newQuery(Post.class);
			q.setFilter("published == isPublished");
			q.declareParameters("Boolean isPublished");
			//q.setOrdering(orderField+" desc, "+seconOrderField+" desc");
			q.setRange((numberOfPage-1)*20, numberOfPage*20+1);
			List<Post> posts = (List<Post>) q.execute(false);
			//List<Post> posts = (List<Post>) pm.newQuery(query).execute();
			if (posts.isEmpty()) {
%>
			 	<p>Новых постов нет</p>
<%
			} else {
				if (numberOfPage>1){
					%><a href="list.jsp?list=<%=String.valueOf(numberOfPage -1)%>">prev</a><%
				}
				if (posts.size() == 21){
					%><a href="list.jsp?list=<%=String.valueOf(numberOfPage +1)%>">next</a><%
					//posts.remove(21);
				}
				%><form action="/votes/deleteposts" method="post"><%
				for (int i = 0; i < Math.min(posts.size(),20); i++){	
					Post post = posts.get(i);
					%><div><input type="checkbox" name="post" value="<%= post.getId() %>"><p><%= post.getContent() %></p><div><%
					for (Long id : post.getAnswers()) {
						Answer answer = pm.getObjectById(Answer.class, id);
						%><blockquote><%= answer.getContent()+ " - "+ answer.getRate() %></blockquote><%
					}
					%></div>
					<a href="edit.jsp?post=<%=post.getId()%>">edit</a></div><%
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