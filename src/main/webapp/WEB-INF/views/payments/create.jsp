<%@page import="com.edupay.model.Student"%>
<%@page import="com.edupay.model.MonthAmount"%>
<%@page import="com.edupay.model.EquipmentAmount"%>
<%@page import="com.edupay.util.PaymentPeriod"%>
<%@page import="com.edupay.util.CurrencyFormatter"%>
<%@page import="com.edupay.util.DateFormatter"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
Student student = (Student) request.getAttribute("student");
List<PaymentPeriod> disabledPaymentPeriods = (List<PaymentPeriod>) request.getAttribute("disabledPaymentPeriods");
MonthAmount monthAmount = (MonthAmount) request.getAttribute("monthAmount");
EquipmentAmount equipmentAmount = (EquipmentAmount) request.getAttribute("equipmentAmount");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <link
      href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.css"
      rel="stylesheet"
    />
    <title>Payer la bourse</title>
</head>
<body>
	<jsp:include page="/WEB-INF/components/header.jsp" />
    
    <main class="px-3 py-5 sm:px-40 md:px-60 lg:px-80">
		<h1 class="text-3xl font-medium">Payer la bourse</h1>
		<p class="mb-5 text-slate-600">Veuillez remplir les champs
			pour payer la bourse de l'étudiant(e)</p>

        <div class="flex flex-col gap-5 mb-10">
            <div class="flex gap-10">
                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Matricule</div>
                    <div class="font-semibold text-slate-800"><%= student.getId() %></div>
                </div>
                
                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Nom</div>
                    <div class="font-semibold text-slate-800"><%= student.getName() %></div>
                </div>

                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Date de naissance</div>
                    <div class="font-semibold text-slate-800"><%= DateFormatter.format(student.getBirthDate()) %></div>
                </div>

                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Sexe</div>
                    <div class="font-semibold text-slate-800"><%= student.getGender().getLabel() %></div>
                </div>
            </div>

            <div class="flex gap-10">
                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Institut</div>
                    <div class="font-semibold text-slate-800"><%= student.getFaculty().getName() %></div>
                </div>

                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Niveau</div>
                    <div class="font-semibold text-slate-800"><%= student.getLevel().getName() %></div>
                </div>

                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Année universitaire</div>
                    <div class="font-semibold text-slate-800"><%= student.getSession().getYear() %>-<%= student.getSession().getYear() + 1 %></div>
                </div>
            </div>
        </div>

        <form method="POST" action="<%= request.getContextPath() + "/payments/store" %>">
            <input type="hidden" name="studentId" value="<%= student.getId() %>" />

            <div class="mb-6 flex flex-col gap-5">
                <div class="text-sm text-slate-700">Mois</div>

                <table>
                    <tbody class="divide-y divide-gray-200">
                        <% for (PaymentPeriod paymentPeriod : PaymentPeriod.values() ) { %>
                            <% if (disabledPaymentPeriods.contains(paymentPeriod)) { %>
                                <tr class="ms-2 text-gray-400 dark:text-gray-500">
                            <% } else { %>
                                <tr class="ms-2">
                            <% } %>
                                <td class="py-2 -my-1">
                                    <% if (disabledPaymentPeriods.contains(paymentPeriod)) { %>
                                        <input disabled checked id="PaymentPeriod.<%= paymentPeriod.toString() %>" type="checkbox" value="<%= paymentPeriod %>" class="text-gray-300 bg-gray-100 border-gray-300 rounded focus:ring-gray-500 dark:focus:ring-gray-300 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-300">
                                    <% } else { %>
                                        <input type="checkbox" id="PaymentPeriod.<%= paymentPeriod.toString() %>" name="paymentPeriods" value="<%= paymentPeriod %>" class="text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
                                    <% } %>
                                </td>

                                <td>
                                    <label for="PaymentPeriod.<%= paymentPeriod.toString() %>" class="hover:cursor-pointer block">
                                        <%= paymentPeriod.getLabel() %>
                                    </label>
                                </td>

                                <td class="text-right">
                                    <% if (paymentPeriod == PaymentPeriod.EQUIPMENT) { %>
                                        <span><%= CurrencyFormatter.format(equipmentAmount.getValue()) %>
                                    <% } else { %>
                                        <%= CurrencyFormatter.format(monthAmount.getValue()) %>
                                    <% } %>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <div class="flex justify-end">
                <button 
                    type="submit"
                    class="w-full rounded-full bg-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 sm:w-auto dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                >
                    Confirmer
                </button>
            </div>
        </form>
    </main>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.js"></script>
</body>
</html>