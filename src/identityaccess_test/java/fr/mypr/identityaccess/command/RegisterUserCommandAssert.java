package fr.mypr.identityaccess.command;

import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterUserCommandAssert extends AbstractAssert<RegisterUserCommandAssert, RegisterUserCommand>
{

	private RegisterUserCommandAssert(RegisterUserCommand actual)
	{
		super(actual, RegisterUserCommandAssert.class);
	}

	public static RegisterUserCommandAssert assertThatRegisterUserCommand(RegisterUserCommand actual)
	{
		return new RegisterUserCommandAssert(actual);
	}

	public RegisterUserCommandAssert hasEmail(String email)
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

	public RegisterUserCommandAssert hasFirstName(String firstName)
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

	public RegisterUserCommandAssert hasLastName(String lastName)
	{
		isNotNull();

		assertThat(actual.getLastName())
				.overridingErrorMessage("Expected last name to be <%s> but was <%s>",
				                        lastName,
				                        actual.getLastName())
				.isEqualTo(lastName);

		return this;
	}

	public RegisterUserCommandAssert hasNoPassword()
	{
		isNotNull();

		assertThat(actual.getPassword())
				.overridingErrorMessage("Expected password to be <null> but was <%s>",
				                        actual.getPassword()
				)
				.isNull();

		return this;
	}

	public RegisterUserCommandAssert hasPassword(String password)
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
}
