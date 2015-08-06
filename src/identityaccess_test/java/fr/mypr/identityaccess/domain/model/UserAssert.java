package fr.mypr.identityaccess.domain.model;

import org.assertj.core.api.*;

public class UserAssert extends AbstractAssert<UserAssert, User>
{

	private UserAssert(User actual)
	{
		super(actual, UserAssert.class);
	}

	public static UserAssert assertThat(User actual)
	{
		return new UserAssert(actual);
	}


	public UserAssert hasPassword(String password)
	{
		isNotNull();

		Assertions.assertThat(actual.password())
				.overridingErrorMessage("Expected password to be <%s> but was <%s>",
				                        password,
				                        actual.password()
				)
				.isEqualTo(password);

		return this;
	}

	public UserAssert hasEmail(String email)
	{
		isNotNull();

		Assertions.assertThat(actual.email())
				.overridingErrorMessage("Expected email to be <%s> but was <%s>",
				                        email,
				                        actual.email()
				)
				.isEqualTo(email);

		return this;
	}

	public PersonAssert assertPerson()
	{
		return PersonAssert.assertThat(actual.person());
	}
}
