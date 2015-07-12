package fr.mypr.pr.port.adapter.persistence.repository;

import org.assertj.core.api.*;

public class ExerciseJpaAssert extends AbstractAssert<ExerciseJpaAssert, ExerciseJpa>
{
	protected ExerciseJpaAssert(ExerciseJpa actual)
	{
		super(actual, ExerciseJpaAssert.class);
	}

	public static ExerciseJpaAssert assertThat(ExerciseJpa actual)
	{
		return new ExerciseJpaAssert(actual);
	}

	public ExerciseJpaAssert hasId(String id)
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

	public ExerciseJpaAssert hasName(String name)
	{
		isNotNull();

		Assertions.assertThat(actual.getName())
				.overridingErrorMessage("Expected name to be <%s> but was <%s>",
				                        name,
				                        actual.getName()
				)
				.isEqualTo(name);

		return this;
	}

	public ExerciseJpaAssert hasUnit(String unit)
	{
		isNotNull();

		Assertions.assertThat(actual.getUnit())
				.overridingErrorMessage("Expected unit to be <%s> but was <%s>",
				                        unit,
				                        actual.getUnit()
				)
				.isEqualTo(unit);

		return this;
	}
}