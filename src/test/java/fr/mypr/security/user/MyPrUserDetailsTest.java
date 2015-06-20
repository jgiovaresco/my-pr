package fr.mypr.security.user;


import fr.mypr.user.model.Role;
import org.junit.Test;

import static fr.mypr.security.user.MyPrUserDetailsAssert.assertThat;

public class MyPrUserDetailsTest
{

	private static final Long ID = 1L;
	private static final String EMAIL = "john.smith@gmail.com";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Smith";
	private static final String PASSWORD = "password";

	@Test
	public void should_create_an_user_details()
	{
		MyPrUserDetails user = MyPrUserDetails.builder()
				.firstName(FIRST_NAME)
				.id(ID)
				.lastName(LAST_NAME)
				.password(PASSWORD)
				.role(Role.ROLE_USER)
				.username(EMAIL)
				.build();

		assertThat(user).hasFirstName(FIRST_NAME)
				.hasId(ID)
				.hasLastName(LAST_NAME)
				.hasPassword(PASSWORD)
				.hasUsername(EMAIL)
				.isActive()
				.isRegisteredUser();
	}
}