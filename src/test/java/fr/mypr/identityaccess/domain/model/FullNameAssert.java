package fr.mypr.identityaccess.domain.model;

import org.assertj.core.api.*;

public class FullNameAssert extends AbstractAssert<FullNameAssert, FullName>
{

	private FullNameAssert(FullName actual)
	{
		super(actual, FullNameAssert.class);
	}

	public static FullNameAssert assertThat(FullName actual)
	{
		return new FullNameAssert(actual);
	}


	public FullNameAssert hasFirstname(String firstName)
	{
		isNotNull();

		Assertions.assertThat(actual.firstName())
				.overridingErrorMessage("Expected firstName to be <%s> but was <%s>",
				                        firstName,
				                        actual.firstName()
				)
				.isEqualTo(firstName);

		return this;
	}

	public FullNameAssert hasLastname(String lastName)
	{
		isNotNull();

		Assertions.assertThat(actual.lastName())
				.overridingErrorMessage("Expected lastName to be <%s> but was <%s>",
				                        lastName,
				                        actual.lastName()
				)
				.isEqualTo(lastName);

		return this;
	}
}
