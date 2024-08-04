package ru.gb.Lesson8.page.pageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.Lesson8.model.Employee;
import ru.gb.Lesson8.service.EmployeeService;
import ru.gb.Lesson8.page.pageDto.EmployeePageDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeePageService {
    private final EmployeeService employeeService;

    public List<EmployeePageDto> findAll() {
        return employeeService.findAll().stream()
                .map(this::convert)
                .toList();
    }

    public Optional<EmployeePageDto> findById(Long id) {
        return employeeService.findById(id)
                .map(this::convert);
    }

    private EmployeePageDto convert(Employee employee) {

        EmployeePageDto employeePageParameters = new EmployeePageDto();
        employeePageParameters.setId(String.valueOf(employee.getId()));
        employeePageParameters.setFirstName(employee.getFirstName());
        employeePageParameters.setLastName(employee.getLastName());
        employeePageParameters.setTimesheetList(employeeService.findTimesheetsByEmployeeId(employee.getId()));

        return employeePageParameters;
    }
}
