<%@page import="model.PaymentItem"%>
<%@page import="model.Payment"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
List<Payment> payments = (List<Payment>) request.getAttribute("payments");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<p>Payments:</p>
	<ul>
		<%
		for (Payment payment : payments) {
		%>
		<li>
			<p><%=payment.getId()%></p>

			<ul>
				<%
				for (PaymentItem paymentItem : payment.getPaymentItems()) {
				%>

				<li><%= paymentItem.getPeriod().getLabel() %>: <%= paymentItem.getAmount() %></li>

				<%
				}
				%>
			</ul>
		</li>
		<%
		}
		%>
	</ul>
</body>
</html>