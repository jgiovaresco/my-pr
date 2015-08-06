package fr.mypr.identityaccess.domain.model;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest
{
	private static final String EMAIL = "foo@bar.com";
	private static final String PASSWORD = "password";
	private static final String ENCRYPTED_PASSWORD = "encryptedPassword";

	private AuthenticationService service;

	@Mock
	private UserRepository repositoryMock;

	@Mock
	private PasswordEncoder passwordEncoderMock;

	@Before
	public void setUp() throws Exception
	{
		service = new AuthenticationService(repositoryMock, passwordEncoderMock);
	}

	@Test
	public void authenticate_should_return_nullUserDescriptor_optional_when_no_user() throws Exception
	{
		when(passwordEncoderMock.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
		when(repositoryMock.userWithCredentials(EMAIL, ENCRYPTED_PASSWORD)).thenReturn(Optional.<User>empty());

		UserDescriptor result = service.authenticate(EMAIL, PASSWORD);

		UserDescriptorAssert.assertThat(result)
				.isNullDescriptor();

		verify(repositoryMock, times(1)).userWithCredentials(EMAIL, ENCRYPTED_PASSWORD);
		verifyNoMoreInteractions(repositoryMock);
	}


	@Test
	public void authenticate_should_return_user_description_optional_when_user_found() throws Exception
	{
		User user = User.builder()
				.email(EMAIL)
				.password(ENCRYPTED_PASSWORD)
				.build();

		when(passwordEncoderMock.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);
		when(repositoryMock.userWithCredentials(EMAIL, ENCRYPTED_PASSWORD)).thenReturn(Optional.of(user));

		UserDescriptor result = service.authenticate(EMAIL, PASSWORD);

		UserDescriptorAssert.assertThat(result)
				.hasEmail(EMAIL)
				.hasPassword(ENCRYPTED_PASSWORD);

		verify(repositoryMock, times(1)).userWithCredentials(EMAIL, ENCRYPTED_PASSWORD);
		verifyNoMoreInteractions(repositoryMock);
	}
}