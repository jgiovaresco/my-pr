package fr.mypr.security.service;


import fr.mypr.security.user.MyPrUserDetails;
import fr.mypr.user.model.UserAccount;
import fr.mypr.user.repository.UserAccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.*;

import java.util.Optional;

import static com.googlecode.catchexception.CatchException.*;
import static fr.mypr.security.user.MyPrUserDetailsAssert.assertThat;
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
	private UserAccountRepository repositoryMock;

	@Before
	public void setUp()
	{
		service = new RepositoryUserDetailsService(repositoryMock);
	}

	@Test
	public void should_throw_exception_when_loadByUsername_with_no_user()
	{
		when(repositoryMock.findByEmail(EMAIL)).thenReturn(Optional.<UserAccount>empty());

		catchException(service).loadUserByUsername(EMAIL);
		Assertions.assertThat((Throwable) caughtException())
				.isExactlyInstanceOf(UsernameNotFoundException.class)
				.hasMessage("No user found with email : " + EMAIL)
				.hasNoCause();

		verify(repositoryMock).findByEmail(EMAIL);
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	public void loadByUsername_UserRegisteredByUsingFormRegistration_ShouldReturnCorrectUserDetails() {
		UserAccount found = UserAccount.builder()
				.email(EMAIL)
				.firstName(FIRST_NAME)
				.id(ID)
				.lastName(LAST_NAME)
				.password(PASSWORD)
				.build();

		when(repositoryMock.findByEmail(EMAIL)).thenReturn(Optional.of(found));

		UserDetails user = service.loadUserByUsername(EMAIL);
		MyPrUserDetails actual = (MyPrUserDetails) user;

		assertThat(actual)
				.hasFirstName(FIRST_NAME)
				.hasId(ID)
				.hasLastName(LAST_NAME)
				.hasPassword(PASSWORD)
				.hasUsername(EMAIL)
				.isActive()
				.isRegisteredUser();

		verify(repositoryMock, times(1)).findByEmail(EMAIL);
		verifyNoMoreInteractions(repositoryMock);
	}
}