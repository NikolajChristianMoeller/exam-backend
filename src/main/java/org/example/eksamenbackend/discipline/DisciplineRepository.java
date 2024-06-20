package org.example.eksamenbackend.discipline;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    Discipline findByName(String name);
}
