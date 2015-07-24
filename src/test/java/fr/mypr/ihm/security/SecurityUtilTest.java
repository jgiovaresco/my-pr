package fr.mypr.ihm.security;

import fr.mypr.identityaccess.domain.model.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Constructor;

import static org.hamcrest.Matchers.isA;

public class SecurityUtilTest
{
	private static final String EMAIL = "foo@bar.com";
	private static final String FIRST_NAME = "Foo";
	private static final String LAST_NAME = "Bar";
	private static final String PASSWORD = "password";

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void constructor_should_throw_an_exception() throws Exception
	{
		exception.expectCause(isA(UnsupportedOperationException.class));

		Constructor<SecurityUtil> constructor = SecurityUtil.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

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
