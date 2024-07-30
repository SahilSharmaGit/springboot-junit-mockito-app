package com.practice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	@Id
	private String code;
	private String name;
	private String department;
	private double salary;
	private String type;

}
