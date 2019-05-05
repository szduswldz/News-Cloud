<%@ page import="user.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.io.PrintWriter"%>
<%@ page import="user.UserDAO"%>
 <%@page import="java.sql.Connection" %>
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property = "userID" />
<jsp:setProperty name="user" property = "userPassword" />
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>News CLoud</title>
</head>
<body>
<%
		String userID=null;
		if (session.getAttribute("userID")!= null){
		userID=(String) session.getAttribute("userID");
		}
		if (userID!=null){ //세션이 이미 존재, 중복 로그인 방지
  		PrintWriter script =response.getWriter();
   		script.println("<script>");
		script.println("alert('이미 로그인되어 있습니다.')");
		script.println("<location.href='main.jsp'");
		script.println("</script>");  
		}
        UserDAO userDAO = new UserDAO();
		int result = userDAO.login(user.getUserID(), user.getUserPassword());
        if (result == 1) { //로그인 성공
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("location.href = 'main.jsp'");
            script.println("</script>");
            session.setAttribute("userID",user.getUserID());
            


        } else if (result == 0) {//비밀번호 불일치
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('비밀번호가 틀립니다.')");
            script.println("history.back()");
            script.println("</script>");
        } else if (result == -1) { //아이디 없음
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('존재하지 않는 아이디입니다.')");
            script.println("history.back()");
            script.println("</script>");
        } else if (result == -2) { //데이터베이스 오류
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('데이터베이스 오류가 발생했습니다.')");
            script.println("history.back()");
            script.println("</script>");
        }
        
%>
</body>
</html>