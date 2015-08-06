package fr.mypr.pr.domain.model;

import org.assertj.core.api.*;

public class ExerciseAssert extends AbstractAssert<ExerciseAssert, Exercise>
{
	protected ExerciseAssert(Exercise actual)
	{
		super(actual, ExerciseAssert.class);
	}

	public static ExerciseAssert assertThat(Exercise actual)
	{
		return new ExerciseAssert(actual);
	}

	public ExerciseAssert hasId(String id)
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

	public ExerciseAssert hasName(String name)
	{
		isNotNull();

		Assertions.assertThat(actual.name())
				.overridingErrorMessage("Expected name to be <%s> but was <%s>",
				                        name,
				                        actual.name()
				)
				.isEqualTo(name);

		return this;
	}

	public ExerciseAssert hasUnit(String unit)
	{
		isNotNull();

		Assertions.assertThat(actual.unit())
				.overridingErrorMessage("Expected unit to be <%s> but was <%s>",
				                        unit,
				                        actual.unit()
				)
				.isEqualTo(unit);

		return this;
	}
}