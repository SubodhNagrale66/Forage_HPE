// package com.HPE.Forage;

// import org.junit.Before;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.runner.RunWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.mockito.junit.MockitoJUnitRunner;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// import com.HPE.Forage.Controller.EmployeeController;
// import com.HPE.Forage.Model.Employee;
// import com.HPE.Forage.Repository.EmployeeRepository;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.ObjectWriter;

// @RunWith(MockitoJUnitRunner.class)
// public class EmployeeControllerTest {

//     private MockMvc mockMvc;
//     ObjectMapper objectMapper = new ObjectMapper();
//     ObjectWriter objectWriter = objectMapper.writer();

//     @Mock
//     private EmployeeRepository employeeRepository;

//     @InjectMocks
//     private EmployeeController employeeController;

//     Employee Test_emp1 = new Employee("Major", "Right", "H1006", "MajorRight@HPE.com", "Test");
//     Employee Test_emp2 = new Employee("Test", "2", "H1007", "Test2@HPE.com", "Test");
//     Employee Test_emp3 = new Employee("Test", "3", "H1008", "Test3@HPE.com", "Test");

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.initMocks(this);
//         this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//     }
// }
