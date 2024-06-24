package com.edupay.repository.payment;

import java.util.List;

import com.edupay.model.Payment;

public interface PaymentRepository {
	void addOne(Payment payment);

	Payment getById(String id);

	List<Payment> getMany();

	void updateOne(Payment payment);

	void deleteOne(String id);
}
