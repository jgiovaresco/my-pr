package fr.mypr.ihm.security;

import fr.mypr.identityaccess.domain.model.User;
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

	public SecurityContextAssert loggedInUserIs(User user)
	{
		isNotNull();

		MyPrUserDetails loggedIn = (MyPrUserDetails) actual.getAuthentication().getPrincipal();

		Assertions.assertThat(loggedIn)
				.overridingErrorMessage("Expected logged in user to be <%s> but was <null>",
				                        user
				)
				.isNotNull();

		MyPrUserDetailsAssert.assertThat(loggedIn)
				.hasFirstName(user.person().name().firstName())
				.hasLastName(user.person().name().lastName())
				.hasUsername(user.email())
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
