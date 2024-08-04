package ru.gb.Lesson5.page.pageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.Lesson5.model.Employee;
import ru.gb.Lesson5.page.pageDto.EmployeePageDto;
import ru.gb.Lesson5.repository.TimesheetRepository;
import ru.gb.Lesson5.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeePageService {
    private final EmployeeService employeeService;
    private final TimesheetRepository timesheetRepository;

    public List<EmployeePageDto> findAll() {
        return employeeService.findAll().stream()
                .map(this::convert)
                .toList();
    }

    public Optional<EmployeePageDto> findById(Long id) {
        return employeeService.findById(id) // Optional<Timesheet>
                .map(this::convert);
    }

    public EmployeePageDto convert(Employee employee) {

        EmployeePageDto employeePageParameters = new EmployeePageDto();
        employeePageParameters.setId(String.valueOf(employee.getId()));
        employeePageParameters.setName(employee.getName());
        employeePageParameters.setTimesheets(timesheetRepository.findByEmployeeId(employee.getId()));
        return employeePageParameters;
    }
}
