package fr.mypr.ihm.controller;

import fr.mypr.*;
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

import java.util.Arrays;

import static integration.IntegrationTestConstants.User.REGISTERED_USER;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestWebConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
public class PersonalRecordControllerTest
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

	@Test
	public void showPersonalRecords_should_render_personal_records_list() throws Exception
	{

		when(personalRecordQueryServiceMock.personalRecordForAthlete(REGISTERED_USER.getEmail())).thenReturn(
				Arrays.asList(
						AthleteExercisePersonalRecordData.builder().exerciseName("Deadlift").exerciseUnit("kg").value(140f).build(),
						AthleteExercisePersonalRecordData.builder().exerciseName("Snatch").exerciseUnit("kg").value(57.0f).build(),
						AthleteExercisePersonalRecordData.builder().exerciseName("Pull ups").exerciseUnit("reps").value(22f).build())
		);

		// @formatter:off
		mockMvc.perform(get("/pr"))
				.andExpect(status().isOk())
				.andExpect(view().name("/pr/list"))
				.andExpect(model().attribute("personalRecords",
	                 Arrays.asList(
	                       AthleteExercisePersonalRecordData.builder().exerciseName("Deadlift").exerciseUnit("kg").value(140f).build(),
	                       AthleteExercisePersonalRecordData.builder().exerciseName("Snatch").exerciseUnit("kg").value(57.0f).build(),
	                       AthleteExercisePersonalRecordData.builder().exerciseName("Pull ups").exerciseUnit("reps").value(22f).build()))
				);
		// @formatter:on
	}
}