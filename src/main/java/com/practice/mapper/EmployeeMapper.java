package com.practice.mapper;

import org.springframework.stereotype.Component;

import com.practice.dto.EmployeeRequestDTO;
import com.practice.dto.EmployeeResponseDTO;
import com.practice.model.Employee;

@Component
public class EmployeeMapper {


	public Employee toEmployee(EmployeeRequestDTO dto) {
		if (dto == null) {
			throw new NullPointerException("DTO should not be null!");
		}
		return new Employee(dto.getCode(), dto.getName(), dto.getDepartment(), dto.getSalary(), dto.getType());

	}

	public EmployeeResponseDTO toResponseDTO(Employee employee) {
		if (employee == null) {
			throw new NullPointerException("Employee should not be null!");
		}
		
		return new EmployeeResponseDTO(employee.getCode(), employee.getName(), employee.getDepartment(),
				employee.getSalary(), employee.getType());

	}

}
