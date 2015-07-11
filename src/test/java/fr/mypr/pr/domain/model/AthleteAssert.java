package fr.mypr.pr.domain.model;

import org.assertj.core.api.*;

public class AthleteAssert extends AbstractAssert<AthleteAssert, Athlete>
{
	protected AthleteAssert(Athlete actual)
	{
		super(actual, AthleteAssert.class);
	}

	public static AthleteAssert assertThat(Athlete actual)
	{
		return new AthleteAssert(actual);
	}

	public AthleteAssert hasIdentity(String identity)
	{
		isNotNull();

		Assertions.assertThat(actual.identity())
				.overridingErrorMessage("Expected identity to be <%s> but was <%s>",
				                        identity,
				                        actual.identity()
				)
				.isEqualTo(identity);

		return this;
	}

	public AthleteAssert hasName(String name)
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
}
