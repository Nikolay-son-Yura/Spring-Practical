package ru.gb.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;
import ru.gb.model.Employee;
import ru.gb.model.Project;
import ru.gb.model.Timesheet;
import ru.gb.repository.EmployeeRepository;
import ru.gb.repository.ProjectRepository;
import ru.gb.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimesheetControllerTest {
    @Autowired
    TimesheetRepository timesheetRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    void findByIdTimesheets() {
        Project project = new Project();
        project.setName("projectName");
        Employee employee = new Employee();
        employee.setFirstName("FirstName");
        employee.setLastName("LastName");

        Timesheet timesheet = new Timesheet();
        timesheet.setCreatedAt(LocalDate.now());
        timesheet.setMinutes(10);
        timesheet.setProjectId(project.getId());
        timesheet.setEmployeeId(employee.getId());

        Timesheet expected = timesheetRepository.save(timesheet);

        ResponseEntity<Timesheet> actual = restClient.get()
                .uri("/timesheets/" + expected.getId())
                .retrieve()
                .toEntity(Timesheet.class);

        Timesheet responseBody = actual.getBody();

        assertNotNull(responseBody);
        assertEquals(timesheet.getId(), responseBody.getId());
        assertEquals(timesheet.getCreatedAt(), responseBody.getCreatedAt());
        assertEquals(timesheet.getMinutes(), responseBody.getMinutes());
        assertEquals(timesheet.getProjectId(), responseBody.getProjectId());
        assertEquals(timesheet.getEmployeeId(), responseBody.getEmployeeId());
    }

    @Test
    void findAllTimesheets() {
        Timesheet timesheet = new Timesheet();
        timesheet.setCreatedAt(LocalDate.now());
        timesheet.setMinutes(10);
        timesheet.setProjectId(1L);
        timesheet.setEmployeeId(1L);

        ResponseEntity<List<Timesheet>> actual = restClient.get()
                .uri("/timesheets")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());

    }

    @Test
    void createTimesheets() {
        Project project = new Project();
        project.setName("projectName");
        Employee employee = new Employee();
        employee.setFirstName("FirstName");
        employee.setLastName("LastName");

        Project project1=projectRepository.save(project);

        Employee employee1 =employeeRepository.save(employee);

        Timesheet createdTimesheet = new Timesheet();
        createdTimesheet.setCreatedAt(LocalDate.now());
        createdTimesheet.setMinutes(10);
        createdTimesheet.setProjectId(project1.getId());
        createdTimesheet.setEmployeeId(employee1.getId());

        ResponseEntity<Timesheet> responseEntity = restClient.post()
                .uri("/timesheets")
                .body(createdTimesheet)
                .retrieve()
                .toEntity(Timesheet.class);

        Timesheet responseBody =responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(timesheetRepository.existsById(responseBody.getId()));

    }
    @Test
    void deleteTimesheets(){
        Timesheet timesheet = new Timesheet();
        timesheet.setCreatedAt(LocalDate.now());
        timesheet.setMinutes(10);
        timesheet.setProjectId(1L);
        timesheet.setEmployeeId(1L);
        timesheet=timesheetRepository.save(timesheet);
        ResponseEntity<Void> response=restClient.delete()
                .uri("/timesheets/"+timesheet.getId())
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertFalse(timesheetRepository.existsById(timesheet.getId()));

    }


}
