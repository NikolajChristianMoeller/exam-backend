package org.example.eksamenbackend.result;

import org.example.eksamenbackend.discipline.Discipline;
import org.example.eksamenbackend.discipline.ResultsType;
import org.example.eksamenbackend.participant.AgeGroup;
import org.example.eksamenbackend.participant.Gender;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {ResultService.class})
public class ResultControllerIntegrationTest {

    @MockBean
    ResultRepository resultRepository;

    @Autowired
    WebTestClient webClient;

    Result mockResult;

    @BeforeEach
    void setUp() {
        mockResult = new Result();

        Discipline mockDiscipline = new Discipline();
        mockDiscipline.setName("Discipline A");
        mockDiscipline.setResultsType(ResultsType.TIME);

        Participant mockParticipant = new Participant();
        mockParticipant.setAgeGroup(AgeGroup.ADULT);
        mockParticipant.setGender(Gender.MALE);
        mockParticipant.setAdjacentClub("Club A");

        mockResult.setDiscipline(mockDiscipline);
        mockResult.setParticipant(mockParticipant);
        mockResult.setResultValue("0:10:0.0");

        when(resultRepository.findById(1L)).thenReturn(java.util.Optional.of(mockResult));
        when(resultRepository.findAll()).thenReturn(java.util.List.of(mockResult));
        when(resultRepository.save(any(Result.class))).thenReturn(mockResult);
    }

    @Test
    void notNull() {
        assertThat(webClient).isNotNull();
    }

    @Test
    void getResultFindAll() {
        webClient
                .get().uri("/results")
                .exchange()
                .expectStatus().isOk()
                .expectBody();
        verify(resultRepository, times(1)).findAll();
    }

    @Test
    void getResultFindById() {
        webClient
                .get().uri("/results/1")
                .exchange()
                .expectStatus().isOk();
        verify(resultRepository, times(1)).findById(1L);
    }

    @Test
    void createResult() {
        webClient
                .post().uri("/results")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                   {
                         "participantId": 1,
                         "disciplineId": 1,
                         "resultDate": "2023-05-05",
                         "hours": 1,
                         "minutes": 30,
                         "seconds": 15,
                         "hundredths": 50,
                         "meters": 100,
                         "centimeters": 50,
                         "points": 10
                     }
                """)
                .exchange()
                .expectStatus().isCreated();

        verify(resultRepository, times(1)).save(any(Result.class));
    }

    @Test
    void updateDisciplineById() {
        when(resultRepository.save(any(Result.class))).thenReturn(mockResult);

        webClient
                .put().uri("/results/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                {
                     "participantId": 2,
                     "disciplineId": 1,
                     "resultDate": "2023-05-05",
                     "hours": 1,
                     "minutes": 30,
                     "seconds": 15,
                     "hundredths": 50,
                     "meters": 100,
                     "centimeters": 50,
                     "points": 10
                 }
            """)
                .exchange()
                .expectStatus().isOk();

        verify(resultRepository, times(1)).save(any(Result.class));
    }

}
