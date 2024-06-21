package util;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import model.Payment;

public class ReceiptFormatter {
	public static String formatHTML(Payment payment) {
		// Example raw HTML content
		String rawHtmlContent = """
				<!DOCTYPE html>
				<html lang="en">
				  <head>
				    <meta charset="UTF-8" />
				    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
				    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
				    <title>Reçu : {0}</title>
				    <style>
				      .receipt-title {
				        text-align: center;
				        font-size: x-large;
				        margin-bottom: 3rem;
				      }
				      .table-container {
				        margin: 5rem 0;
				      }
				      .receipt-table {
				        width: 90%;
				        margin: 0 auto;
				        border-collapse: collapse;
				        border-spacing: 0;
				      }
				      th {
				        text-align: center;
				        padding: 1rem;
				      }
				      td {
				        padding: 0.7rem;
				      }
				      .order-infos h2 {
				        font-size: larger;
				      }
				      .order-info {
				        font-weight: bold;
				      }
				      .table-head-row {
				        background-color: #263238;
				        color: #fff;
				      }
				      .table-head-cell {
				        border-right: solid 2px #fff;
				      }
				      .table-head-cell:last-of-type {
				        border-right: none;
				      }
				      .order-table-row:nth-child(2n + 1) {
				        background-color: #cfd8dc;
				      }
				      .total-text-cell {
				        background-color: #263238;
				        color: #fff;
				        text-align: center;
				        text-transform: uppercase;
				        font-weight: bold;
				        letter-spacing: 0.2rem;
				        border: solid 2px #263238;
				      }
				      .car-name {
				        font-weight: bold;
				      }
				      .cell-void {
				        border: none;
				      }
				      .car-quantity {
				        text-align: center;
				      }
				      .car-price {
				        text-align: right;
				      }
				      .order-subtotal {
				        text-align: right;
				      }
				      .order-total {
				        padding: 1rem;
				        font-weight: bold;
				        text-align: right;
				        border: solid 2px #263238;
				      }
				      .total-text {
				        font-weight: bold;
				      }
				      .receipt-container {
				        margin: 1rem;
				        padding: 1rem 2rem 7rem 2rem;
				        border: dotted 5px #000;
				      }
				    </style>
				  </head>
				  <body>
				    <div class="receipt-container">
				        <h1 class="receipt-title">
				          Aujourd'hui le :
				          <span class="order-info">{1}</span>
				        </h1>
				      <div class="order-infos">
				        <h2>
				          Reçu :
				          <span class="order-info">
				            {2}
				          </span>
				        </h2>
				        <h2>
				          Matricule :
				          <span class="order-info">
				            {3}
				          </span>
				        </h2>
				        <h2>
				          Nom :
				          <span class="order-info">
				            {4}
				          </span>
				        </h2>
				        <h2>
				          Né(e) le :
				          <span class="order-info">
				            {5}
				          </span>
				        </h2>
				        <h2>
				          Sex :
				          <span class="order-info">
				            {6}
				          </span>
				        </h2>
				        <h2>
				          Institution :
				          <span class="order-info">{7}</span>,
						</h2>
						<h2>
							Niveau :
							<span class="order-info">{8}</span>
						</h2>
				      </div>
				      <div class="table-container">
				        <table class="receipt-table">
				          <thead>
				            <tr class="table-head-row">
				              <th class="table-head-cell">Mois</th>
				              <th class="table-head-cell">Montant</th>
				            </tr>
				          </thead>
				          <tbody>
				            {9}
				            <tr class="total-table-row">
				              <td class="total-text-cell">Total</td>
				              <td class="order-total">
				                {10}
				              </td>
				            </tr>
				          </tbody>
				        </table>
				      </div>
				    </div>
				  </body>
				</html>
				""";

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);

		String formattedHtmlContent = rawHtmlContent
				.replace("{0}", payment.getId())
				.replace("{1}", dateFormatter.format(payment.getCreatedAt()))
				.replace("{2}", payment.getId())
				.replace("{3}", payment.getStudent().getId())
				.replace("{4}", payment.getStudent().getName())
				.replace("{5}", dateFormatter.format(payment.getStudent().getBirthDate()))
				.replace("{6}", payment.getStudent().getGender().getLabel())
				.replace("{7}", payment.getStudent().getFaculty().getName())
				.replace("{8}", payment.getStudent().getLevel().getName())
				.replace("{9}", payment.getPaymentItems().stream()
						.map(paymentItem -> """
								<tr class="order-table-row">
								<td class="car-name">
								""" +
								paymentItem.getPeriod().getLabel() +
								"""
										</td>
										<td class="car-quantity">
										""" +
								CurrencyFormatter.format(paymentItem.getAmount()) +
								"""
										</td>
										</tr>
										""")
						.collect(Collectors.joining()))
				.replace("{10}", CurrencyFormatter.format(
						payment.getPaymentItems().stream().mapToInt(paymentItem -> paymentItem.getAmount()).sum()));

		return formattedHtmlContent;
	}
}
