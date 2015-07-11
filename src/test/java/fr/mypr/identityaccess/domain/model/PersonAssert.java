package fr.mypr.identityaccess.domain.model;

import org.assertj.core.api.*;

public class PersonAssert extends AbstractAssert<PersonAssert, Person>
{

	private PersonAssert(Person actual)
	{
		super(actual, PersonAssert.class);
	}

	public static PersonAssert assertThat(Person actual)
	{
		return new PersonAssert(actual);
	}


	public PersonAssert hasFirstname(String firstName)
	{
		isNotNull();

		Assertions.assertThat(actual.name().firstName())
				.overridingErrorMessage("Expected firstName to be <%s> but was <%s>",
				                        firstName,
				                        actual.name().firstName()
				)
				.isEqualTo(firstName);

		return this;
	}

	public PersonAssert hasLastname(String lastName)
	{
		isNotNull();

		Assertions.assertThat(actual.name().lastName())
				.overridingErrorMessage("Expected lastName to be <%s> but was <%s>",
				                        lastName,
				                        actual.name().lastName()
				)
				.isEqualTo(lastName);

		return this;
	}
}
