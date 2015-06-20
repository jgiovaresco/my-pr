package fr.mypr.user.model;

import org.junit.Test;

import static fr.mypr.user.model.UserAccountAssert.assertThat;


public class UserAccountTest
{
	private static final String EMAIL = "john.smith@gmail.com";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Smith";
	private static final String PASSWORD = "password";

	@Test
	public void should_create_registered_user()
	{

		UserAccount user = UserAccount.builder()
				.email(EMAIL)
				.password(PASSWORD)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.build();

		assertThat(user)
				.hasNoId()
				.hasEmail(EMAIL)
				.hasPassword(PASSWORD)
				.hasFirstName(FIRST_NAME)
				.hasLastName(LAST_NAME)
				.isRegisteredUser()
		;
	}
}