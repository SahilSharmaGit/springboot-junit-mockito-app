package com.practice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.practice.dto.EmployeeResponseDTO;
import com.practice.exception.NoEmployeesFoundException;
import com.practice.mapper.EmployeeMapper;
import com.practice.model.Employee;
import com.practice.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository repository;
	private final EmployeeMapper employeeMapper;

	public EmployeeResponseDTO saveEmployee(Employee employee) {

		Employee savedEmployee = repository.save(employee);
		// trying to call second time
		// repository.save(employee);

		return employeeMapper.toResponseDTO(savedEmployee);

	}

	public EmployeeResponseDTO getEmployee(String code) {
		Optional<Employee> optional = repository.findById(code);
		if (optional.isPresent()) {
			return employeeMapper.toResponseDTO(optional.get());

		}
		throw new NoEmployeesFoundException("Employee not found");

	}

	public List<EmployeeResponseDTO> getAllEmployee() {

		List<EmployeeResponseDTO> list = repository.findAll().stream().map(e -> employeeMapper.toResponseDTO(e))
				.toList();

		if (list.isEmpty()) {
			throw new NoEmployeesFoundException("There's no employees in the db");
		}
		return list;

	}

	public void deleteEmployee(String code) {

		repository.deleteById(code);

	}

}
