package com.practice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.EmployeeRequestDTO;
import com.practice.dto.EmployeeResponseDTO;
import com.practice.exception.ValidationFailureException;
import com.practice.mapper.EmployeeMapper;
import com.practice.model.Employee;
import com.practice.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

	private final EmployeeService service;
	private final EmployeeMapper employeeMapper;

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome!";
	}

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO requestDTO) {
		log.info("createEmployee");

		validateEmployee(requestDTO);
		Employee employee = employeeMapper.toEmployee(requestDTO);
		return new ResponseEntity<>(service.saveEmployee(employee), HttpStatusCode.valueOf(201));
	}

	private void validateEmployee(EmployeeRequestDTO employee) {

		if (employee.getCode().isBlank()) {
			throw new ValidationFailureException("Code cannot be empty");
		}
		if (employee.getName().isBlank()) {
			throw new ValidationFailureException("Name cannot be empty");
		}

		if (employee.getDepartment().isBlank()) {
			throw new ValidationFailureException("Department cannot be empty");
		}

		if (employee.getType().isBlank()) {
			throw new ValidationFailureException("Type cannot be empty");
		}

		if (employee.getSalary() <= 0.0) {
			throw new ValidationFailureException("Salary must be positive");
		}

	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public ResponseEntity<EmployeeResponseDTO> getEmployee(@RequestParam("employeeCode") String employeeCode) {
		log.info("getEmployee");

		if (employeeCode == null || employeeCode.isBlank()) {
			throw new IllegalArgumentException("Code cannot be empty");
		}

		return ResponseEntity.ok(service.getEmployee(employeeCode));
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/all")
	public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployee() {
		log.info("getAllEmployee");

		return ResponseEntity.ok(service.getAllEmployee());
	}
}
