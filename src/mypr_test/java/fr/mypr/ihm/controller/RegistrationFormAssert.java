package fr.mypr.ihm.controller;

import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationFormAssert extends AbstractAssert<RegistrationFormAssert, RegistrationForm>
{

	private RegistrationFormAssert(RegistrationForm actual)
	{
		super(actual, RegistrationFormAssert.class);
	}

	public static RegistrationFormAssert assertThatRegistrationForm(RegistrationForm actual)
	{
		return new RegistrationFormAssert(actual);
	}

	public RegistrationFormAssert hasEmail(String email)
	{
		isNotNull();

		assertThat(actual.getEmail())
				.overridingErrorMessage("Expected email to be <%s> but was <%s>",
				                        email,
				                        actual.getEmail()
				)
				.isEqualTo(email);

		return this;
	}

	public RegistrationFormAssert hasFirstName(String firstName)
	{
		isNotNull();

		assertThat(actual.getFirstName())
				.overridingErrorMessage("Expected first name to be <%s> but was <%s>",
				                        firstName,
				                        actual.getFirstName()
				)
				.isEqualTo(firstName);

		return this;
	}

	public RegistrationFormAssert hasLastName(String lastName)
	{
		isNotNull();

		assertThat(actual.getLastName())
				.overridingErrorMessage("Expected last name to be <%s> but was <%s>",
				                        lastName,
				                        actual.getLastName())
				.isEqualTo(lastName);

		return this;
	}

	public RegistrationFormAssert hasNoPassword()
	{
		isNotNull();

		assertThat(actual.getPassword())
				.overridingErrorMessage("Expected password to be <null> but was <%s>",
				                        actual.getPassword()
				)
				.isNull();

		return this;
	}

	public RegistrationFormAssert hasNoConfirmPassword()
	{
		isNotNull();

		assertThat(actual.getConfirmPassword())
				.overridingErrorMessage("Expected confirm password to be <null> but was <%s>",
				                        actual.getConfirmPassword()
				)
				.isNull();

		return this;
	}

	public RegistrationFormAssert hasPassword(String password)
	{
		isNotNull();

		assertThat(actual.getPassword())
				.overridingErrorMessage("Expected password to be <%s> but was <%s>",
				                        password,
				                        actual.getPassword()
				)
				.isEqualTo(password);

		return this;
	}

	public RegistrationFormAssert hasConfirmPassword(String confirmPassword)
	{
		isNotNull();

		assertThat(actual.getConfirmPassword())
				.overridingErrorMessage("Expected confirm password to be <%s> but was <%s>",
				                        confirmPassword,
				                        actual.getConfirmPassword()
				)
				.isEqualTo(confirmPassword);

		return this;
	}
}
