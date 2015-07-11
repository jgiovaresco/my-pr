package fr.mypr.ihm.security.util;

import fr.mypr.identityaccess.domain.model.*;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtilTest
{

	private static final String EMAIL = "foo@bar.com";
	private static final String FIRST_NAME = "Foo";
	private static final String LAST_NAME = "Bar";
	private static final String PASSWORD = "password";

	@Test
	public void logInUser_should_add_user_details_to_SecurityContext()
	{
		User user = User.builder()
				.email(EMAIL)
				.password(PASSWORD)
				.person(Person.builder().name(new FullName(FIRST_NAME, LAST_NAME)).build())
				.build();

		SecurityUtil.logInUser(user);

		SecurityContextAssert.assertThat(SecurityContextHolder.getContext())
				.loggedInUserIs(user)
				.loggedInUserHasPassword(PASSWORD);
	}

}
