package repository.academicSession;

import java.util.List;

import model.AcademicSession;

public interface AcademicSessionRepository {
	List<AcademicSession> getSessions();

	AcademicSession getLatest();

	AcademicSession getById(Integer id);
}
