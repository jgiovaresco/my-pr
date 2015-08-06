package fr.mypr.identityaccess.domain.model;

import org.assertj.core.api.*;

public class UserDescriptorAssert extends AbstractAssert<UserDescriptorAssert, UserDescriptor>
{

	private UserDescriptorAssert(UserDescriptor actual)
	{
		super(actual, UserDescriptorAssert.class);
	}

	public static UserDescriptorAssert assertThat(UserDescriptor actual)
	{
		return new UserDescriptorAssert(actual);
	}

	public UserDescriptorAssert isNullDescriptor()
	{
		isNotNull();

		Assertions.assertThat(actual.isNullDescriptor())
				.overridingErrorMessage("Expected null descriptor")
				.isTrue();

		return this;
	}

	public UserDescriptorAssert hasPassword(String password)
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

	public UserDescriptorAssert hasEmail(String email)
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
}
