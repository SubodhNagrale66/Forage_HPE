package com.HPE.Forage.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HPE.Forage.Model.Employee;
import com.HPE.Forage.Repository.EmployeeRepository;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/Employees")
    public ResponseEntity<List<Employee>> getAllEmp()

    {
        List<Employee> empList = new ArrayList<>();
        employeeRepository.findAll().forEach(empList::add);
        return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);

    }

    @GetMapping("/Employees/{emp_id}")
    public ResponseEntity<?> EmployeeById(@PathVariable String emp_id) {
        Optional<Employee> eOptional = employeeRepository.findById(emp_id);
        if (eOptional.isPresent()) {
            return new ResponseEntity<Employee>(eOptional.get(), HttpStatus.OK);
        } else
            return new ResponseEntity<>("Employee Not Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/Employees")
    public ResponseEntity<?> createNewEmployee(@RequestBody Employee employee) {
        // TODO: process POST request
        employeeRepository.save(employee);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @PutMapping("/Update/{emp_id}")
    public ResponseEntity<?> UpdateEmployee(@PathVariable String emp_id, @RequestBody Employee employee) {
        Optional<Employee> emp = employeeRepository.findById(emp_id);
        if (emp.isPresent()) {
            Employee existingEmployee = emp.get();
            existingEmployee.setEmployee_id(emp_id);
            existingEmployee.setFirst_name(employee.getFirst_name());
            existingEmployee.setLast_name(employee.getLast_name());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setTitle(employee.getTitle());
            employeeRepository.save(existingEmployee);

            return new ResponseEntity<Employee>(emp.get(), HttpStatus.OK);
        } else
            return new ResponseEntity<>("Employee Not Found / Updated", HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/DeleteId/{emp_id}")
    public ResponseEntity<?> DeleteById(@PathVariable String emp_id) {
        Optional<Employee> DeleteRequestedEmp = employeeRepository.findById(emp_id);
        if (DeleteRequestedEmp.isPresent()) {
            employeeRepository.deleteById(emp_id);
            return new ResponseEntity<Employee>(DeleteRequestedEmp.get(), HttpStatus.OK);

        } else
            return new ResponseEntity<>("Entry Not Found / Deleted"+emp_id, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/DeleteAll")
    public ResponseEntity<?> DeleteAll() {
        employeeRepository.deleteAll();
        return new ResponseEntity<>("All Entries Deleted", HttpStatus.OK);
    }

    

}
