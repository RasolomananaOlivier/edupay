package model;

import util.PaymentPeriod;

public class PaymentItem {
	private int id;
	private String paymentId;
	private int amount;
	private PaymentPeriod period;

	public PaymentItem(int id, String paymentId, int amount, PaymentPeriod period) {
		super();
		this.id = id;
		this.paymentId = paymentId;
		this.amount = amount;
		this.period = period;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public PaymentPeriod getPeriod() {
		return period;
	}

	public void setPeriod(PaymentPeriod period) {
		this.period = period;
	}
}
