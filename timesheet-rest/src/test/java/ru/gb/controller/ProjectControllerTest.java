package ru.gb.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.model.Project;
import ru.gb.repository.ProjectRepository;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectControllerTest {

    @Autowired
    ProjectRepository projectRepository;

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    void getByIdNotFound() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restClient.get()
                    .uri("/projects/-2")
                    .retrieve()
                    .toBodilessEntity();
        });
    }

    @Test
    void getByIdAllOk() {
        // given
        Project project = new Project();
        project.setName("projectName");
        Project expected = projectRepository.save(project);
        // GET /projects/{id}
        ResponseEntity<Project> actual = restClient.get()
                .uri("/projects/" + expected.getId())
                .retrieve()
                .toEntity(Project.class);

        // assert 200 OK
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        Project responseBody = actual.getBody();
        assertNotNull(responseBody);
        assertEquals(project.getId(), responseBody.getId());
        assertEquals(project.getName(), responseBody.getName());
    }

    @Test
    void testCreate() {

        Project toCreate=new Project();
        toCreate.setName("NewName");

        ResponseEntity<Project> response=restClient.post()
                .uri("/projects")
                .body(toCreate)
                .retrieve()
                .toEntity(Project.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Project responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(responseBody.getName(), toCreate.getName());
        assertTrue(projectRepository.existsById(responseBody.getId()));
    }

    @Test
    void testDeleteById() {
        Project toDelete = new Project();
        toDelete.setName("NewName");
        toDelete = projectRepository.save(toDelete);

        ResponseEntity<Void> response = restClient.delete()
                .uri("/projects/" + toDelete.getId())
                .retrieve()
                .toBodilessEntity(); // less
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Проверяем, что запись в БД НЕТ
        assertFalse(projectRepository.existsById(toDelete.getId()));
    }

}