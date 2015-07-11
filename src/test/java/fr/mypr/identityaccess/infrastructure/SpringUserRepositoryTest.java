package fr.mypr.identityaccess.infrastructure;

import fr.mypr.identityaccess.domain.model.*;
import fr.mypr.identityaccess.domain.persistence.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SpringUserRepositoryTest
{
	private static final String EMAIL = "foo@bar.com";
	private static final String PASSWORD = "password";
	private static final String FIRSTNAME = "foo";
	private static final String LASTNAME = "bar";

	private SpringUserRepository service;

	@Mock
	private UserAccountRepository repositoryMock;

	@Before
	public void setUp()
	{
		service = new SpringUserRepository(repositoryMock);
	}

	@Test
	public void userWithCredentials_should_return_empty_optional_when_with_no_user_found()
	{
		when(repositoryMock.findByCredentials(EMAIL, PASSWORD)).thenReturn(Optional.<UserAccount>empty());

		Optional<User> result = service.userWithCredentials(EMAIL, PASSWORD);

		assertThat(result).isEmpty();
		verify(repositoryMock, times(1)).findByCredentials(EMAIL, PASSWORD);
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	public void userWithCredentials_should_return_user_descriptor_when_with_user_found()
	{
		UserAccount found = UserAccount.builder()
				.email(EMAIL)
				.password(PASSWORD)
				.firstName(FIRSTNAME)
				.lastName(LASTNAME)
				.build();

		when(repositoryMock.findByCredentials(EMAIL, PASSWORD)).thenReturn(Optional.of(found));

		Optional<User> result = service.userWithCredentials(EMAIL, PASSWORD);

		assertThat(result).isPresent();
		UserAssert.assertThat(result.get())
				.hasEmail(EMAIL)
				.hasPassword(PASSWORD)
				.assertPerson()
				.hasFirstname(FIRSTNAME)
				.hasLastname(LASTNAME);

		verify(repositoryMock, times(1)).findByCredentials(EMAIL, PASSWORD);
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	public void userWithUsername_should_return_empty_optional_when_with_no_user_found()
	{
		when(repositoryMock.findByEmail(EMAIL)).thenReturn(Optional.<UserAccount>empty());

		Optional<User> result = service.userWithEmail(EMAIL);

		assertThat(result).isEmpty();
		verify(repositoryMock, times(1)).findByEmail(EMAIL);
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	public void userWithUsername_should_return_user_descriptor_when_with_user_found()
	{
		UserAccount found = UserAccount.builder()
				.email(EMAIL)
				.password(PASSWORD)
				.firstName(FIRSTNAME)
				.lastName(LASTNAME)
				.build();

		when(repositoryMock.findByEmail(EMAIL)).thenReturn(Optional.of(found));

		Optional<User> result = service.userWithEmail(EMAIL);

		assertThat(result).isPresent();
		// @formatter:off
		UserAssert.assertThat(result.get())
				.hasEmail(EMAIL)
				.hasPassword(PASSWORD)
				.assertPerson()
					.hasFirstname(FIRSTNAME)
					.hasLastname(LASTNAME);
		// @formatter:on
		verify(repositoryMock, times(1)).findByEmail(EMAIL);
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	public void add_should_save_userAccount()
	{
		User user = User.builder()
				.email(EMAIL)
				.password(PASSWORD)
				.person(Person.builder().name(new FullName(FIRSTNAME, LASTNAME)).build())
				.build();

		service.add(user);

		ArgumentCaptor<UserAccount> arg = ArgumentCaptor.forClass(UserAccount.class);
		verify(repositoryMock, times(1)).save(arg.capture());
		verifyNoMoreInteractions(repositoryMock);

		UserAccountAssert.assertThat(arg.getValue())
				.hasEmail(EMAIL)
				.hasPassword(PASSWORD)
				.hasFirstName(FIRSTNAME)
				.hasLastName(LASTNAME);
	}
}