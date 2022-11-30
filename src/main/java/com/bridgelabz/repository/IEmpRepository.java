package com.bridgelabz.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.model.Model;

@Repository
public interface IEmpRepository extends JpaRepository<Model, Integer> {

	Optional<Model> findById(int employeeId);

	Optional<Model> findByemployeeName(String employeeName);

	Optional<Model> findByEmployeeName(String name);

	void deleteByEmployeeName(String employeeName);

	
	Optional<Model> findByEmployeeId(int employeeId);

	Optional<Model> findByEmailAndPassword(String email, String password);

	Optional<Model> findByEmail(String email);

	Optional<Model> findByPassword(String password);

	
	
}
