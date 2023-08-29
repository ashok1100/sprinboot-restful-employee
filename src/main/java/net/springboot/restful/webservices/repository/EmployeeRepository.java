package net.springboot.restful.webservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.springboot.restful.webservices.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	public Optional<Employee> findByEmail(String email);

}
