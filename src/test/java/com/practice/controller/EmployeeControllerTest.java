package com.practice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.dto.EmployeeRequestDTO;
import com.practice.dto.EmployeeResponseDTO;
import com.practice.exception.ValidationFailureException;
import com.practice.mapper.EmployeeMapper;
import com.practice.model.Employee;
import com.practice.service.EmployeeService;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	private EmployeeController employeeController;

	@Mock
	private EmployeeMapper employeeMapper;

	@Mock
	private EmployeeService employeeService;
	private Employee employee;
	private EmployeeResponseDTO employeeResponseDTO;

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
	public void shouldWelcome() throws Exception {

		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
				.get("/api/v1/employee/welcome");
		ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);

	}

	@Test
	public void shouldCreateEmployee() throws Exception {

		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employeeResponseDTO);
		ObjectMapper mapper = new ObjectMapper();
		String employeeJSON = mapper.writeValueAsString(employee);

		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.post("/api/v1/employee")
				.contentType(MediaType.APPLICATION_JSON).content(employeeJSON);
		ResultActions perform = mockMvc.perform(reqBuilder);
		MvcResult andReturn = perform.andReturn();
		MockHttpServletResponse response = andReturn.getResponse();
		int status = response.getStatus();
		assertEquals(201, status);

	}

	@Test
	public void shouldThrowExceptionWhenCodeIsEmpty() throws Exception {

		EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("", "Sahil", "IT", 3500, "Full-Time");

		var ex = assertThrows(ValidationFailureException.class, () -> {
			try {
				ResponseEntity<EmployeeResponseDTO> response = employeeController.createEmployee(requestDTO);
				// This line will not be reached if the exception is thrown
				assertEquals(400, response.getStatusCode());

				fail("Expected ValidationFailureException but got ResponseEntity");
			} catch (ValidationFailureException e) {
				// Handle the expected exception
				throw e;
			}
		});

		// You can add assertions on the exception here, if needed
//		assertEquals("Code cannot be empty", exception.getMessage());
//		EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("", "Sahil", "IT", 3500, "Full-Time");
//
//		var ex = assertThrows(ValidationFailureException.class, () -> {
//			employeeController.createEmployee(requestDTO);
//		});
//
		assertEquals("Code cannot be empty", ex.getMessage());

	}

	@Test
	public void shouldThrowExceptionWhenNameIsEmpty() throws Exception {

		EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("101", "", "IT", 3500, "Full-Time");

		var exception = assertThrows(ValidationFailureException.class, () -> {
			employeeController.createEmployee(requestDTO);
		});

		assertEquals("Name cannot be empty", exception.getMessage());

	}

	@Test
	public void shouldThrowExceptionWhenDepartmentIsEmpty() throws Exception {

		EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("101", "Sahil", "", 3500, "Full-Time");

		var exception = assertThrows(ValidationFailureException.class, () -> {
			employeeController.createEmployee(requestDTO);
		});

		assertEquals("Department cannot be empty", exception.getMessage());

	}

	@Test
	public void shouldThrowExceptionWhenTypeIsEmpty() throws Exception {

		EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("101", "Sahil", "IT", 3500, "");

		var exception = assertThrows(ValidationFailureException.class, () -> {
			employeeController.createEmployee(requestDTO);
		});

		assertEquals("Type cannot be empty", exception.getMessage());

	}

	@Test
	public void shouldThrowExceptionWhenSalaryIsLessThanEqZero() throws Exception {

		EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("101", "Sahil", "IT", -230, "Full-Time");

		var exception = assertThrows(ValidationFailureException.class, () -> {
			employeeController.createEmployee(requestDTO);
		});

		assertEquals("Salary must be positive", exception.getMessage());

	}

	@Test
	public void shouldGetEmployeeByCode() throws Exception {

		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/api/v1/employee")
				.param("employeeCode", "101");
		ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);

	}

	@Test
	public void shouldThrowWhenEmployeeCodeParamBlank() throws Exception {

		var ex = assertThrows(IllegalArgumentException.class, () -> {
			employeeController.getEmployee(null);
		});

		assertEquals("Code cannot be empty", ex.getMessage());
	}

	@Test
	public void shouldGetEmployees() throws Exception {

		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/api/v1/employee/all");
		ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder);
		MvcResult result = perform.andReturn();
		MockHttpServletResponse response = result.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);

	}

}
