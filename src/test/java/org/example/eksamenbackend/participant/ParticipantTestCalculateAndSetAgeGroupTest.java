package org.example.eksamenbackend.participant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTestCalculateAndSetAgeGroupTest {

    @Test
    void calculateAndSetAgeGroup() {
        Participant participant = new Participant();

        participant.setAge(8);
        participant.calculateAndSetAgeGroup();
        assertEquals(AgeGroup.KIDS, participant.getAgeGroup());

        participant.setAge(12);
        participant.calculateAndSetAgeGroup();
        assertEquals(AgeGroup.YOUTH, participant.getAgeGroup());

        participant.setAge(20);
        participant.calculateAndSetAgeGroup();
        assertEquals(AgeGroup.JUNIOR, participant.getAgeGroup());

        participant.setAge(30);
        participant.calculateAndSetAgeGroup();
        assertEquals(AgeGroup.ADULT, participant.getAgeGroup());

        participant.setAge(50);
        participant.calculateAndSetAgeGroup();
        assertEquals(AgeGroup.SENIOR, participant.getAgeGroup());
    }

}
