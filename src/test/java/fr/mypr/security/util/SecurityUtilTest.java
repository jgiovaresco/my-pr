package fr.mypr.security.util;

import fr.mypr.user.model.UserAccount;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtilTest
{

	private static final String EMAIL = "foo@bar.com";
	private static final String FIRST_NAME = "Foo";
	private static final Long ID = 1L;
	private static final String LAST_NAME = "Bar";
	private static final String PASSWORD = "password";

	@Test
	public void logInUser_should_add_user_details_to_SecurityContext()
	{
		UserAccount user = UserAccount.builder()
				.email(EMAIL)
				.firstName(FIRST_NAME)
				.id(ID)
				.lastName(LAST_NAME)
				.password(PASSWORD)
				.build();

		SecurityUtil.logInUser(user);

		SecurityContextAssert.assertThat(SecurityContextHolder.getContext())
				.loggedInUserIs(user)
				.loggedInUserHasPassword(PASSWORD);
	}

}
