package org.example.eksamenbackend.discipline;

import org.example.eksamenbackend.participant.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {DisciplineService.class})
public class DisciplineControllerIntegrationTest {

    @MockBean
    DisciplineRepository disciplineRepository;

    @Autowired
    WebTestClient webClient;

    private Discipline mockDiscipline;

    @BeforeEach
    void setUp() {
        mockDiscipline = new Discipline();
        mockDiscipline.setId(1L);
        mockDiscipline.setName("Discipline A");
        mockDiscipline.setDescription("Description A");
        mockDiscipline.setResultsType(ResultsType.TIME);

        Participant participant = new Participant();
        participant.setId(1L);
        mockDiscipline.setParticipants(List.of(participant));

        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(mockDiscipline));
        when(disciplineRepository.findAll()).thenReturn(List.of(mockDiscipline));
        when(disciplineRepository.save(any(Discipline.class))).thenReturn(mockDiscipline);

    }

    @Test
    void notNull() {
        assertThat(webClient).isNotNull();
    }


    @Test
    void getDisciplineFindAll() {
        webClient
                .get().uri("/disciplines")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo("Discipline A")
                .jsonPath("$[0].description").isEqualTo("Description A")
                .jsonPath("$[0].resultsType").isEqualTo("TIME")
                .jsonPath("$[0].participants[0].id").isEqualTo(1);

        verify(disciplineRepository, times(1)).findAll();
    }

    @Test
    void getDisciplineFindById() {
        webClient
                .get().uri("/disciplines/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Discipline A")
                .jsonPath("$.description").isEqualTo("Description A")
                .jsonPath("$.resultsType").isEqualTo("TIME")
                .jsonPath("$.participants[0].id").isEqualTo(1);

        verify(disciplineRepository, times(1)).findById(1L);
    }

    @Test
    void createDiscipline() {
        webClient
                .post().uri("/disciplines")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                   {
                      "name": "Discipline A",
                      "description": "Description A",
                      "resultsType": "TIME",
                      "participants": [
                             {
                                 "id": 1
                             },
                             {
                                 "id": 2
                             }
                       ]
                    }
                """)
                .exchange()
                .expectStatus().isCreated();

        verify(disciplineRepository, times(1)).save(any(Discipline.class));
    }


    @Test
    void updateDisciplineById() {
        when(disciplineRepository.save(any(Discipline.class))).thenReturn(mockDiscipline);

        webClient
                .put().uri("/disciplines/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                 {
                  "name": "Discipline A",
                  "description": "Description A",
                  "resultsType": "TIME",
                  "participants": [
                         {
                             "id": 1
                         },
                         {
                             "id": 2
                         }
                   ]
                }
            """)
                .exchange()
                .expectStatus().isOk();

        verify(disciplineRepository, times(1)).save(any(Discipline.class));
    }
}