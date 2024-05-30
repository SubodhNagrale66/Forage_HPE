package com.HPE.Forage.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HPE.Forage.Model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,String> {

}
