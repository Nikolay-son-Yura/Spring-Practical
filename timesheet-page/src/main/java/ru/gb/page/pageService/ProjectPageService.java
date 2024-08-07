package ru.gb.page.pageService;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.client.EmployeeResponse;

import ru.gb.client.ProjectResponse;
import ru.gb.client.TimesheetResponse;
import ru.gb.page.pageDto.ProjectPageDto;
import ru.gb.page.pageDto.TimesheetPageDto;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service

public class ProjectPageService {


    private final DiscoveryClient discoveryClient;

    public ProjectPageService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    private RestClient restClient() {
        List<ServiceInstance> instances = discoveryClient.getInstances("TIMESHEET-REST");
        int instancesCount = instances.size();
        int instanceIndex = ThreadLocalRandom.current().nextInt(0, instancesCount);

        ServiceInstance instance = instances.get(instanceIndex);
        String uri = "http://" + instance.getHost() + ":" + instance.getPort();
        System.out.println("URI = " + uri);
        return RestClient.create(uri);
    }

    private List<ProjectResponse> requestList(String s) {
        return Objects.requireNonNull(restClient().get()
                .uri(s)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                }));
    }

    private ProjectResponse request(String s) {
        return Objects.requireNonNull(restClient().get()
                .uri(s)
                .retrieve()
                .body(ProjectResponse.class));
    }

    private ProjectPageDto convertRequest(ProjectResponse projectResponse) {
        ProjectPageDto projectPageDto = new ProjectPageDto();
        projectPageDto.setId(String.valueOf(projectResponse.getId()));
        projectPageDto.setName(projectResponse.getName());
        return projectPageDto;
    }

    public List<ProjectPageDto> findAll() {
        List<ProjectResponse> projectsResponses = null;
        try {
            projectsResponses = requestList("/projects");
        } catch (HttpServerErrorException ignored) {
        }
        if (projectsResponses == null) {
            throw new RuntimeException("oops");
        }
        List<ProjectPageDto> result = new ArrayList<>();
        for (ProjectResponse project : projectsResponses) {
            result.add(convertRequest(project));
        }
        return result;
    }

    public Optional<ProjectPageDto> findById(Long id) {

        try {
            ProjectResponse projectResponse = request("/projects/" + id);

            return Optional.of(convertRequest(projectResponse));
        } catch (HttpServerErrorException ignored) {
            return Optional.empty();
        }
    }

}
