package fr.mypr.pr.port.adapter.persistence.repository;

import org.assertj.core.api.*;

import java.time.LocalDate;

public class PersonalRecordJpaAssert extends AbstractAssert<PersonalRecordJpaAssert, PersonalRecordJpa>
{
	protected PersonalRecordJpaAssert(PersonalRecordJpa actual)
	{
		super(actual, PersonalRecordJpaAssert.class);
	}

	public static PersonalRecordJpaAssert assertThat(PersonalRecordJpa actual)
	{
		return new PersonalRecordJpaAssert(actual);
	}

	public PersonalRecordJpaAssert hasId(String id)
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

	public PersonalRecordJpaAssert hasDate(LocalDate date)
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

	public PersonalRecordJpaAssert hasValue(Float value)
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

	public PersonalRecordJpaAssert hasAthleteId(String athleteId)
	{
		isNotNull();

		Assertions.assertThat(actual.getAthleteId())
				.overridingErrorMessage("Expected athleteId to be <%s> but was <%s>",
				                        athleteId,
				                        actual.getAthleteId()
				)
				.isEqualTo(athleteId);

		return this;
	}

	public PersonalRecordJpaAssert hasAthleteName(String athleteName)
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

	public PersonalRecordJpaAssert hasExerciseId(String exerciseId)
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

	public PersonalRecordJpaAssert hasExerciseName(String exerciseName)
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

	public PersonalRecordJpaAssert hasExerciseUnit(String exerciseUnit)
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