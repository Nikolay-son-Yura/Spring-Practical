package ru.gb.page.pageService;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.client.EmployeeResponse;
import ru.gb.client.ProjectResponse;
import ru.gb.client.TimesheetResponse;
import ru.gb.page.pageDto.ProjectPageDto;
import ru.gb.page.pageDto.TimesheetPageDto;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TimesheetPageService {

//    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;
    private ProjectPageService projectPageService;


    public TimesheetPageService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    //    public TimesheetPageService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
    private RestClient restClient() {
        List<ServiceInstance> instances = discoveryClient.getInstances("TIMESHEET-REST");
        int instancesCount = instances.size();
        int instanceIndex = ThreadLocalRandom.current().nextInt(0, instancesCount);

        ServiceInstance instance = instances.get(instanceIndex);
        String uri = "http://" + instance.getHost() + ":" + instance.getPort();
        System.out.println("URI = " + uri);
        return RestClient.create(uri);
    }

    private List<TimesheetResponse> requestList(String s) {
        return Objects.requireNonNull(restClient().get()
                .uri(s)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                }));
    }

    private TimesheetResponse request(String s) {
        return Objects.requireNonNull(restClient().get()
                .uri(s)
                .retrieve()
                .body(TimesheetResponse.class));
    }

    private TimesheetPageDto convertRequest(TimesheetResponse timesheetResponse) {
        TimesheetPageDto timesheetPageDto = new TimesheetPageDto();
        timesheetPageDto.setId(String.valueOf(timesheetResponse.getId()));
        timesheetPageDto.setMinutes(String.valueOf(timesheetResponse.getMinutes()));
        timesheetPageDto.setCreatedAt(timesheetResponse.getCreatedAt().format(DateTimeFormatter.ISO_DATE));
        ProjectResponse project = restClient().get()
                .uri("/projects/" + timesheetResponse.getProjectId())
                .retrieve()
                .body(ProjectResponse.class);
        timesheetPageDto.setProjectId(project.getName());
        EmployeeResponse employee = restClient().get()
                .uri("/employees/" + timesheetResponse.getEmployeeId())
                .retrieve()
                .body(EmployeeResponse.class);
        timesheetPageDto.setEmployeeId(employee.getFirstName());

        return timesheetPageDto;
    }

    public List<TimesheetPageDto> findAll() {
        List<TimesheetResponse> timesheets = null;
        try {
            timesheets = requestList("/timesheets");
        } catch (HttpServerErrorException ignored) {
        }
        if (timesheets == null) {
            throw new RuntimeException("oops");
        }
        List<TimesheetPageDto> result = new ArrayList<>();
        for (TimesheetResponse timesheet : timesheets) {
            result.add(convertRequest(timesheet));
        }
        return result;
    }

    public Optional<TimesheetPageDto> проверка(Long id) {
        try {
            TimesheetResponse timesheet = request("/timesheets/" + id);
            return Optional.of(convertRequest(timesheet));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    public Optional<ProjectPageDto> findByProjectId(Long id) {
//       return projectPageService.findById(id);

        try {
            ProjectResponse project = restClient().get()
                    .uri("/projects/" + id)
                    .retrieve()
                    .body(ProjectResponse.class);;
            ProjectPageDto projectPageDto = new ProjectPageDto();
            projectPageDto.setId(String.valueOf(project.getId()));
            projectPageDto.setName(project.getName());
            return Optional.of(projectPageDto);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }

    }
}



