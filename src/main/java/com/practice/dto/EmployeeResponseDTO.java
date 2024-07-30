package com.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
	private String code;
	private String name;
	private String department;
	private double salary;
	private String type;

	public static EmployeeResponseDTO of(String code, String name, String department, double salary, String type) {
		return new EmployeeResponseDTO(code, name, department, salary, type);
	}
}
