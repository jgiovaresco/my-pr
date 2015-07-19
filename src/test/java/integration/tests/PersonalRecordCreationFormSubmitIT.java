package integration.tests;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.*;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import fr.mypr.MyPrApplication;
import fr.mypr.ihm.controller.data.PersonalRecordForm;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static integration.IntegrationTestConstants.User.REGISTERED_USER;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"integrationTest"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MyPrApplication.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class PersonalRecordCreationFormSubmitIT
{
	private static final String EXERCISE_ID = "1";
	private static final String PR_DATE = "2015-06-24";
	private static final String PR_VALUE = "140";

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp()
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.addFilter(springSecurityFilterChain)
				.build();
	}


	@Test
	@DatabaseSetup({"no-exercises.xml", "no-pr.xml"})
	@ExpectedDatabase(value = "no-pr.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void showPersonalRecordCreationForm_should_render_login_page_with_user_not_logged() throws Exception
	{
		mockMvc.perform(get("/pr/new"))
				.andExpect(redirectedUrl("http://localhost/login"))
		;
	}

	@Test
	@DatabaseSetup({"no-exercises.xml", "no-pr.xml"})
	@ExpectedDatabase(value = "no-exercises.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void showPersonalRecordCreationForm_should_render_personal_creation_creation_page_with_empty_form() throws Exception
	{
		mockMvc.perform(get("/pr/new")
				                .with(user(REGISTERED_USER.getUserDetails())))
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("pr/creationForm"))
				.andExpect(model().attribute(PersonalRecordForm.EXERCISES_ATTRIBUTE_FORM, allOf(
						hasProperty(PersonalRecordForm.FIELD_DATE, isEmptyOrNullString()),
						hasProperty(PersonalRecordForm.FIELD_VALUE, isEmptyOrNullString()),
						hasProperty(PersonalRecordForm.FIELD_EXERCISE, isEmptyOrNullString())
				)));
	}

	@Test
	@DatabaseSetup({"no-exercises.xml", "no-pr.xml"})
	@ExpectedDatabase(value = "no-exercises.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void addNewPersonalRecord_should_render_creation_page_with_errors_when_create_with_empty_form() throws Exception
	{
		mockMvc.perform(post("/pr/new")
				                .with(user(REGISTERED_USER.getUserDetails()))
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
	@DatabaseSetup({"exercises.xml", "no-pr.xml", "users.xml"})
	@ExpectedDatabase(value = "creation-pr-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void addNewPersonalRecord_should_create_new_pr_and_render_list_page() throws Exception
	{
		mockMvc.perform(post("/pr/new")
				                .with(user(REGISTERED_USER.getUserDetails()))
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(PersonalRecordForm.FIELD_DATE, PR_DATE)
				                .param(PersonalRecordForm.FIELD_VALUE, PR_VALUE)
				                .param(PersonalRecordForm.FIELD_EXERCISE, EXERCISE_ID)
				                .sessionAttr(PersonalRecordForm.EXERCISES_ATTRIBUTE_FORM, new PersonalRecordForm())
		)
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/pr"));
	}
}
