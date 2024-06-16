<%@page import="model.PaymentItem"%>
<%@page import="model.Payment"%>
<%@page import="util.PaymentPeriod"%>
<%@page import="model.AcademicSession"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
List<Payment> students = (List<Payment>) request.getAttribute("students");
List<PaymentPeriod> paymentPeriods = (List<PaymentPeriod>) request.getAttribute("paymentPeriods");
List<AcademicSession> academicSessions = (List<AcademicSession>) request.getAttribute("academicSessions");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <p>
    <%= students.toString() %>
    </p>
    <p>
    <%= paymentPeriods.toString() %>
    </p>
    <p>
    <%= academicSessions.toString() %>
    </p>
</body>
</html>