package fr.mypr.pr.application.data;

import org.assertj.core.api.*;

import java.time.LocalDate;

public class AthleteExercisePersonalRecordDataAssert
		extends AbstractAssert<AthleteExercisePersonalRecordDataAssert, AthleteExercisePersonalRecordData>
{
	protected AthleteExercisePersonalRecordDataAssert(AthleteExercisePersonalRecordData actual)
	{
		super(actual, AthleteExercisePersonalRecordDataAssert.class);
	}

	public static AthleteExercisePersonalRecordDataAssert assertThat(AthleteExercisePersonalRecordData actual)
	{
		return new AthleteExercisePersonalRecordDataAssert(actual);
	}

	public AthleteExercisePersonalRecordDataAssert hasId(String id)
	{
		isNotNull();

		Assertions.assertThat(actual.getId())
				.overridingErrorMessage("Expected id to be <%s> but was <%s>",
				                        id,
				                        actual.getId()
				)
				.isEqualTo(id);

		return this;
	}

	public AthleteExercisePersonalRecordDataAssert hasDate(LocalDate date)
	{
		isNotNull();

		Assertions.assertThat(actual.getDate())
				.overridingErrorMessage("Expected date to be <%s> but was <%s>",
				                        date,
				                        actual.getDate()
				)
				.isEqualTo(date);

		return this;
	}

	public AthleteExercisePersonalRecordDataAssert hasValue(Float value)
	{
		isNotNull();

		Assertions.assertThat(actual.getValue())
				.overridingErrorMessage("Expected value to be <%s> but was <%s>",
				                        value,
				                        actual.getValue()
				)
				.isEqualTo(value);

		return this;
	}

	public AthleteExercisePersonalRecordDataAssert hasAthleteId(String athleteId)
	{
		isNotNull();

		Assertions.assertThat(actual.getAthleteIdentity())
				.overridingErrorMessage("Expected athleteId to be <%s> but was <%s>",
				                        athleteId,
				                        actual.getAthleteIdentity()
				)
				.isEqualTo(athleteId);

		return this;
	}

	public AthleteExercisePersonalRecordDataAssert hasAthleteName(String athleteName)
	{
		isNotNull();

		Assertions.assertThat(actual.getAthleteName())
				.overridingErrorMessage("Expected athleteName to be <%s> but was <%s>",
				                        athleteName,
				                        actual.getAthleteName()
				)
				.isEqualTo(athleteName);

		return this;
	}

	public AthleteExercisePersonalRecordDataAssert hasExerciseId(String exerciseId)
	{
		isNotNull();

		Assertions.assertThat(actual.getExerciseId())
				.overridingErrorMessage("Expected exerciseId to be <%s> but was <%s>",
				                        exerciseId,
				                        actual.getExerciseId()
				)
				.isEqualTo(exerciseId);

		return this;
	}

	public AthleteExercisePersonalRecordDataAssert hasExerciseName(String exerciseName)
	{
		isNotNull();

		Assertions.assertThat(actual.getExerciseName())
				.overridingErrorMessage("Expected exerciseName to be <%s> but was <%s>",
				                        exerciseName,
				                        actual.getExerciseName()
				)
				.isEqualTo(exerciseName);

		return this;
	}

	public AthleteExercisePersonalRecordDataAssert hasExerciseUnit(String exerciseUnit)
	{
		isNotNull();

		Assertions.assertThat(actual.getExerciseUnit())
				.overridingErrorMessage("Expected exerciseUnit to be <%s> but was <%s>",
				                        exerciseUnit,
				                        actual.getExerciseUnit()
				)
				.isEqualTo(exerciseUnit);

		return this;
	}
}