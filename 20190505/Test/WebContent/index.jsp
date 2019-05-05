<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="usr.*"%>
<html>
  <head>
    <title>Rserve</title>
  </head>
  <body>
<h1> hello world </h1>
<%
  Rserve_C rc = new Rserve_C();
   String[] press_names = rc.crawling();
  for(int i=0; i<press_names.length; i++) {
  	out.println(press_names[i]);
  }

 
%>
  </body>
</html>
