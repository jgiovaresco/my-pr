package fr.mypr.security.util;

import fr.mypr.security.user.*;
import fr.mypr.user.model.UserAccount;
import org.assertj.core.api.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

public class SecurityContextAssert extends AbstractAssert<SecurityContextAssert, SecurityContext>
{

	private SecurityContextAssert(SecurityContext actual)
	{
		super(actual, SecurityContextAssert.class);
	}

	public static SecurityContextAssert assertThat(SecurityContext actual)
	{
		return new SecurityContextAssert(actual);
	}

	public SecurityContextAssert userIsAnonymous()
	{
		isNotNull();

		Authentication authentication = actual.getAuthentication();

		Assertions.assertThat(authentication)
				.overridingErrorMessage("Expected authentication to be <null> but was <%s>.",
				                        authentication
				)
				.isNull();

		return this;
	}

	public SecurityContextAssert loggedInUserIs(UserAccount user)
	{
		isNotNull();

		MyPrUserDetails loggedIn = (MyPrUserDetails) actual.getAuthentication().getPrincipal();

		Assertions.assertThat(loggedIn)
				.overridingErrorMessage("Expected logged in user to be <%s> but was <null>",
				                        user
				)
				.isNotNull();

		MyPrUserDetailsAssert.assertThat(loggedIn)
				.hasFirstName(user.getFirstName())
				.hasId(user.getId())
				.hasLastName(user.getLastName())
				.hasUsername(user.getEmail())
				.isActive()
				.isRegisteredUser();

		return this;
	}

	public SecurityContextAssert loggedInUserHasPassword(String password)
	{
		isNotNull();

		MyPrUserDetails loggedIn = (MyPrUserDetails) actual.getAuthentication().getPrincipal();

		Assertions.assertThat(loggedIn)
				.overridingErrorMessage("Expected logged in user to be <not null> but was <null>")
				.isNotNull();

		MyPrUserDetailsAssert.assertThat(loggedIn)
				.hasPassword(password);

		return this;
	}
}
