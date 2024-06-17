package repository.amount;

import java.util.List;

import model.MonthAmount;

public interface AmountRepository {
	List<MonthAmount> getMonthAmounts();

	MonthAmount getMonthAmount(int id);

	void addLMonthAmount(MonthAmount amount);

	void updateMonthAmount(MonthAmount amount);

	void deleteMonthAmount(int id);
}
