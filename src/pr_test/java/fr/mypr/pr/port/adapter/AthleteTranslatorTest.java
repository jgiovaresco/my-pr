package fr.mypr.pr.port.adapter;

import fr.mypr.identityaccess.application.IdentityApplicationService;
import fr.mypr.identityaccess.domain.model.*;
import fr.mypr.pr.domain.model.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AthleteTranslatorTest
{
	private static final String EMAIL = "john@doe.com";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";

	private AthleteTranslator translator;

	@Mock
	private IdentityApplicationService identityApplicationServiceMock;

	@Before
	public void setUp() throws Exception
	{
		translator = new AthleteTranslator(identityApplicationServiceMock);
	}

	@Test
	public void athleteFrom_should_return_an_athlete()
	{
		User user = User.builder()
				.email(EMAIL)
				.person(Person.builder().name(new FullName(FIRST_NAME, LAST_NAME)).build())
				.build();

		when(identityApplicationServiceMock.user(EMAIL)).thenReturn(user);

		Athlete a = translator.athleteFrom(EMAIL);

		AthleteAssert.assertThat(a)
				.hasIdentity(EMAIL)
				.hasName(FIRST_NAME + " " + LAST_NAME);

		verify(identityApplicationServiceMock, times(1)).user(EMAIL);
		verifyNoMoreInteractions(identityApplicationServiceMock);
	}

}