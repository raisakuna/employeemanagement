package com.microservice.employeeapp.employeeRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.employeeapp.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
	

}
