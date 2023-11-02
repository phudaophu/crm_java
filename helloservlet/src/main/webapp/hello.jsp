<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
<!DOCTYPE html>

<%--
		<%! code java %>: Khai báo biến
		<% %>: Thẻ xử lý logic code
		<%= %>: Xuất giá trị của biến ra HTML
--%>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	
	<%! int count = 0; %>
	<% 
	int cnt = 1;
	count++;
	cnt++;%>
	
	<body>
		Hello Servlet
		<h1><%= count %></h1>
		<h2><%= cnt %></h2>
		
 		<% 
			if(count%2==0){
				%>
				<h1 style="color:red"> <%= count %> </h1>
			<% }
		  	else{ %>
				<h1 style="color:black"> <%= count %> </h1>
				<%
		}
		%> 
		
		
<%-- 		<h1 style="color: <%= (count % 2 == 0) ? "red" : "black" %>;">
        <%= count %>
    	</h1> --%>
    	
<%--      	<% 
			if(count%2==0){
			%>
			
		<h1 style="color:red"> <%=count%> </h1>
			
			<%
			}else{
			%>
			
		<h1 style="color:black"> <%=count%> </h1>
		    
		   <%
			}
		   %> 
--%>
    	
	</body>
</html>