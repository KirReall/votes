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
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>
  <body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user == null) {
%>
    	<p>Hello! <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>, please.</p>
<%	
    } else {
%>
    	<p>Hello, <%= user.getNickname() %>! (You can
    	<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
		<div><a href="list.jsp">new posts</a> <a href ="comments.jsp">comments</a> </div>
<%	
    }
%>  
  </body>
</html>