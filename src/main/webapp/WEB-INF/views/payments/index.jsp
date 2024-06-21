<%@page import="model.PaymentItem"%>
<%@page import="model.Payment"%>
<%@page import="util.CurrencyFormatter"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
List<Payment> payments = (List<Payment>) request.getAttribute("payments");
SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);

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
    <style>
      /* Custom CSS to handle the checkbox state and sibling elements */
      .paymentDetails > input:checked ~ .content {
        display: block;
      }
      /* Custom CSS for label text change */
      .paymentDetails > input:checked + label::after {
        content: "Voir moins";
      }
      .paymentDetails > label::after {
        content: "Voir plus";
      }
    </style>
<title>Liste des paiements</title>
</head>

  <body>
	<jsp:include page="/WEB-INF/components/header.jsp" />

    <main class="px-5 py-3">
      <h1 class="text-3xl font-bold">Liste des paiements</h1>

      <div class="flex justify-between items-end">
        <div class="text-slate-600 mt-1">
          Retrouver ici la liste des paiements effectués
        </div>
      </div>

      <ul class="flex flex-col gap-4 pt-5">
        
		<%
		for (Payment payment : payments) {
		%>
		<li class="flex flex-col gap-5 rounded-md p-5 border">
			<div class="flex justify-between">
            	<div class="flex gap-10">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Identifiant</div>
						<div class="font-semibold text-slate-800"><%= payment.getId() %></div>
					</div>
			
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Date de paiement</div>
						<div class="font-semibold text-slate-800"><%= dateFormatter.format(payment.getCreatedAt()) %></div>
					</div>
				</div>

				<div class="flex gap-1">
				<a
					href="<%= request.getContextPath() + "/payments/receipt?paymentId=" + payment.getId() %>"
					target="_blank"
					class="text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
					>Reçu</a
				>
				<a
					href="<%= request.getContextPath() + "/payments/edit?paymentId=" + payment.getId() %>"
					class="text-white bg-green-700 hover:bg-green-800 focus:outline-none focus:ring-4 focus:ring-green-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
					>Modifier</a
				>
				<a
					href="<%= request.getContextPath() + "/payments/delete?paymentId=" + payment.getId() %>"
					class="text-red-700 hover:text-white border border-red-700 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:hover:bg-red-600 dark:focus:ring-red-900"
					>Supprimer</a
				>
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
					<div class="font-semibold text-slate-800"><%= dateFormatter.format(payment.getStudent().getBirthDate()) %></div>
				</div>

				<div class="flex flex-col">
					<div class="text-sm text-slate-700">Sexe</div>
					<div class="font-semibold text-slate-800"><%= payment.getStudent().getGender().getLabel() %></div>
				</div>

				<div class="flex flex-col">
					<div class="text-sm text-slate-700">Institut</div>
					<div class="font-semibold text-slate-800"><%= payment.getStudent().getFaculty().getName() %></div>
				</div>
			</div>

          <div class="flex gap-10">
            <div class="flex flex-col">
              <div class="text-sm text-slate-700">Année universitaire</div>
              <div class="font-semibold text-slate-800"><%= payment.getAcademicSession().getYear() %>-<%= payment.getAcademicSession().getYear() + 1 %></div>
            </div>

            <div class="flex flex-col">
              <div class="text-sm text-slate-700">Niveau</div>
              <div class="font-semibold text-slate-800"><%= payment.getLevel().getName() %></div>
            </div>

            <div class="flex flex-col">
              <div class="text-sm text-slate-700">Nombre de mois</div>
              <div class="font-semibold text-slate-800"><%= payment.getPaymentItems().size() %></div>
            </div>

            <div class="flex flex-col">
              <div class="text-sm text-slate-700">Total</div>
              <div class="font-semibold text-slate-800"><%= CurrencyFormatter.format(payment.getPaymentItems().stream().mapToInt(paymentItem -> paymentItem.getAmount()).sum()) %></div>
            </div>
          </div>

			<div class="paymentDetails">
				<!-- Checkbox hidden but accessible for toggling -->
				<input type="checkbox" id="toggleCheckbox.<%= payment.getId() %>" class="hidden" />
				<label
					for="toggleCheckbox.<%= payment.getId() %>"
					class="cursor-pointer py-2 rounded hover:underline hover:text-blue-500 focus:outline-none"
				>
					<!-- Empty label, text will be added by CSS -->
				</label>

				<div class="content mt-4 hidden">
					<table>
						<thead>
							<tr>
								<th class="border bg-slate-500 text-white px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Mois</th>
								<th class="border bg-slate-500 text-white px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Montant</th>
							</tr>
						</thead>

						<tbody class="divide-y divide-gray-200">
							<%
								for (PaymentItem paymentItem : payment.getPaymentItems()) {
							%>
								<tr class="odd:bg-white even:bg-slate-50">
									<td class="text-left inline-block mr-5"><%= paymentItem.getPeriod().getLabel() %></td>
									<td class="text-right"><%= CurrencyFormatter.format(paymentItem.getAmount()) %></td>
								</tr>
							<%
								}
							%>
							<tr class="">
								<td class="border border-slate-500 bg-slate-500 text-white px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total</td>
								<td class="border border-slate-500 text-right font-semibold"><%= CurrencyFormatter.format(payment.getPaymentItems().stream().mapToInt(paymentItem -> paymentItem.getAmount()).sum()) %></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</li>
		<%
		}
		%>
      </ul>
    </main>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.js"></script>
  </body>
</html>