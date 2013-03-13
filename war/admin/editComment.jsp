<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.jdo.Query" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="votes.server.PMF" %>
<%@ page import="votes.server.CommentToAnswer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
    }
%>
    <p>Hello, <%= user.getNickname() %>! (You can
    <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%		
	String parmetr = request.getParameter("comment");
	if ((parmetr=="")||(parmetr==null)){
		response.sendRedirect("/admin/comments.jsp?list=1");
	} else {
		Long id = Long.valueOf(parmetr);
			
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			CommentToAnswer comment = pm.getObjectById(CommentToAnswer.class, id);
%>
		<form action="/votes/editcomment?id=<%= comment.getId() %>" method="post">
			<div><output name="id"><%= comment.getId() %></output><textarea name="content" rows="3" cols="60"><%= comment.getContent() %></textarea></div>
			<div><input type="submit" value="Edit Post" /></div>
		</form>
<%			
		} catch (Exception e) {
			String mess = e.getMessage();
%>
			<div><%= mess %></div>
<%
		} finally {
			pm.close();
		}
		
	}
%>
</body>
</html>