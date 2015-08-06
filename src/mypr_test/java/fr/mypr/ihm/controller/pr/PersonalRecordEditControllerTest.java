package fr.mypr.ihm.controller.pr;

import fr.mypr.UnitTestWebConfiguration;
import fr.mypr.ihm.WebConfiguration;
import fr.mypr.ihm.controller.pr.data.PersonalRecordEditForm;
import fr.mypr.pr.application.*;
import fr.mypr.pr.application.data.AthleteExercisePersonalRecordData;
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

import static integration.IntegrationTestConstants.User.REGISTERED_USER;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestWebConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
public class PersonalRecordEditControllerTest
{
	private static final String EXERCISE_NAME = "Deadlift";
	private static final String PR_ID = "prId";
	private static final LocalDate PR_DATE = LocalDate.of(2015, 6, 24);
	private static final Float PR_VALUE = 130.0f;

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
	public void showPersonalRecordEditionForm_should_render_pr_creation_page_with_form_containing_data() throws Exception
	{
		AthleteExercisePersonalRecordData personalRecordData = AthleteExercisePersonalRecordData.builder()
				.id(PR_ID)
				.value(PR_VALUE)
				.date(PR_DATE)
				.exerciseName(EXERCISE_NAME)
				.build();
		when(personalRecordQueryServiceMock.personalRecordDataOfId(REGISTERED_USER.getEmail(), PR_ID)).thenReturn(personalRecordData);

		mockMvc.perform(get("/pr/edit")
				                .param("prId", PR_ID))
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("pr/editionForm"))
				.andExpect(model().attribute(PersonalRecordEditForm.PR_ATTRIBUTE_FORM, allOf(
						hasProperty(PersonalRecordEditForm.FIELD_ID, is(equalTo(PR_ID))),
						hasProperty(PersonalRecordEditForm.FIELD_VALUE, is(equalTo(PR_VALUE))),
						hasProperty(PersonalRecordEditForm.FIELD_EXERCISE_NAME, is(equalTo(EXERCISE_NAME)))
				)));
	}

	@Test
	public void updatePersonalRecord_should_render_personal_record_edition_page_with_errors_when_edit_with_empty_form() throws Exception
	{
		mockMvc.perform(post("/pr/edit")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(PersonalRecordEditForm.FIELD_ID, PR_ID)
				                .param(PersonalRecordEditForm.FIELD_DATE, "2015-06-24")
				                .param(PersonalRecordEditForm.FIELD_VALUE, "130")
				                .param(PersonalRecordEditForm.FIELD_EXERCISE_NAME, EXERCISE_NAME)
				                .sessionAttr(PersonalRecordEditForm.PR_ATTRIBUTE_FORM, new PersonalRecordEditForm())
		)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/pr"));

		verify(personalRecordApplicationServiceMock).newPersonalRecordForExercise(PR_ID, LocalDate.of(2015, 6, 24), 130.0f);
		verifyNoMoreInteractions(personalRecordApplicationServiceMock);
	}
}