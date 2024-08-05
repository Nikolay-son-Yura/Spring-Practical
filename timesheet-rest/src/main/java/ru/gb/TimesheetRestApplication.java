package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ru.gb.model.*;
import ru.gb.repository.*;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@EnableDiscoveryClient
@SpringBootApplication
public class TimesheetRestApplication {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(TimesheetRestApplication.class, args);

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
            employee.setId((long) i);
            employee.setFirstName("Employee #" + i);
            employee.setLastName("LastName " + i);

            employeeRepo.save(employee);
        }


        RoleRepository roleRepository = ctx.getBean(RoleRepository.class);

        Role roleAdmin = new Role();
        Role roleUser = new Role();
        Role roleRest = new Role();

        roleAdmin.setName(Roles.ADMIN.getName());
        roleUser.setName(Roles.USER.getName());
        roleRest.setName(Roles.REST.getName());

        roleAdmin=roleRepository.save(roleAdmin);
        roleUser = roleRepository.save(roleUser);
        roleRest = roleRepository.save(roleRest);


        UserRepository userRepository = ctx.getBean(UserRepository.class);
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG"); // admin
        User user = new User();
        user.setLogin("user");
        user.setPassword("$2a$12$.dlnBAYq6sOUumn3jtG.AepxdSwGxJ8xA2iAPoCHSH61Vjl.JbIfq"); // user
        User rest =new User();
        rest.setLogin("rest");
        rest.setPassword("$2a$12$.rUjk6V1gmXSnq7siVf65O4QX3VWBbtH82OcDzxQoK4a.e53qSVDC");

        User anonymous = new User();
        anonymous.setLogin("anon");
        anonymous.setPassword("$2a$12$tPkyYzWCYUEePUFXUh3scesGuPum1fvFYwm/9UpmWNa52xEeUToRu"); // anon

        admin = userRepository.save(admin);
        user = userRepository.save(user);
        anonymous = userRepository.save(anonymous);

        UserRoleRepository userRoleRepository=ctx.getBean(UserRoleRepository.class);

        UserRole adminAdminRole = new UserRole();
        adminAdminRole.setUserId(admin.getId());
        adminAdminRole.setRoleId(roleAdmin.getId());
        userRoleRepository.save(adminAdminRole);

        UserRole adminUserRole = new UserRole();
        adminUserRole.setUserId(admin.getId());
        adminUserRole.setRoleId(roleUser.getId());
        userRoleRepository.save(adminUserRole);

        UserRole adminRestRole = new UserRole();
        adminRestRole.setUserId(admin.getId());
        adminRestRole.setRoleId(roleRest.getId());
        userRoleRepository.save(adminRestRole);

        TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);

        LocalDate createdAt = LocalDate.now();
        for (int i = 1; i <= 10; i++) {
            createdAt = createdAt.plusDays(1);

            Timesheet timesheet = new Timesheet();
            timesheet.setId((long) i);
            timesheet.setProjectId(ThreadLocalRandom.current().nextLong(1, 6));
            timesheet.setEmployeeId(ThreadLocalRandom.current().nextLong(1, 10));
            timesheet.setCreatedAt(createdAt);
            timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

            timesheetRepo.save(timesheet);
        }
    }
}
