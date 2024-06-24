package com.edupay.repository.paymentItem;

import java.util.List;

import com.edupay.model.PaymentItem;

public interface PaymentItemRepository {
	void addMany(List<PaymentItem> paymentItems);

	List<PaymentItem> getBySessionIdAndStudentId(int sessionId, String studentId);

	void deleteMany(List<Integer> ids);
}
