package ru.gb.Lesson8.page.pageController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.Lesson8.page.pageDto.ProjectPageDto;
import ru.gb.Lesson8.page.pageService.ProjectPageService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/home/projects")
@RequiredArgsConstructor
public class ProjectPageController {
    private final ProjectPageService service;

    @GetMapping
    public String getAllProjects(Model model) {
        List<ProjectPageDto> projects = service.findAll();
        model.addAttribute("projects", projects);
        return "projects-page.html";
    }

    @GetMapping("/{id}")
    public String getProjectPage(@PathVariable Long id, Model model) {
        Optional<ProjectPageDto> projectOpt = service.findById(id);
        if (projectOpt.isEmpty()) {
            throw new NoSuchElementException();
        }

        model.addAttribute("project", projectOpt.get());
        return "project-page.html";
    }

}
