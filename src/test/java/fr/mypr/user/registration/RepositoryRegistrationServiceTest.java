package fr.mypr.user.registration;

import com.googlecode.catchexception.CatchException;
import fr.mypr.ihm.controller.RegistrationForm;
import fr.mypr.user.model.*;
import fr.mypr.user.repository.UserAccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryRegistrationServiceTest
{

	private static final String EMAIL = "john.smith@gmail.com";
	private static final String ENCODED_PASSWORD = "encodedPassword";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Smith";
	private static final String PASSWORD = "password";

	private RepositoryRegistrationService service;

	@Mock
	private PasswordEncoder passwordEncoderMock;

	@Mock
	private UserAccountRepository repositoryMock;

	@Before
	public void setUp()
	{
		service = new RepositoryRegistrationService(passwordEncoderMock, repositoryMock);
	}

	@Test
	public void registerNewUserAccount_should_create_new_user_account() throws DuplicateEmailException
	{
		RegistrationForm registration = RegistrationForm.builder()
				.email(EMAIL)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.password(PASSWORD)
				.confirmPassword(PASSWORD)
				.build();

		when(repositoryMock.findByEmail(EMAIL)).thenReturn(Optional.<UserAccount>empty());
		when(passwordEncoderMock.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);

		when(repositoryMock.save(isA(UserAccount.class))).thenAnswer(invocation -> {
			Object[] arguments = invocation.getArguments();
			return arguments[0];
		});

		UserAccount registered = service.registerNewUserAccount(registration);

		UserAccountAssert.assertThat(registered)
				.hasEmail(EMAIL)
				.hasFirstName(FIRST_NAME)
				.hasLastName(LAST_NAME)
				.hasPassword(ENCODED_PASSWORD)
				.isRegisteredUser();

		verify(repositoryMock, times(1)).findByEmail(EMAIL);

		verify(passwordEncoderMock, times(1)).encode(PASSWORD);
		verifyNoMoreInteractions(passwordEncoderMock);

		verify(repositoryMock, times(1)).save(registered);
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	public void registerNewUserAccount_should_throw_exception_when_duplicate_email() throws DuplicateEmailException
	{
		RegistrationForm registration = RegistrationForm.builder()
				.email(EMAIL)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.password(PASSWORD)
				.confirmPassword(PASSWORD)
				.build();

		when(repositoryMock.findByEmail(EMAIL)).thenReturn(Optional.of(new UserAccount()));

		catchException(service).registerNewUserAccount(registration);

		Assertions.assertThat(CatchException.<Exception>caughtException())
				.isExactlyInstanceOf(DuplicateEmailException.class)
				.hasMessage(String.format("The email address: %s is already in use.", EMAIL))
				.hasNoCause();

		verify(repositoryMock, times(1)).findByEmail(EMAIL);
		verifyNoMoreInteractions(repositoryMock);
		verifyZeroInteractions(passwordEncoderMock);
	}
}