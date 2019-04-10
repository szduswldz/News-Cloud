<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name ="viewport" content="width=device-width",initial-scale="1">
<title>News Cloud</title>
<link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
    <% 
        String userID=null;
        if  (session.getAttribute("userID")!=null){ //세션이 존재한다
            userID = (String) session.getAttribute("userID"); //그 값을 userID에 저장한다. 
        }
    %>
    <nav class="navbar navbar-default">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                aria-expanded="false">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="main.jsp">News Cloud </a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="main.jsp">처음화면</a></li>
                
            </ul>
            <%
                if(userID==null){ //회원가입이 되어있지않다면 로그인/회원가입 메뉴를 표시하게한다.
            
            %>
               <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle"
                        data-toggle="dropdown" role="button" aria-haspopup="true"
                        aria-expanded="false">접속하기<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="login.jsp">로그인</a></li>
                        <li><a href="join.jsp">회원가입</a></li>
                    </ul>
                </li>
            </ul>               
            <%
                } else { //회원가입이 되어있으면 로그아웃메뉴를 표시하게한다.
            %>
              <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle"
                        data-toggle="dropdown" role="button" aria-haspopup="true"
                        aria-expanded="false">회원관리<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="logoutaction.jsp">로그아웃</a></li>
                    </ul>
                </li>
            </ul>
            <%
              }
            %>
       </div>
       <h1 class="my-4">Welcome to News Cloud!!!</h1>
       <div class="col-lg-4 mb-4">
            <div class="card h-100">
               <h4 class="card-header">오늘의 뉴스</h4>
               <div class="card-body">
                  <p class="card-text">카테고리 맞춰 실시간 뉴스를 제공합니다.</p>
               </div>
                       
               <div class="card-footer">
                  <button type="submit" class="btn btn-primary"
                     onClick="moveToday();">바로가기</button>
               </div>
         	</div>
         </div>
                        
       <div class="col-lg-4 mb-4">
            <div class="card h-100">
               <h4 class="card-header">나를 위한 기사</h4>
               <div class="card-body">
                  <p class="card-text">사용자가 자주보는 카테고리의 뉴스를 제공합니다.</p>
               </div>
               <div class="card-footer">
                  <button type="submit" class="btn btn-primary"
                     onClick="moveToday();">바로가기</button>
                    
               </div>
               </div>
            </div>
    </nav>
    ​<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="js/bootstrap.js"></script>
    
</body>
</html>