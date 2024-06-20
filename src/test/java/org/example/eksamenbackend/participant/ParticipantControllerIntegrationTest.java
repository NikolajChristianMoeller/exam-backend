package org.example.eksamenbackend.participant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

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
@ComponentScan(basePackageClasses = {ParticipantService.class})
public class ParticipantControllerIntegrationTest {
    @MockBean
    ParticipantRepository participantRepository;

    @Autowired
    WebTestClient webClient;

    private Participant mockTestClass;

    @BeforeEach
    public void setUp() {
        mockTestClass = new Participant();
        mockTestClass.setId(1L);
        mockTestClass.setName("Test Entity");
        mockTestClass.setGender("Test Gender");
        mockTestClass.setAge(25);
        mockTestClass.setClub("Test Club");

        when(participantRepository.save(any(Participant.class))).thenReturn(mockTestClass);
        when(participantRepository.findById(1L)).thenReturn(Optional.of(mockTestClass));
        when(participantRepository.findAll()).thenReturn(Collections.singletonList(mockTestClass));
        doNothing().when(participantRepository).deleteById(1L);
    }

    @Test
    void notNull() {
        assertThat(webClient).isNotNull();
    }

    @Test
    void createTestClass() {
        webClient
                .post().uri("/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "name":"Test Entity",
                        "gender":"Test Gender",
                        "age": 25,
                        "club":"Test Club"
                    }
                """)
                .exchange()
                .expectStatus().isCreated();

        verify(participantRepository, times(1)).save(any(Participant.class));
    }

    @Test
    void getTestClass() {
        webClient
                .get().uri("/participants/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Test Entity")
                .jsonPath("$.gender").isEqualTo("Test Gender")
                .jsonPath("$.age").isEqualTo(25)
                .jsonPath("$.club").isEqualTo("Test Club");

        verify(participantRepository, times(1)).findById(1L);
    }

    @Test
    void getAllTestClass() {
        webClient
                .get().uri("/participants")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Test Entity")
                .jsonPath("$.gender").isEqualTo("Test Gender")
                .jsonPath("$.age").isEqualTo(25)
                .jsonPath("$.club").isEqualTo("Test Club");

        verify(participantRepository, times(1)).findAll();
    }

    @Test
    void updateTestClass() {
        when(participantRepository.save(any(Participant.class))).thenReturn(mockTestClass);

        webClient
                .put().uri("/participants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "name":"Updated Entity",
                        "gender":"Updated Gender",
                        "age": 35,
                        "club":"Updated Club"
                    }
                """)
                .exchange()
                .expectStatus().isOk();

        verify(participantRepository, times(1)).save(any(Participant.class));
    }

    @Test
    void deleteTestClass() {
        webClient
                .delete().uri("/participants/1")
                .exchange()
                .expectStatus().isOk();

        verify(participantRepository, times(1)).deleteById(1L);
    }
}