package ru.gb.Lesson5.page.pageController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.Lesson5.page.pageDto.EmployeePageDto;
import ru.gb.Lesson5.page.pageService.EmployeePageService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/home/employees")
@RequiredArgsConstructor
public class EmployeePageController {
    private final EmployeePageService service;

    @GetMapping
    public String getAllEmployees(Model model) {
        List<EmployeePageDto> employeePageDtoList = service.findAll();
        model.addAttribute("projects", employeePageDtoList);
        return "employees-page.html";
    }


    @GetMapping("/{id}")
    public String getEmployeesPage(@PathVariable Long id, Model model) {
        Optional<EmployeePageDto> employeeOpt = service.findById(id);
        if (employeeOpt.isEmpty()) {
            throw new NoSuchElementException();
        }

        model.addAttribute("employee", employeeOpt.get());
        return "employee-page.html";
    }
}
