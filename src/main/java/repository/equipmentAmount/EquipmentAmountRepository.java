package repository.equipmentAmount;

import java.util.List;

import model.EquipmentAmount;

public interface EquipmentAmountRepository {
	List<EquipmentAmount> getMonthAmounts();

	EquipmentAmount getMonthAmount(int id);

	EquipmentAmount getByLevelId(int levelId);

	void addMonthAmount(EquipmentAmount amount);

	void updateMonthAmount(EquipmentAmount amount);

	void deleteMonthAmount(int id);
}
