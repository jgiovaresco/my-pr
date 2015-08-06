package fr.mypr.ihm.security;


import fr.mypr.identityaccess.application.IdentityApplicationService;
import fr.mypr.identityaccess.domain.model.*;
import fr.mypr.identityaccess.domain.model.User;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.*;

import static com.googlecode.catchexception.CatchException.*;
import static fr.mypr.ihm.security.MyPrUserDetailsAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryUserDetailsServiceTest
{

	private static final String EMAIL = "foo@bar.com";
	private static final String FIRST_NAME = "Foo";
	private static final Long ID = 1L;
	private static final String LAST_NAME = "Bar";
	private static final String PASSWORD = "password";

	private RepositoryUserDetailsService service;

	@Mock
	private IdentityApplicationService identityApplicationServiceMock;

	@Before
	public void setUp()
	{
		service = new RepositoryUserDetailsService(identityApplicationServiceMock);
	}

	@Test
	public void should_throw_exception_when_loadByUsername_with_no_user()
	{
		when(identityApplicationServiceMock.user(EMAIL)).thenReturn(null);

		catchException(service).loadUserByUsername(EMAIL);
		Assertions.assertThat((Throwable) caughtException())
				.isExactlyInstanceOf(UsernameNotFoundException.class)
				.hasMessage("No user found with email : " + EMAIL)
				.hasNoCause();

		verify(identityApplicationServiceMock).user(EMAIL);
		verifyNoMoreInteractions(identityApplicationServiceMock);
	}

	@Test
	public void loadByUsername_UserRegisteredByUsingFormRegistration_ShouldReturnCorrectUserDetails()
	{
		User found = User.builder()
				.email(EMAIL)
				.password(PASSWORD)
				.person(Person.builder()
						        .name(new FullName(FIRST_NAME, LAST_NAME))
						        .build())
				.build();

		when(identityApplicationServiceMock.user(EMAIL)).thenReturn(found);

		UserDetails user = service.loadUserByUsername(EMAIL);
		MyPrUserDetails actual = (MyPrUserDetails) user;

		assertThat(actual)
				.hasFirstName(FIRST_NAME)
				.hasLastName(LAST_NAME)
				.hasPassword(PASSWORD)
				.hasUsername(EMAIL)
				.isActive()
				.isRegisteredUser();

		verify(identityApplicationServiceMock, times(1)).user(EMAIL);
		verifyNoMoreInteractions(identityApplicationServiceMock);
	}
}