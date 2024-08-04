package ru.gb.Lesson5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.Lesson5.model.Employee;
import ru.gb.Lesson5.model.Project;
import ru.gb.Lesson5.model.Timesheet;
import ru.gb.Lesson5.repository.EmployeeRepository;
import ru.gb.Lesson5.repository.ProjectRepository;
import ru.gb.Lesson5.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class TimesheetApplication {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(TimesheetApplication.class, args);

        ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);
        for (int i = 1; i <= 5; i++) {
            Project project = new Project();
            project.setId((long) i);
            project.setName("Project #" + i);
            projectRepo.save(project);
        }

        EmployeeRepository employeeRepo = ctx.getBean(EmployeeRepository.class);
        for (int i = 1; i <= 10; i++) {
            Employee employee = new Employee();
            employee.setName("Employee #" + i);

            employeeRepo.save(employee);
        }

        TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);

        LocalDate createdAt = LocalDate.now();
        for (int i = 1; i <= 10; i++) {
            createdAt = createdAt.plusDays(1);

            Timesheet timesheet = new Timesheet();
            timesheet.setId((long) i);
            timesheet.setCreatedAt(createdAt);
            timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

            timesheetRepo.save(timesheet);
        }
    }
}
