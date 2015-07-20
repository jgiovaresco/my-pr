package fr.mypr.ihm.security;

import fr.mypr.identityaccess.domain.model.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Constructor;

import static integration.IntegrationTestConstants.User.REGISTERED_USER;
import static org.assertj.core.api.Assertions.assertThat;
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

	@Test
	public void principal_should_return_null_when_no_user_logged()
	{
		MyPrUserDetails principal = SecurityUtil.principal();
		assertThat(principal).isNull();
	}

	@Test
	public void principal_should_return_MyPrUserDetails_when_user_logged()
	{
		UserDetails registeredUser = REGISTERED_USER.getUserDetails();
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(registeredUser, null, registeredUser.getAuthorities()));

		MyPrUserDetailsAssert.assertThat(SecurityUtil.principal())
				.isNotNull()
				.isSameAs(REGISTERED_USER.getUserDetails());
	}
}
