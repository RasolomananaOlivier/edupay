package com.edupay.repository.academicSession;

import java.util.List;

import com.edupay.model.AcademicSession;

public interface AcademicSessionRepository {
	List<AcademicSession> getSessions();

	AcademicSession getLatest();

	AcademicSession getById(Integer id);
}
