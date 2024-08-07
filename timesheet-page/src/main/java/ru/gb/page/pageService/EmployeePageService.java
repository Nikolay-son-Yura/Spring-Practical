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
import ru.gb.page.pageDto.EmployeePageDto;
import ru.gb.page.pageDto.TimesheetPageDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service

public class EmployeePageService {

    private final DiscoveryClient discoveryClient;

    public EmployeePageService(DiscoveryClient discoveryClient) {
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
    private List<EmployeeResponse> requestList(String s) {
        return Objects.requireNonNull(restClient().get()
                .uri(s)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                }));
    }

    private EmployeeResponse request(String s) {
        return Objects.requireNonNull(restClient().get()
                .uri(s)
                .retrieve()
                .body(EmployeeResponse.class));
    }
    private EmployeePageDto convertRequest(EmployeeResponse employeeResponse) {
        EmployeePageDto employeePageDto = new EmployeePageDto();
        employeePageDto.setId(String.valueOf(employeeResponse.getId()));
        employeePageDto.setFirstName(employeeResponse.getFirstName());
        return employeePageDto;
    }

    public List<EmployeePageDto> findAll() {
        List<EmployeeResponse> employeeResponses = null;
        try {
            employeeResponses = requestList("/employees");
        } catch (HttpServerErrorException ignored) {
        }
        if (employeeResponses == null) {
            throw new RuntimeException("oops");
        }
        List<EmployeePageDto> result = new ArrayList<>();
        for (EmployeeResponse employee : employeeResponses) {

            result.add(convertRequest(employee));
        }
        return result;
    }

    public Optional<EmployeePageDto> findById(Long id) {
        try {
            EmployeeResponse employeeResponses = request("/employees/" + id);
            return Optional.of(convertRequest(employeeResponses));
        } catch (HttpServerErrorException ignored) {
            return Optional.empty();
        }
    }
}
