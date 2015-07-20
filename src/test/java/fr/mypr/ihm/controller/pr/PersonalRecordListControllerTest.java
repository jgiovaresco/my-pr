package fr.mypr.ihm.controller.pr;

import fr.mypr.UnitTestWebConfiguration;
import fr.mypr.ihm.WebConfiguration;
import fr.mypr.pr.application.PersonalRecordQueryService;
import fr.mypr.pr.application.data.AthleteExercisePersonalRecordData;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static integration.IntegrationTestConstants.User.REGISTERED_USER;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestWebConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
public class PersonalRecordListControllerTest
{
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private PersonalRecordQueryService personalRecordQueryServiceMock;

	@Before
	public void setUp()
	{
		personalRecordQueryServiceMock = webApplicationContext.getBean(PersonalRecordQueryService.class);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();

		UserDetails registeredUser = REGISTERED_USER.getUserDetails();
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(registeredUser, null, registeredUser.getAuthorities()));
	}

	@After
	public void tearDown() throws Exception
	{
		reset(personalRecordQueryServiceMock);
	}

	@Test
	public void showPersonalRecords_should_render_personal_records_list() throws Exception
	{
		Collection<AthleteExercisePersonalRecordData> personalRecordData = new ArrayList<>();

		when(personalRecordQueryServiceMock.allPersonalRecordsOfAthlete(REGISTERED_USER.getEmail())).thenReturn(personalRecordData);

		mockMvc.perform(get("/pr"))
				.andExpect(status().isOk())
				.andExpect(view().name("/pr/list"))
				.andExpect(model().attribute("personalRecords", is(sameInstance(personalRecordData))));
	}
}