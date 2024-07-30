package com.practice.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.practice.dto.EmployeeRequestDTO;
import com.practice.dto.EmployeeResponseDTO;
import com.practice.model.Employee;

class EmployeeMapperTest {

	private EmployeeMapper employeeMapper;
	private EmployeeRequestDTO employeeRequestDTO;
	private Employee employee;

	@BeforeEach
	public void setUp() {
		this.employeeMapper = new EmployeeMapper();
		this.employeeRequestDTO = EmployeeRequestDTO.of("101", "Sahil", "IT", 3500, "Full-Time");
		this.employee = new Employee("101", "Sahil", "IT", 3500, "Full-Time");

	}

	@AfterEach
	public void cleanUp() {
		this.employeeMapper = null;
		this.employeeRequestDTO = null;
	}

	@Test
	public void shouldMapEmployeeRequestDTOToEmployee() {

		Employee employee = employeeMapper.toEmployee(employeeRequestDTO);
		assertNotNull(employee, "Employee should have created.");
		assertEquals(employeeRequestDTO.getCode(), employee.getCode(), "Code not matched!");
		assertEquals(employeeRequestDTO.getName(), employee.getName(), "Name not matched!");
		assertEquals(employeeRequestDTO.getDepartment(), employee.getDepartment(), "Department not matched!");
		assertEquals(employeeRequestDTO.getSalary(), employee.getSalary(), "Salary not matched!");
		assertEquals(employeeRequestDTO.getType(), employee.getType(), "Type not matched!");

	}

	@Test
	public void shouldMapEmployeeToEmployeeResponseDTO() {

		EmployeeResponseDTO employeeResponseDTO = employeeMapper.toResponseDTO(employee);
		assertNotNull(employeeResponseDTO, "Employee Request DTO should have created.");
		assertEquals(employee.getCode(), employeeResponseDTO.getCode(), "Code not matched!");
		assertEquals(employee.getName(), employeeResponseDTO.getName(), "Name not matched!");
		assertEquals(employee.getDepartment(), employeeResponseDTO.getDepartment(), "Department not matched!");
		assertEquals(employee.getSalary(), employeeResponseDTO.getSalary(), "Salary not matched!");
		assertEquals(employee.getType(), employeeResponseDTO.getType(), "Type not matched!");

	}

	@Test
	public void should_throw_null_pointer_exception_when_requestDTO_null() {

		var ex = assertThrows(NullPointerException.class, () -> employeeMapper.toEmployee(null));
		assertEquals("DTO should not be null!", ex.getMessage());
	}

	@Test
	public void should_throw_null_pointer_exception_when_employee_null() {

		var ex = assertThrows(NullPointerException.class, () -> employeeMapper.toResponseDTO(null));
		assertEquals("Employee should not be null!", ex.getMessage());
	}

}
