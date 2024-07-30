package com.practice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.practice.model.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

}
