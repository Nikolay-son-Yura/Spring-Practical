package ru.gb.Lesson6.page.pageController;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.gb.Lesson6.page.pageDto.EmployeePageDto;
import ru.gb.Lesson6.page.pageService.EmployeePageService;
import java.util.NoSuchElementException;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home/employees")
@RequiredArgsConstructor
public class EmployeePageController {
    private final EmployeePageService service;

    @GetMapping
    public String getAllEmployees(Model model){
        List<EmployeePageDto> employees = service.findAll();
//        model.addA
        model.addAttribute("employees", employees);
        return "employees-page.html";
    }


    @GetMapping("/{id}")
    public String getEmployeePage(@PathVariable Long id, Model model){
        Optional<EmployeePageDto> employeeOpt = service.findById(id);
        if(employeeOpt.isEmpty()){
            throw new NoSuchElementException();
        }
        model.addAttribute("employee", employeeOpt.get());
        return "employee-page.html";
    }
}
