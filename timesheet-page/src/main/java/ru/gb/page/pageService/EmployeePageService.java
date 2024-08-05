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
    public List<EmployeePageDto> findAll() {
        List<EmployeeResponse> employeeResponses = null;
        try {
            employeeResponses=restClient().get()
                    .uri("/employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<EmployeeResponse>>(){});
        }catch (HttpServerErrorException ignored){

        }
        if (employeeResponses == null) {
            throw new RuntimeException("oops");
        }
        List<EmployeePageDto> result= new ArrayList<>();
        for (EmployeeResponse employee : employeeResponses) {
            EmployeePageDto employeePageDto =new EmployeePageDto();
            employeePageDto.setId(String.valueOf(employee.getId()));
            employeePageDto.setFirstName(employee.getFirstName());
            result.add(employeePageDto);
        }
        return result;
    }

    public Optional<EmployeePageDto> findById(Long id) {
        try {
            EmployeeResponse employeeResponses = restClient().get()
                    .uri("/employees" + id)
                    .retrieve()
                    .body(EmployeeResponse.class);
            EmployeePageDto employeePageDto = new EmployeePageDto();
            employeePageDto.setId(String.valueOf(employeeResponses.getId()));
            employeePageDto.setFirstName(employeeResponses.getFirstName());
            return Optional.of(employeePageDto);
        } catch (HttpServerErrorException ignored) {
            return Optional.empty();
        }
    }
}
