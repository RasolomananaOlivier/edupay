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
<meta charset="UTF-8">
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
			<a href="<%= request.getContextPath() + "/payments/edit?paymentId=" + payment.getId() %>">Mettre à jour</a>
			<a href="<%= request.getContextPath() + "/payments/delete?paymentId=" + payment.getId() %>">Supprimer</a>

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