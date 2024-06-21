package repository.amount;

import java.util.List;

import model.MonthAmount;

public interface AmountRepository {
	List<MonthAmount> getMonthAmounts();

	MonthAmount getMonthAmount(int id);

	MonthAmount getByLevelId(int levelId);

	void addMonthAmount(MonthAmount amount);

	void updateMonthAmount(MonthAmount amount);

	void deleteMonthAmount(int id);
}
