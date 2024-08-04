package ru.gb.Lesson8.service;

import org.springframework.stereotype.Service;
import ru.gb.Lesson8.model.Employee;
import ru.gb.Lesson8.model.Timesheet;
import ru.gb.Lesson8.repository.EmployeeRepository;
import ru.gb.Lesson8.repository.TimesheetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TimesheetRepository timesheetRepository;

    public EmployeeService(EmployeeRepository employeeRepository, TimesheetRepository timesheetRepository) {
        this.employeeRepository = employeeRepository;
        this.timesheetRepository = timesheetRepository;
    }

    public Optional<Employee> findById(Long id){
        return employeeRepository.findById(id);
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Employee create(Employee employee){
        return employeeRepository.save(employee);
    }

    public void delete(Long id){
        employeeRepository.deleteById(id);
    }

    public List<Timesheet> findTimesheetsByEmployeeId(Long id){
        List<Timesheet> timesheetsByEmployeeId = new ArrayList<>();
        for (Timesheet timesheet : timesheetRepository.findAll()) {
            if(timesheet.getEmployeeId().equals(id)){
                timesheetsByEmployeeId.add(timesheet);
            }
        }
        return timesheetsByEmployeeId;
    }
}
