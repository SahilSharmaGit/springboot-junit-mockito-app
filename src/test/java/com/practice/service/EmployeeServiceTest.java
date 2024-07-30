package com.practice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.practice.dto.EmployeeResponseDTO;
import com.practice.exception.NoEmployeesFoundException;
import com.practice.mapper.EmployeeMapper;
import com.practice.model.Employee;
import com.practice.repository.EmployeeRepository;

public class EmployeeServiceTest {

	private Employee employee;
	private EmployeeResponseDTO employeeResponseDTO;

	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private EmployeeMapper employeeMapper;

	@BeforeEach
	public void setUp() {
		this.employee = new Employee("101", "Sahil", "IT", 3500, "Full-Time");
		this.employeeResponseDTO = new EmployeeResponseDTO("101", "Sahil", "IT", 3500, "Full-Time");
		MockitoAnnotations.openMocks(this);

	}

	@AfterEach
	public void cleanUp() {
		this.employee = null;
		this.employeeResponseDTO = null;
	}
	
	@Test
	public void should_successfully_save_employee() {
		Employee savedEmployee = new Employee("101", "Sahil", "IT", 3500, "Full-Time");

		when(employeeRepository.save(employee)).thenReturn(savedEmployee);
		when(employeeMapper.toResponseDTO(savedEmployee)).thenReturn(employeeResponseDTO);
		EmployeeResponseDTO responseDTO = employeeService.saveEmployee(employee);

		assertNotNull(responseDTO, "Response should not be null!");
		assertEquals(savedEmployee.getCode(), responseDTO.getCode(), "Code not matched!");
		assertEquals(savedEmployee.getName(), responseDTO.getName(), "Name not matched!");
		assertEquals(savedEmployee.getDepartment(), responseDTO.getDepartment(), "Department not matched!");
		assertEquals(savedEmployee.getSalary(), responseDTO.getSalary(), "Salary not matched!");
		assertEquals(savedEmployee.getType(), responseDTO.getType(), "Type not matched!");

		// verifying that, methods should be called once
		verify(employeeMapper, times(1)).toResponseDTO(savedEmployee);
		verify(employeeRepository, times(1)).save(employee);

	}

	@Test
	public void shouldReturnEmployeeByCode() {
		final String employeeCode = "101";
		when(employeeRepository.findById(employeeCode)).thenReturn(Optional.ofNullable(employee));
		when(employeeMapper.toResponseDTO(employee)).thenReturn(employeeResponseDTO);
		EmployeeResponseDTO response = employeeService.getEmployee(employeeCode);
		assertNotNull(response, "Response should not be null!");
		assertEquals(employee.getCode(), response.getCode(), "Code not matched!");
		assertEquals(employee.getName(), response.getName(), "Name not matched!");
		assertEquals(employee.getDepartment(), response.getDepartment(), "Department not matched!");
		assertEquals(employee.getSalary(), response.getSalary(), "Salary not matched!");
		assertEquals(employee.getType(), response.getType(), "Type not matched!");

		verify(employeeRepository, times(1)).findById(employeeCode);

	}

	@Test
	public void shouldThrowWhenEmployeeByCodeNull() {
		final String employeeCode = "code doesn't exists";
		when(employeeRepository.findById(employeeCode)).thenReturn(Optional.empty());
		var ex = assertThrows(NoEmployeesFoundException.class, () -> employeeService.getEmployee(employeeCode));
		assertEquals("Employee not found", ex.getMessage());

	}

	@Test
	public void shouldReturnEmployeeList() {

		List<Employee> emps = List.of(employee);
		when(employeeRepository.findAll()).thenReturn(emps);
		when(employeeMapper.toResponseDTO(any(Employee.class))).thenReturn(employeeResponseDTO);
		List<EmployeeResponseDTO> list = employeeService.getAllEmployee();

		assertEquals(emps.size(), list.size());
		verify(employeeRepository, times(1)).findAll();

	}

	@Test
	public void shouldThrowWhenEmployeeListEmpty() {
		when(employeeRepository.findAll()).thenReturn(List.of());

		var ex = assertThrows(NoEmployeesFoundException.class, () -> employeeService.getAllEmployee());
		assertEquals("There's no employees in the db", ex.getMessage());

	}

	@Test
	public void shouldDeleteEmployee() {
		String employeeCode = "EMP123";

		// Call the method under test
		employeeService.deleteEmployee(employeeCode);

		// Verify that repository.deleteById() was called with the correct parameter
		verify(employeeRepository, times(1)).deleteById(employeeCode);
	}

}
