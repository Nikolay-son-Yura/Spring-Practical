package ru.gb.page.pageService;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.client.EmployeeResponse;

import ru.gb.client.ProjectResponse;
import ru.gb.page.pageDto.ProjectPageDto;
import ru.gb.page.pageDto.TimesheetPageDto;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service

public class ProjectPageService {


//    private final DiscoveryClient discoveryClient;
//
//    public ProjectPageService(DiscoveryClient discoveryClient) {
//        this.discoveryClient = discoveryClient;
//    }
//    private RestClient restClient() {
//        List<ServiceInstance> instances = discoveryClient.getInstances("TIMESHEET-REST");
//        int instancesCount = instances.size();
//        int instanceIndex = ThreadLocalRandom.current().nextInt(0, instancesCount);
//
//        ServiceInstance instance = instances.get(instanceIndex);
//        String uri = "http://" + instance.getHost() + ":" + instance.getPort();
//        System.out.println("URI = " + uri);
//        return RestClient.create(uri);
//    }
//    public List<ProjectPageDto> findAll() {
//        List<ProjectResponse> project = null;
//        try {
//            project = restClient().get()
//                    .uri("/projects")
//                    .retrieve()
//                    .body(new ParameterizedTypeReference<List<ProjectResponse>>() {
//                    });
//
//        } catch (HttpServerErrorException e) {
//            // ignore
//        }
//
//        if (project == null) {
//            throw new RuntimeException("oops");
//        }
//        List<ProjectPageDto> result = new ArrayList<>();
//
//        return
//    }
//
//    public Optional<ProjectPageDto> findById(Long id) {
//        return
//    }

}
