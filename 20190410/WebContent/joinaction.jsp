<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="java.io.PrintWriter"%>
 <%@page import="java.sql.Connection" %>
<%
    request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property = "userID" />
<jsp:setProperty name="user" property = "userPassword" />
<jsp:setProperty name="user" property = "userName" />
<jsp:setProperty name="user" property = "userPhoneNumber" />
<jsp:setProperty name="user" property = "userEmail" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>News Cloud</title>
</head>
String userID=null;
        if (session.getAttribute("userID")!= null){
            userID=(String) session.getAttribute("userID");
        }
        if (userID!=null){
            PrintWriter script =response.getWriter();
            script.println("<script>");
            script.println("alert('이미 로그인되어 있습니다.')");
            script.println("<location.href='main.jsp'");
            script.println("</script>");
            
        }


<body>
    <%    //null 값이 있는지 검사
        if (user.getUserID()==null || user.getUserPassword()==null || user.getUserName()==null ||
        		 user.getUserPhoneNumber()==null|| user.getUserEmail()==null){
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert(' 입력이 안 된 사항이 있습니다.')");
            script.println("history.back()");
            script.println("</script>");
        }else{
            UserDAO userDAO = new UserDAO();
            int result = userDAO.join(user);
            if (result == -1) { //아이디가 중복되는지 확인 (userID는 Primary Key이기 때문.)
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('이미 존재하는 아이디입니다.')");
                script.println("history.back()");
                script.println("</script>");
                session.setAttribute("userID",user.getUserID());
            } 
             else {//회원가입이 되었을때 메인페이지로 이동시켜준다
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("location.href = 'main.jsp'");
                script.println("</script>");

            }
 
        }
    
        
    %>
</body>
</html>
 