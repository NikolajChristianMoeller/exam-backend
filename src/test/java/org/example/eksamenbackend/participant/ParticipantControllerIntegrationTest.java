package org.example.eksamenbackend.participant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.example.eksamenbackend.discipline.Discipline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ComponentScan(basePackageClasses = {ParticipantService.class})
public class ParticipantControllerIntegrationTest {

    @MockBean
    ParticipantRepository participantRepository;

    @Autowired
    WebTestClient webClient;

    private Participant mockParticipant;

    @BeforeEach
    void setUp() {
        mockParticipant = new Participant();
        mockParticipant.setFullName("Nikolaj Christian Møller");
        mockParticipant.setAge(33);
        mockParticipant.setGender(Gender.MALE);
        mockParticipant.setAdjacentClub("Club Name");
        mockParticipant.setCountry(Country.DENMARK);

        Discipline discipline = new Discipline();
        discipline.setId(1L);
        mockParticipant.setDisciplines(List.of(discipline));

        Mockito.when(participantRepository.findById(1L)).thenReturn(Optional.of(mockParticipant));
        Mockito.when(participantRepository.findAll()).thenReturn(List.of(mockParticipant));
        Mockito.when(participantRepository.save(any(Participant.class))).thenReturn(mockParticipant);
    }

    @Test
    void notNull() {
        assertThat(webClient).isNotNull();
    }

    @Test
    void getParticipantsFindAll() {
        webClient
                .get().uri("/participants")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].fullName").isEqualTo("Nikolaj Christian Møller")
                .jsonPath("$[0].age").isEqualTo(33)
                .jsonPath("$[0].gender").isEqualTo("MALE")
                .jsonPath("$[0].adjacentClub").isEqualTo("Club Name")
                .jsonPath("$[0].country").isEqualTo("DENMARK")
                .jsonPath("$[0].disciplines[0].id").isEqualTo(1);

        verify(participantRepository, times(1)).findAll();
    }

    @Test
    void getParticipantsFindById() {
        webClient
                .get().uri("/participants/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.fullName").isEqualTo("Nikolaj Christian Møller")
                .jsonPath("$.age").isEqualTo(33)
                .jsonPath("$.gender").isEqualTo("MALE")
                .jsonPath("$.adjacentClub").isEqualTo("Club Name")
                .jsonPath("$.country").isEqualTo("DENMARK")
                .jsonPath("$.disciplines[0].id").isEqualTo(1);

        verify(participantRepository, times(1)).findById(1L);
    }

    @Test
    void createParticipants() {
        webClient
                .post().uri("/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                       "fullName": "Nikolaj Christian Møller",
                       "age": 33,
                       "gender": "MALE",
                       "adjacentClub": "Club Name",
                       "country": "DENMARK",
                       "disciplines": [
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

        verify(participantRepository, times(1)).save(any(Participant.class));
    }

    @Test
    void updateParticipantById() {
        when(participantRepository.save(any(Participant.class))).thenReturn(mockParticipant);

        webClient
                .put().uri("/participants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                       "fullName": "Nikolaj Christian Møller",
                       "age": 33,
                       "gender": "MALE",
                       "adjacentClub": "Club Name",
                       "country": "DENMARK",
                       "disciplines": [
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

        verify(participantRepository, times(1)).save(any(Participant.class));
    }

    @Test
    void deleteParticipantById() {
        webClient
                .delete().uri("/participants/1")
                .exchange()
                .expectStatus().isOk();

        verify(participantRepository, times(1)).deleteById(1L);
    }

}
