package fr.mypr.identityaccess.application;

import com.googlecode.catchexception.CatchException;
import fr.mypr.identityaccess.command.RegisterUserCommand;
import fr.mypr.identityaccess.domain.model.*;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IdentityApplicationServiceTest
{
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	private static final String FIRST_NAME = "first";
	private static final String LAST_NAME = "last";

	private IdentityApplicationService service;

	@Mock
	private AuthenticationService authenticationServiceMock;
	@Mock
	private UserRepository userRepositoryMock;

	@Before
	public void setUp() throws Exception
	{
		service = new IdentityApplicationService(authenticationServiceMock, userRepositoryMock);
	}

	@Test
	public void authenticateUser_should_return_user_descriptor() throws Exception
	{
		when(authenticationServiceMock.authenticate(EMAIL, PASSWORD)).thenReturn(UserDescriptor.nullDescriptorInstance());

		UserDescriptor result = service.authenticateUser(EMAIL, PASSWORD);

		UserDescriptorAssert.assertThat(result).isNullDescriptor();
		verify(authenticationServiceMock, times(1)).authenticate(EMAIL, PASSWORD);
		verifyNoMoreInteractions(authenticationServiceMock);
	}

	@Test
	public void user_should_return_null_when_optional_user_empty() throws Exception
	{
		when(userRepositoryMock.userWithEmail(EMAIL)).thenReturn(Optional.<User>empty());

		User result = service.user(EMAIL);

		assertThat(result).isNull();
		verify(userRepositoryMock, times(1)).userWithEmail(EMAIL);
		verifyNoMoreInteractions(userRepositoryMock);
	}

	@Test
	public void user_should_return_user_when_user_found() throws Exception
	{
		User expected = User.builder().build();
		when(userRepositoryMock.userWithEmail(EMAIL)).thenReturn(Optional.of(expected));

		User result = service.user(EMAIL);

		assertThat(result)
				.isNotNull()
				.isSameAs(expected);
		verify(userRepositoryMock, times(1)).userWithEmail(EMAIL);
		verifyNoMoreInteractions(userRepositoryMock);
	}

	@Test
	public void registerUser_should_throw_DuplicatedEmailException_when_email_exists() throws Exception
	{
		RegisterUserCommand command = RegisterUserCommand.builder()
				.email(EMAIL)
				.password(PASSWORD)
				.build();

		when(userRepositoryMock.userWithEmail(EMAIL)).thenReturn(Optional.of(User.builder().build()));

		catchException(service).registerUser(command);


		Assertions.assertThat(CatchException.<Exception>caughtException())
				.isExactlyInstanceOf(DuplicateEmailException.class)
				.hasMessage(String.format("The email address: %s is already in use.", EMAIL))
				.hasNoCause();

		verify(userRepositoryMock, times(1)).userWithEmail(EMAIL);
		verifyNoMoreInteractions(userRepositoryMock);
	}


	@Test
	public void registerUser_should_add_user_to_repository() throws Exception
	{
		RegisterUserCommand command = RegisterUserCommand.builder()
				.email(EMAIL)
				.password(PASSWORD)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.build();

		when(userRepositoryMock.userWithEmail(EMAIL)).thenReturn(Optional.<User>empty());

		service.registerUser(command);

		verify(userRepositoryMock, times(1)).userWithEmail(EMAIL);

		ArgumentCaptor<User> arg = ArgumentCaptor.forClass(User.class);
		verify(userRepositoryMock, times(1)).add(arg.capture());
		verifyNoMoreInteractions(userRepositoryMock);
		// @formatter:off
		UserAssert.assertThat(arg.getValue())
				.hasEmail(EMAIL)
				.hasPassword(PASSWORD)
				.assertPerson()
					.hasFirstname(FIRST_NAME)
					.hasLastname(LAST_NAME);
		// @formatter:on
	}
}