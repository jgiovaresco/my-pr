package integration.tests;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.*;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import fr.mypr.MyPrApplication;
import fr.mypr.ihm.controller.pr.data.PersonalRecordEditForm;
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

import java.time.LocalDate;

import static integration.IntegrationTestConstants.User.REGISTERED_USER;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"integrationTest"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MyPrApplication.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class PersonalRecordEditionFormSubmitIT
{
	private static final String EXERCISE_NAME = "Deadlift";
	private static final String PR_ID = "1";
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
	@DatabaseSetup({"exercises.xml", "personalRecords.xml"})
	@ExpectedDatabase(value = "personalRecords.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void showPersonalRecordEditionForm_should_render_login_page_with_user_not_logged() throws Exception
	{
		mockMvc.perform(get("/pr/edit")
				                .param("prId", "1"))
				.andExpect(redirectedUrl("http://localhost/login"))
		;
	}

	@Test
	@DatabaseSetup({"exercises.xml", "personalRecords.xml"})
	@ExpectedDatabase(value = "personalRecords.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void showPersonalRecordEditionForm_should_render_pr_creation_page_with_form_containing_data() throws Exception
	{
		mockMvc.perform(get("/pr/edit")
				                .param("prId", "1")
				                .with(user(REGISTERED_USER.getUserDetails())))
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("pr/editionForm"))
				.andExpect(model().attribute(PersonalRecordEditForm.PR_ATTRIBUTE_FORM, allOf(
						hasProperty(PersonalRecordEditForm.FIELD_ID, is(equalTo(PR_ID))),
						hasProperty(PersonalRecordEditForm.FIELD_DATE, is(equalTo(LocalDate.of(2014, 12, 12)))),
						hasProperty(PersonalRecordEditForm.FIELD_VALUE, is(equalTo(130.0f))),
						hasProperty(PersonalRecordEditForm.FIELD_EXERCISE_NAME, is(equalTo(EXERCISE_NAME)))
				)));
	}

	@Test
	@DatabaseSetup({"exercises.xml", "personalRecords.xml"})
	@ExpectedDatabase(value = "update-pr-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void updatePersonalRecord_should_render_creation_page_with_errors_when_create_with_empty_form() throws Exception
	{
		mockMvc.perform(post("/pr/edit")
				                .with(user(REGISTERED_USER.getUserDetails()))
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(PersonalRecordEditForm.FIELD_ID, PR_ID)
				                .param(PersonalRecordEditForm.FIELD_DATE, PR_DATE)
				                .param(PersonalRecordEditForm.FIELD_VALUE, PR_VALUE)
				                .param(PersonalRecordEditForm.FIELD_EXERCISE_NAME, EXERCISE_NAME)
				                .sessionAttr(PersonalRecordEditForm.PR_ATTRIBUTE_FORM, new PersonalRecordEditForm())
		)
//				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/pr"));
	}
}
