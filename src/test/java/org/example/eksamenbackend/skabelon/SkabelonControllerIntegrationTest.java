package org.example.eksamenbackend.skabelon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {SkabelonService.class})
public class SkabelonControllerIntegrationTest {
    @MockBean
    SkabelonRepository skabelonRepository;

    @Autowired
    WebTestClient webClient;

    private Skabelon mockTestClass;

    @BeforeEach
    public void setUp() {
        mockTestClass = new Skabelon();
        mockTestClass.setId(1L);
        mockTestClass.setName("Test Entity");
        mockTestClass.setAge(25);

        when(skabelonRepository.save(any(Skabelon.class))).thenReturn(mockTestClass);
        when(skabelonRepository.findById(1L)).thenReturn(Optional.of(mockTestClass));
        when(skabelonRepository.findAll()).thenReturn(Collections.singletonList(mockTestClass));
        doNothing().when(skabelonRepository).deleteById(1L);
    }

    @Test
    void notNull() {
        assertThat(webClient).isNotNull();
    }

    @Test
    void createTestClass() {
        webClient
                .post().uri("/skabelons")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "name":"Test Entity",
                        "age": 25
                    }
                """)
                .exchange()
                .expectStatus().isCreated();

        verify(skabelonRepository, times(1)).save(any(Skabelon.class));
    }

    @Test
    void getTestClass() {
        webClient
                .get().uri("/skabelons/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Test Entity")
                .jsonPath("$.age").isEqualTo(25);

        verify(skabelonRepository, times(1)).findById(1L);
    }

    @Test
    void getAllTestClass() {
        webClient
                .get().uri("/skabelons")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo("Test Entity")
                .jsonPath("$[0].age").isEqualTo(25);

        verify(skabelonRepository, times(1)).findAll();
    }

    @Test
    void updateTestClass() {
        when(skabelonRepository.save(any(Skabelon.class))).thenReturn(mockTestClass);

        webClient
                .put().uri("/skabelons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "name":"Updated Entity",
                        "age": 35
                    }
                """)
                .exchange()
                .expectStatus().isOk();

        verify(skabelonRepository, times(1)).save(any(Skabelon.class));
    }

    @Test
    void deleteTestClass() {
        webClient
                .delete().uri("/skabelons/1")
                .exchange()
                .expectStatus().isOk();

        verify(skabelonRepository, times(1)).deleteById(1L);
    }
}