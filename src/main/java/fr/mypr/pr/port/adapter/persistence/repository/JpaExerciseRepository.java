package fr.mypr.pr.port.adapter.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaExerciseRepository extends JpaRepository<ExerciseJpa, String>
{
}
