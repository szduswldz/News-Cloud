<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Calendar"%>
<%@ page import="user.*"%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오늘의 기사</title>
</head>
<body>
<%

			Rserve_C rc = new Rserve_C();
			
			Calendar cal = Calendar.getInstance();

			String[] press_names = rc.crawling();

			String cate = rc.getCategory();
			String subCate = rc.getSubCategory();

			rc.getDescription("summary1 = \"준비중\"");
			rc.getDescription("summary2 = \"준비중\"");

			int press1 = -1;
			if (request.getParameter("press1") != null) {
				press1 = Integer.parseInt(request.getParameter("press1"));
			}

			int press2 = -1;
			if (request.getParameter("press2") != null) {
				press2 = Integer.parseInt(request.getParameter("press2"));
			}

			int pressFlag = 0;
			if (request.getParameter("pressFlag") != null) {
				pressFlag = Integer.parseInt(request.getParameter("pressFlag"));
			}

			String summary1 = "준비중";
			String summary2 = "준비중";

			LogDAO logDAO = new LogDAO();
			String userID = null;
			if (pressFlag == 1) {
				System.out.println(press_names[press1] + "" + press_names[press2]);
				rc.assignPress(press_names[press1], press_names[press2]);
				rc.execute(rc.getCategory(), rc.getSubCategory());
				summary1 = rc.getDescription("summary1");
				summary2 = rc.getDescription("summary2");
				logDAO.logging(userID, press_names[press1], cate, subCate,
						cal.get(Calendar.YEAR) + "-" + 
								(cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE)
								+ " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":"
								+ cal.get(Calendar.SECOND));
				logDAO.logging(userID, press_names[press2], cate, subCate,
						cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE)
								+ " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":"
								+ cal.get(Calendar.SECOND));
			}

			
		%>

		<!-- Form Section -->
		<div class="row">
			<div class="col-lg-12">
				<label>카테고리</label>
				<div class="control-group form-group">
					<div class="controls">
						<div class="row">
							<div class="col-lg-4">
								<select name="category" class="form-control" disabled>
									<option value=0><%=cate%></option>
								</select>
							</div>
							<div class="col-lg-4">
								<select name="subcategory" class="form-control" disabled>
									<option value=0><%=subCate%></option>
								</select>
							</div>
						</div>
						<p class="help-block"></p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-8 mb-4">
			<div class="control-group form-group">
					<div class="controls">
						<label>기간</label> <input type="text" class="form-control"
							id="period"
							value=<%=cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE)%>>
						<p class="help-block"></p>
					</div>
				</div>
			</div>
		</div>

		<%
			
		%>

		<!-- 언론사 Section -->
		<div class="row">
			<div class="col-lg-12 mb-4">
				<div class="col-lg-6 mb-4">
					<select name="press1" class="form-control">
						<%
							for (int i = 0; i < press_names.length; i++) {
								if (!rc.getArticleSum()[i].equals("1")) {
						%>
						<option value=<%=i%>><%=press_names[i]%></option>

						<%
							}
							}
						%>
					</select>
				</div>
				<div class="col-lg-6 mb-4">
					<select name="press2" class="form-control">
						<%
							for (int i = 0; i < press_names.length; i++) {								
								if (!rc.getArticleSum()[i].equals("1")) {
								%>
								<option value=<%=i%>><%=press_names[i]%></option>

								<%
									}
									}
								%>
							</select>
						</div>
					</div>
				</div>
				<div class="col-lg-6 col-sm-6 portfolio-item">
            <div class="card h-100">
               <%
                  if (pressFlag == 1) {
               %>
               <a href="Board.jsp?press=press_idx1"><img class="card-img-top"
                  src="C:\RPrj\data\wc_image.png" alt=""></a>
               <div class="card-body">
                  <p class="card-text"><%=summary1%></p>
               </div>
               <%
                  } else {
               %>
               <a href="#"><img class="card-img-top"
                  src="‪C:\RPrj\big.png" alt=""></a>
               <div class="card-body">
                  <p class="card-text">분석 전 상태</p>
               </div>
               <%
                  }
               %>
            </div>
            <script>
            function selectPress() {
                var press1 = document.getElementsByName('press1')[0].value;
                var press2 = document.getElementsByName('press2')[0].value;

                if (press1 != press2) {

                   location.href = 'today.jsp?press1=' + press1 + '&press2='
                         + press2 + '&pressFlag=1';

                } else {
                   alert("언론사가 중복 선택 되었습니다.");
                }
             }
          </script>
</div>
</body>
</html>