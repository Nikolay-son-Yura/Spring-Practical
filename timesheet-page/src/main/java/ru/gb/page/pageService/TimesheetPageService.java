package ru.gb.page.pageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.gb.client.EmployeeResponse;
import ru.gb.client.ProjectResponse;
import ru.gb.client.TimesheetResponse;
import ru.gb.page.pageDto.TimesheetPageDto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TimesheetPageService {

//    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;

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

    public List<TimesheetPageDto> findAll() {
        List<TimesheetResponse> timesheets = null;
        try {
            timesheets = restClient().get()
                    .uri("/timesheets")
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<TimesheetResponse>>() {
                    });

        } catch (HttpServerErrorException ignored) {

        }

        if (timesheets == null) {
            throw new RuntimeException("oops");
        }

        List<TimesheetPageDto> result = new ArrayList<>();
        for (TimesheetResponse timesheet : timesheets) {
            TimesheetPageDto timesheetPageDto = new TimesheetPageDto();
            timesheetPageDto.setId(String.valueOf(timesheet.getId()));
            timesheetPageDto.setMinutes(String.valueOf(timesheet.getMinutes()));
            timesheetPageDto.setCreatedAt(timesheet.getCreatedAt().format(DateTimeFormatter.ISO_DATE));
            ProjectResponse project = restClient().get()
                    .uri("/projects/" + timesheet.getProjectId())
                    .retrieve()
                    .body(ProjectResponse.class);
            timesheetPageDto.setProjectId(project.getName());
            EmployeeResponse employee = restClient().get()
                    .uri("/employee/" + timesheet.getEmployeeId())
                    .retrieve()
                    .body(EmployeeResponse.class);
            timesheetPageDto.setEmployeeId(employee.getFirstName());

            result.add(timesheetPageDto);
        }

        return result;
    }


    public Optional<TimesheetPageDto> findById(Long id) {
        try {
            TimesheetResponse timesheet = restClient().get()
                    .uri("/timesheets/" + id)
                    .retrieve()
                    .body(TimesheetResponse.class);

            TimesheetPageDto timesheetPageDto = new TimesheetPageDto();
            timesheetPageDto.setId(String.valueOf(timesheet.getId()));
            timesheetPageDto.setMinutes(String.valueOf(timesheet.getMinutes()));
            timesheetPageDto.setCreatedAt(timesheet.getCreatedAt().format(DateTimeFormatter.ISO_DATE));

            ProjectResponse project = restClient().get()
                    .uri("/projects/" + timesheet.getProjectId())
                    .retrieve()
                    .body(ProjectResponse.class);
            timesheetPageDto.setProjectId(project.getName());
            EmployeeResponse employee = restClient().get()
                    .uri("/employee/" + timesheet.getEmployeeId())
                    .retrieve()
                    .body(EmployeeResponse.class);
            timesheetPageDto.setEmployeeId(employee.getFirstName());
            return Optional.of(timesheetPageDto);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
}
