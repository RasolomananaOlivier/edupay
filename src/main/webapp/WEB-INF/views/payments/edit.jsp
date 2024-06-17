<%@page import="model.Payment"%>
<%@page import="util.PaymentPeriod"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
Payment payment = (Payment) request.getAttribute("payment");
List<PaymentPeriod> usedPaymentPeriods = (List<PaymentPeriod>) request.getAttribute("usedPaymentPeriods");
List<PaymentPeriod> disabledPaymentPeriods = (List<PaymentPeriod>) request.getAttribute("disabledPaymentPeriods");
Random random = new Random();
int uniqueId = random.nextInt();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<h1>Edit payment</h1>

        <div><%= payment.getId() %></div>
        <div><%= usedPaymentPeriods.toString() %></div>
        <div><%= disabledPaymentPeriods.toString() %></div>

    <form method="POST" action="<%= request.getContextPath() + "/payments/update?paymentId=" + payment.getId() %>">
        <input type="text" name="text" value="" />
        
        <% for (PaymentPeriod paymentPeriod : PaymentPeriod.values() ) { %>
            <% if (disabledPaymentPeriods.contains(paymentPeriod)) { %>
                <input type="checkbox" id="PaymentPeriod.<%= paymentPeriod.toString() + "." + uniqueId %>" name="disabledPaymentPeriods" value="<%= paymentPeriod %>" checked="" disabled="">
            <% } else if (usedPaymentPeriods.contains(paymentPeriod)) { %>
                <input type="checkbox" id="PaymentPeriod.<%= paymentPeriod.toString() + "." + uniqueId %>" name="paymentPeriods" value="<%= paymentPeriod %>" checked="">
            <% } else { %>
                <input type="checkbox" id="PaymentPeriod.<%= paymentPeriod.toString() + "." + uniqueId %>" name="paymentPeriods" value="<%= paymentPeriod %>">
            <% } %>
            <label for="PaymentPeriod.<%= paymentPeriod.toString() + "." + uniqueId %>"><%= paymentPeriod.getLabel() %></label><br>
        <% } %>

        <input type="Submit" />
    </form>
</body>
</html>