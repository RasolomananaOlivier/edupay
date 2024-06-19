<%@page import="model.Payment"%>
<%@page import="util.PaymentPeriod"%>
<%@page import="model.MonthAmount"%>
<%@page import="model.EquipmentAmount"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
Payment payment = (Payment) request.getAttribute("payment");
List<PaymentPeriod> usedPaymentPeriods = (List<PaymentPeriod>) request.getAttribute("usedPaymentPeriods");
List<PaymentPeriod> disabledPaymentPeriods = (List<PaymentPeriod>) request.getAttribute("disabledPaymentPeriods");
MonthAmount monthAmount = (MonthAmount) request.getAttribute("monthAmount");
EquipmentAmount equipmentAmount = (EquipmentAmount) request.getAttribute("equipmentAmount");
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
                <span><%= 52 %></span>
            <label for="PaymentPeriod.<%= paymentPeriod.toString() + "." + uniqueId %>">
                <%= paymentPeriod.getLabel() %>
                <% if (paymentPeriod == PaymentPeriod.EQUIPMENT) { %>
                    <span><%= equipmentAmount.getValue() %></span>
                <% } else { %>
                    <span><%= monthAmount.getValue() %></span>
                <% } %> Ar
            </label><br>
        <% } %>

        <input type="Submit" />
    </form>
</body>
</html>