package com.HPE.Forage;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
// import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.HPE.Forage.Controller.EmployeeController;
import com.HPE.Forage.Model.Employee;
import com.HPE.Forage.Repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EmployeeRepository employeeRepository;

    Employee Test_emp1 = new Employee("Major", "Right", "H1006", "MajorRight@HPE.com", "Test");
    Employee Test_emp2 = new Employee("Test", "2", "H1007", "Test2@HPE.com", "Test");
    Employee Test_emp3 = new Employee("Test", "3", "H1008", "Test3@HPE.com", "Test");

    // Start Get Map Testing
    @Test
    public void getAllRecords_Success() throws Exception {
        List<Employee> empList = new ArrayList<Employee>(Arrays.asList(Test_emp1, Test_emp2, Test_emp3));
        Mockito.when(employeeRepository.findAll()).thenReturn(empList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/Employees")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].first_name", is("Test")));

    }
    // End GET Map Testing

    // Start GetById Testing
    @Test
    public void getEmpById_Success() throws Exception {
        Mockito.when(employeeRepository.findById(Test_emp1.getEmployee_id()))
                .thenReturn(java.util.Optional.of(Test_emp1));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/Employees/H1006")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.first_name", is("Major")));

    }
    // End GetById Testing

    // Post Map Testing
    @Test
    public void PostNewEmployee_Success() throws Exception {

        Employee Testemp = Employee.builder()
                .first_name("Test10")
                .last_name("Pro")
                .email("Test10Pro@HPE.com")
                .employee_id("H1007")
                .title("Test")
                .build();

        Mockito.when(employeeRepository.save(Testemp)).thenReturn(Testemp);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/Employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(Testemp));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.first_name", is("Test10")));

    }
    // End Post Map Testing

    // PUT Testing

    @Test
    public void PutMappingTesting_Success() throws Exception {
        Employee UpdatedEmployee = Employee.builder()
                .first_name("Major")
                .last_name("Stern")
                .email("MajorStern@HPE.com")
                .employee_id("H1006")
                .title("Test Analyst")
                .build();

        Mockito.when(employeeRepository.findById(Test_emp1.getEmployee_id())).thenReturn(Optional.of(Test_emp1));
        Mockito.when(employeeRepository.save(UpdatedEmployee)).thenReturn(UpdatedEmployee);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/api/Update/H1006")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(UpdatedEmployee));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.last_name", is("Stern")));
    }
    // End PUT Testing

    // DELETE MAPPING TEST

    @Test
    public void DeleteMappingByID_Test() throws Exception {

        Mockito.when(employeeRepository.findById(Test_emp1.getEmployee_id())).thenReturn(Optional.of(Test_emp1));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/DeleteId/H1006")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.first_name", is("Major")))
                .andExpect(status().isOk());
    }

    // END DELETE MAPPING TEST

    // Delete Mapping Not Found Test
    
    @Test
    public void deleteById_NotFound() throws Exception {
        String nonExistentEmpId = "H1006_25";
        Mockito.when(employeeRepository.findById(nonExistentEmpId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/DeleteId/" + nonExistentEmpId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entry Not Found / Deleted" + nonExistentEmpId)));
    }

 
    // END Delete Mapping Not Found Test

}
