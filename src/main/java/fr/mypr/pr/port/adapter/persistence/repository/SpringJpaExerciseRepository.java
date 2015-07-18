package fr.mypr.pr.port.adapter.persistence.repository;

import fr.mypr.pr.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SpringJpaExerciseRepository implements ExerciseRepository
{
	private JpaExerciseRepository jpaExerciseRepository;

	@Autowired
	public SpringJpaExerciseRepository(JpaExerciseRepository jpaExerciseRepository)
	{
		this.jpaExerciseRepository = jpaExerciseRepository;
	}

	@Override
	public Exercise exerciseOfId(String anExerciseId)
	{
		return toExercise(jpaExerciseRepository.findOne(anExerciseId));
	}

	@Override
	public String nextIdentity()
	{
		return UUID.randomUUID().toString().toUpperCase();
	}

	@Override
	public void save(Exercise anExercise)
	{
		ExerciseJpa exerciseJpa = ExerciseJpa.builder()
				.id(anExercise.id())
				.name(anExercise.name())
				.unit(anExercise.unit())
				.build();

		jpaExerciseRepository.save(exerciseJpa);
	}

	private Exercise toExercise(ExerciseJpa exerciseJpa)
	{
		Exercise exercise = null;
		if (null != exerciseJpa)
		{
			exercise = Exercise.builder()
					.id(exerciseJpa.getId())
					.name(exerciseJpa.getName())
					.unit(exerciseJpa.getUnit())
					.build();
		}
		return exercise;
	}
}
