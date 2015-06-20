package fr.mypr.user.model;

import org.assertj.core.api.*;

public class UserAccountAssert extends AbstractAssert<UserAccountAssert, UserAccount>
{
	private UserAccountAssert(UserAccount actual)
	{
		super(actual, UserAccountAssert.class);
	}

	public static UserAccountAssert assertThat(UserAccount actual)
	{
		return new UserAccountAssert(actual);
	}


	public UserAccountAssert hasEmail(String email)
	{
		isNotNull();

		Assertions.assertThat(actual.getEmail())
				.overridingErrorMessage("Expected email to be <%s> but was <%s>",
				                        email,
				                        actual.getEmail()
				)
				.isEqualTo(email);

		return this;
	}

	public UserAccountAssert hasFirstName(String firstName)
	{
		isNotNull();

		Assertions.assertThat(actual.getFirstName())
				.overridingErrorMessage("Expected first name to be <%s> but was <%s>",
				                        firstName,
				                        actual.getFirstName()
				)
				.isEqualTo(firstName);

		return this;
	}

	public UserAccountAssert hasLastName(String lastName)
	{
		isNotNull();

		Assertions.assertThat(actual.getLastName())
				.overridingErrorMessage("Expected last name to be <%s> but was <%s>",
				                        lastName,
				                        actual.getLastName()
				)
				.isEqualTo(lastName);

		return this;
	}

	public UserAccountAssert hasNoId()
	{
		isNotNull();

		Assertions.assertThat(actual.getId())
				.overridingErrorMessage("Expected id to be <null> but was <%d>",
				                        actual.getId()
				)
				.isNull();

		return this;
	}

	public UserAccountAssert hasPassword(String password)
	{
		isNotNull();

		Assertions.assertThat(actual.getPassword())
				.overridingErrorMessage("Expected password to be <%s> but was <%s>",
				                        password,
				                        actual.getPassword()
				)
				.isEqualTo(password);

		return this;
	}

	private UserAccountAssert hasNoPassword()
	{
		isNotNull();

		Assertions.assertThat(actual.getPassword())
				.overridingErrorMessage("Expected password to be <null> but was <%s>",
				                        actual.getPassword()
				)
				.isNull();
		return this;
	}

	public UserAccountAssert isRegisteredUser()
	{
		isNotNull();

		Assertions.assertThat(actual.getRole())
				.overridingErrorMessage("Expected role to be <ROLE_USER> but was <%s>",
				                        actual.getRole()
				)
				.isEqualTo(Role.ROLE_USER);

		return this;
	}
}
