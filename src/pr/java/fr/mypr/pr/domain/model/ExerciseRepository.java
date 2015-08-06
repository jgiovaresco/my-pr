package fr.mypr.pr.domain.model;


public interface ExerciseRepository
{
	Exercise exerciseOfId(String anExerciseId);

	String nextIdentity();

	void save(Exercise anExercise);
}
