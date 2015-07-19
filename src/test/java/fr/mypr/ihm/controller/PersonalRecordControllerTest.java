package fr.mypr.ihm.controller;

import fr.mypr.UnitTestWebConfiguration;
import fr.mypr.ihm.WebConfiguration;
import fr.mypr.ihm.controller.data.PersonalRecordForm;
import fr.mypr.pr.application.*;
import fr.mypr.pr.application.data.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.*;

import static integration.IntegrationTestConstants.User.REGISTERED_USER;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestWebConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
public class PersonalRecordControllerTest
{
	private static final String EXERCISE_ID = "exId";
	private static final String PR_DATE = "2015-06-24";
	private static final String PR_VALUE = "130.0";

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private PersonalRecordApplicationService personalRecordApplicationServiceMock;
	private PersonalRecordQueryService personalRecordQueryServiceMock;
	private ExerciseQueryService exerciseQueryServiceMock;

	@Before
	public void setUp()
	{
		exerciseQueryServiceMock = webApplicationContext.getBean(ExerciseQueryService.class);
		personalRecordQueryServiceMock = webApplicationContext.getBean(PersonalRecordQueryService.class);
		personalRecordApplicationServiceMock = webApplicationContext.getBean(PersonalRecordApplicationService.class);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();

		UserDetails registeredUser = REGISTERED_USER.getUserDetails();
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(registeredUser, null, registeredUser.getAuthorities()));
	}

	@After
	public void tearDown() throws Exception
	{
		reset(exerciseQueryServiceMock, personalRecordApplicationServiceMock, personalRecordQueryServiceMock);
	}

	@Test
	public void showPersonalRecords_should_render_personal_records_list() throws Exception
	{
		Collection<AthleteExercisePersonalRecordData> personalRecordData = new ArrayList<>();

		when(personalRecordQueryServiceMock.personalRecordForAthlete(REGISTERED_USER.getEmail())).thenReturn(personalRecordData);

		mockMvc.perform(get("/pr"))
				.andExpect(status().isOk())
				.andExpect(view().name("/pr/list"))
				.andExpect(model().attribute("personalRecords", is(sameInstance(personalRecordData))));
	}

	@Test
	public void showPersonalRecordCreationForm_should_render_personal_record_creation_page_with_empty_form() throws Exception
	{
		Collection<ExerciseData> exerciseData = new ArrayList<>();
		when(exerciseQueryServiceMock.allExercises()).thenReturn(exerciseData);

		mockMvc.perform(get("/pr/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("pr/creationForm"))
				.andExpect(model().attribute(PersonalRecordForm.PR_ATTRIBUTE_FORM, allOf(
						hasProperty(PersonalRecordForm.FIELD_DATE, isEmptyOrNullString()),
						hasProperty(PersonalRecordForm.FIELD_VALUE, isEmptyOrNullString()),
						hasProperty(PersonalRecordForm.FIELD_EXERCISE, isEmptyOrNullString())
				)))
				.andExpect(model().attribute("exercises", is(sameInstance(exerciseData))));
	}

	@Test
	public void addNewPersonalRecord_should_render_personal_record_creation_page_with_errors_when_create_with_empty_form() throws Exception
	{
		mockMvc.perform(post("/pr/new")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .sessionAttr(PersonalRecordForm.EXERCISES_ATTRIBUTE_FORM, new PersonalRecordForm())
		)
				.andExpect(status().isOk())
				.andExpect(view().name("pr/creationForm"))
				.andExpect(model().attribute(PersonalRecordForm.PR_ATTRIBUTE_FORM, allOf(
						hasProperty(PersonalRecordForm.FIELD_DATE, isEmptyOrNullString()),
						hasProperty(PersonalRecordForm.FIELD_VALUE, isEmptyOrNullString()),
						hasProperty(PersonalRecordForm.FIELD_EXERCISE, isEmptyOrNullString())
				)))
				.andExpect(model().attributeHasFieldErrors(
						PersonalRecordForm.PR_ATTRIBUTE_FORM,
						PersonalRecordForm.FIELD_DATE,
						PersonalRecordForm.FIELD_VALUE,
						PersonalRecordForm.FIELD_EXERCISE
				));
	}

	@Test
	public void addNewPersonalRecord_should_render_personal_records_list_page_when_no_error() throws Exception
	{
		mockMvc.perform(post("/pr/new")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(PersonalRecordForm.FIELD_DATE, PR_DATE)
				                .param(PersonalRecordForm.FIELD_VALUE, PR_VALUE)
				                .param(PersonalRecordForm.FIELD_EXERCISE, EXERCISE_ID)
				                .sessionAttr(PersonalRecordForm.EXERCISES_ATTRIBUTE_FORM, new PersonalRecordForm())
		)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/pr"));
	}

	@Test
	public void addNewPersonalRecord_should_create_personal_record_when_no_error() throws Exception
	{
		mockMvc.perform(post("/pr/new")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(PersonalRecordForm.FIELD_DATE, PR_DATE)
				                .param(PersonalRecordForm.FIELD_VALUE, PR_VALUE)
				                .param(PersonalRecordForm.FIELD_EXERCISE, EXERCISE_ID)
				                .sessionAttr(PersonalRecordForm.EXERCISES_ATTRIBUTE_FORM, new PersonalRecordForm())
		)
				.andExpect(status().isFound());

		verify(personalRecordApplicationServiceMock)
				.newExercisePersonalRecordForAthlete(REGISTERED_USER.getEmail(), EXERCISE_ID, LocalDate.of(2015, 6, 24), 130.0f);
		verifyNoMoreInteractions(personalRecordApplicationServiceMock);
	}
}