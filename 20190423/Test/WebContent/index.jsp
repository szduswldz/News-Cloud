<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="usr.Rserve_C" %>
<html>
  <head>
    <title>Rserve</title>
  </head>
  <body>
<h1> hello world </h1>
<%
  Rserve_C rc = new Rserve_C();
  String[] rCate = rc.returnRClass();
  for(int i=0; i<rCate.length; i++) {
  	out.println(rCate[i]);
  }
%>
  </body>
</html>