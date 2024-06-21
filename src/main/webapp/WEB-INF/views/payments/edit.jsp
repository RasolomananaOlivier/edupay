<%@page import="model.Payment"%>
<%@page import="util.PaymentPeriod"%>
<%@page import="model.MonthAmount"%>
<%@page import="model.EquipmentAmount"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Random"%>
<%@page import="util.CurrencyFormatter"%>
<%@page import="util.DateFormatter"%>
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
    <script src="https://cdn.tailwindcss.com"></script>
    <link
      href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.css"
      rel="stylesheet"
    />
    <title>Modifier le paiement</title>
</head>
<body>
	<jsp:include page="/WEB-INF/components/header.jsp" />
    
    <main class="px-3 py-5 sm:px-40 md:px-60 lg:px-80">
		<h1 class="text-3xl font-medium">Modifier le paiement de la bourse</h1>
		<p class="mb-5 text-slate-600">Veuillez remplir les champs
			pour modifier le payement de la bourse de l'étudiant(e)</p>

        <div class="flex flex-col gap-5 mb-10">
            <div class="flex gap-10">
                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Identifiant</div>
                    <div class="font-semibold text-slate-800"><%= payment.getId() %></div>
                </div>

                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Date de paiment</div>
                    <div class="font-semibold text-slate-800"><%= DateFormatter.format(payment.getCreatedAt()) %></div>
                </div>
            </div>

            <div class="flex gap-10">
                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Matricule</div>
                    <div class="font-semibold text-slate-800"><%= payment.getStudent().getId() %></div>
                </div>
                
                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Nom</div>
                    <div class="font-semibold text-slate-800"><%= payment.getStudent().getName() %></div>
                </div>

                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Date de naissance</div>
                    <div class="font-semibold text-slate-800"><%= DateFormatter.format(payment.getStudent().getBirthDate()) %></div>
                </div>

                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Sexe</div>
                    <div class="font-semibold text-slate-800"><%= payment.getStudent().getGender().getLabel() %></div>
                </div>
            </div>

            <div class="flex gap-10">
                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Institut</div>
                    <div class="font-semibold text-slate-800"><%= payment.getStudent().getFaculty().getName() %></div>
                </div>

                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Niveau</div>
                    <div class="font-semibold text-slate-800"><%= payment.getLevel().getName() %></div>
                </div>

                <div class="flex flex-col">
                    <div class="text-sm text-slate-700">Année universitaire</div>
                    <div class="font-semibold text-slate-800"><%= payment.getAcademicSession().getYear() %>-<%= payment.getAcademicSession().getYear() + 1 %></div>
                </div>
            </div>
        </div>

        <form method="POST" action="<%= request.getContextPath() + "/payments/update?paymentId=" + payment.getId() %>">
            <input type="hidden" name="studentId" value="<%= payment.getStudent().getId() %>" />

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
                                    <% } else if (usedPaymentPeriods.contains(paymentPeriod)) { %>
                                        <input checked type="checkbox" id="PaymentPeriod.<%= paymentPeriod.toString() %>" name="paymentPeriods" value="<%= paymentPeriod %>" class="text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
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

		<h1>Edit payment</h1>

        <div><%= payment.getId() %></div>
        <div><%= usedPaymentPeriods.toString() %></div>
        <div><%= disabledPaymentPeriods.toString() %></div>

    <form method="POST" action="<%= request.getContextPath() + "/payments/update?paymentId=" + payment.getId() %>">

        <% for (PaymentPeriod paymentPeriod : PaymentPeriod.values() ) { %>
            <% if (disabledPaymentPeriods.contains(paymentPeriod)) { %>
                <input type="checkbox" id="PaymentPeriod.<%= paymentPeriod.toString() + "." + uniqueId %>" name="disabledPaymentPeriods" value="<%= paymentPeriod %>" checked="" disabled="">
            <% } else if (usedPaymentPeriods.contains(paymentPeriod)) { %>
                <input type="checkbox" id="PaymentPeriod.<%= paymentPeriod.toString() + "." + uniqueId %>" name="paymentPeriods" value="<%= paymentPeriod %>" checked="">
            <% } else { %>
                <input type="checkbox" id="PaymentPeriod.<%= paymentPeriod.toString() + "." + uniqueId %>" name="paymentPeriods" value="<%= paymentPeriod %>">
            <% } %>
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