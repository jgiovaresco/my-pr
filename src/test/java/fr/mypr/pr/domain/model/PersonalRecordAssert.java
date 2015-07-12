package fr.mypr.pr.domain.model;

import org.assertj.core.api.*;

import java.time.LocalDate;

public class PersonalRecordAssert extends AbstractAssert<PersonalRecordAssert, PersonalRecord>
{
	protected PersonalRecordAssert(PersonalRecord actual)
	{
		super(actual, PersonalRecordAssert.class);
	}

	public static PersonalRecordAssert assertThat(PersonalRecord actual)
	{
		return new PersonalRecordAssert(actual);
	}

	public PersonalRecordAssert hasId(String id)
	{
		isNotNull();

		Assertions.assertThat(actual.id())
				.overridingErrorMessage("Expected id to be <%s> but was <%s>",
				                        id,
				                        actual.id()
				)
				.isEqualTo(id);

		return this;
	}

	public PersonalRecordAssert hasDate(LocalDate date)
	{
		isNotNull();

		Assertions.assertThat(actual.date())
				.overridingErrorMessage("Expected date to be <%s> but was <%s>",
				                        date,
				                        actual.date()
				)
				.isEqualTo(date);

		return this;
	}

	public PersonalRecordAssert hasValue(Float value)
	{
		isNotNull();

		Assertions.assertThat(actual.value())
				.overridingErrorMessage("Expected value to be <%s> but was <%s>",
				                        value,
				                        actual.value()
				)
				.isEqualTo(value);

		return this;
	}

	public PersonalRecordAssert hasAthlete(Athlete athlete)
	{
		isNotNull();

		Assertions.assertThat(actual.athlete())
				.overridingErrorMessage("Expected athlete to be <%s> but was <%s>",
				                        athlete,
				                        actual.athlete()
				)
				.isEqualTo(athlete);

		return this;
	}

	public PersonalRecordAssert hasExercise(Exercise exercise)
	{
		isNotNull();

		Assertions.assertThat(actual.exercise())
				.overridingErrorMessage("Expected exercise to be <%s> but was <%s>",
				                        exercise,
				                        actual.exercise()
				)
				.isEqualTo(exercise);

		return this;
	}
}