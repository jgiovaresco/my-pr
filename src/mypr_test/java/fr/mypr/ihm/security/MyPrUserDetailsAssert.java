package fr.mypr.ihm.security;

import org.assertj.core.api.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyPrUserDetailsAssert extends AbstractAssert<MyPrUserDetailsAssert, MyPrUserDetails>
{

	private MyPrUserDetailsAssert(MyPrUserDetails actual)
	{
		super(actual, MyPrUserDetailsAssert.class);
	}

	public static MyPrUserDetailsAssert assertThat(MyPrUserDetails actual)
	{
		return new MyPrUserDetailsAssert(actual);
	}

	public MyPrUserDetailsAssert hasFirstName(String firstName)
	{
		isNotNull();

		Assertions.assertThat(actual.getFirstName())
				.overridingErrorMessage("Expected first name to be <%s> but was <%s>",
				                        firstName,
				                        actual.getFirstName()
				)
				.isEqualTo(firstName);

		return this;
	}

	public MyPrUserDetailsAssert hasLastName(String lastName)
	{
		isNotNull();

		Assertions.assertThat(actual.getLastName())
				.overridingErrorMessage("Expected last name to be <%s> but was <%s>",
				                        lastName,
				                        actual.getLastName()
				)
				.isEqualTo(lastName);

		return this;
	}

	public MyPrUserDetailsAssert hasPassword(String password)
	{
		isNotNull();

		Assertions.assertThat(actual.getPassword())
				.overridingErrorMessage("Expected password to be <%s> but was <%s>",
				                        password,
				                        actual.getPassword()
				)
				.isEqualTo(password);

		return this;
	}

	public MyPrUserDetailsAssert hasUsername(String username)
	{
		isNotNull();

		Assertions.assertThat(actual.getUsername())
				.overridingErrorMessage("Expected username to be <%s> but was <%s>",
				                        username,
				                        actual.getUsername()
				)
				.isEqualTo(username);

		return this;
	}

	public MyPrUserDetailsAssert isActive()
	{
		isNotNull();

		Assertions.assertThat(actual.isAccountNonExpired())
				.overridingErrorMessage("Expected account to be non expired but it was expired")
				.isTrue();

		Assertions.assertThat(actual.isAccountNonLocked())
				.overridingErrorMessage("Expected account to be non locked but it was locked")
				.isTrue();

		Assertions.assertThat(actual.isCredentialsNonExpired())
				.overridingErrorMessage("Expected credentials to be non expired but they were expired")
				.isTrue();

		Assertions.assertThat(actual.isEnabled())
				.overridingErrorMessage("Expected account to be enabled but it was not")
				.isTrue();

		return this;
	}

	public MyPrUserDetailsAssert isRegisteredUser()
	{
		isNotNull();

		Assertions.assertThat(actual.getRole())
				.overridingErrorMessage("Expected role to be <ROLE_USER> but was <%s>",
				                        actual.getRole()
				)
				.isEqualTo(Role.ROLE_USER);

		Collection<? extends GrantedAuthority> authorities = actual.getAuthorities();

		Assertions.assertThat(authorities.size())
				.overridingErrorMessage("Expected <1> granted authority but found <%d>",
				                        authorities.size()
				)
				.isEqualTo(1);

		GrantedAuthority authority = authorities.iterator().next();

		Assertions.assertThat(authority.getAuthority())
				.overridingErrorMessage("Expected authority to be <ROLE_USER> but was <%s>",
				                        authority.getAuthority()
				)
				.isEqualTo(Role.ROLE_USER.name());

		return this;
	}
}
