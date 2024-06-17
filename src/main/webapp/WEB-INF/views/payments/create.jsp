<%@page import="model.PaymentItem"%>
<%@page import="model.Student"%>
<%@page import="util.PaymentPeriod"%>
<%@page import="model.AcademicSession"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Student student = (Student) request.getAttribute("student");
List<PaymentPeriod> disabledPaymentPeriods = (List<PaymentPeriod>) request.getAttribute("disabledPaymentPeriods");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <p><%= student.toString() %></p>

    <p>
        <%= disabledPaymentPeriods.toString() %>
    </p>

    <form method="POST" action="<%= request.getContextPath() + "/payments/store" %>">
        <input type="hidden" name="studentId" value="<%= student.getId() %>" />
        
        <% for (PaymentPeriod paymentPeriod : PaymentPeriod.values() ) { %>
            <% if (disabledPaymentPeriods.contains(paymentPeriod)) { %>
                <input type="checkbox" id="PaymentPeriod.<%= paymentPeriod.toString() %>" name="disabledPaymentPeriods" value="<%= paymentPeriod %>" checked="" disabled="">
            <% } else { %>
                <input type="checkbox" id="PaymentPeriod.<%= paymentPeriod.toString() %>" name="paymentPeriods" value="<%= paymentPeriod %>">
            <% } %>
            <label for="PaymentPeriod.<%= paymentPeriod.toString() %>"><%= paymentPeriod.getLabel() %></label><br>
        <% } %>

        <input type="submit" value="Create" />
    </form>
</body>
</html>