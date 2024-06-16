package repository.payment;

import java.util.List;

import model.Payment;

public interface PaymentRepository {
	void addOne(Payment payment);

	Payment getById(int id);

	List<Payment> getMany();

	void updateOne(Payment payment);

	void deleteOne(int id);
}
