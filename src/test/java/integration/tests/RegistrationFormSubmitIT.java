package integration.tests;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.*;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import fr.mypr.MyPrApplication;
import fr.mypr.ihm.controller.RegistrationForm;
import integration.IntegrationTestConstants;
import org.apache.commons.lang3.RandomStringUtils;
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

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"integrationTest"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MyPrApplication.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class RegistrationFormSubmitIT
{

	private static final String EMAIL = "john.smith@gmail.com";
	private static final String MALFORMED_EMAIL = "john.smithatgmail.com";
	private static final String PASSWORD = "password";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Smith";

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
	@DatabaseSetup("no-users.xml")
	@ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void showRegistrationForm_should_render_registration_page_with_empty_form() throws Exception
	{
		mockMvc.perform(get("/user/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/registrationForm"))
				.andExpect(model().attribute("user", allOf(
						hasProperty("email", isEmptyOrNullString()),
						hasProperty("firstName", isEmptyOrNullString()),
						hasProperty("lastName", isEmptyOrNullString()),
						hasProperty("password", isEmptyOrNullString()),
						hasProperty("confirmPassword", isEmptyOrNullString())
				)));
	}

	@Test
	@DatabaseSetup("no-users.xml")
	@ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void registerUserAccount_should_render_registration_form_with_errors_when_registration_with_empty_form() throws Exception
	{
		mockMvc.perform(post("/user/register")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .sessionAttr(RegistrationForm.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
		)
				.andExpect(status().isOk())
				.andExpect(view().name("user/registrationForm"))
				.andExpect(model().attribute(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM, allOf(
						hasProperty(RegistrationForm.FIELD_NAME_EMAIL, isEmptyOrNullString()),
						hasProperty(RegistrationForm.FIELD_NAME_FIRST_NAME, isEmptyOrNullString()),
						hasProperty(RegistrationForm.FIELD_NAME_LAST_NAME, isEmptyOrNullString()),
						hasProperty(RegistrationForm.FIELD_NAME_PASSWORD, isEmptyOrNullString()),
						hasProperty(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, isEmptyOrNullString())
				)))
				.andExpect(model().attributeHasFieldErrors(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM,
				                                           RegistrationForm.FIELD_NAME_EMAIL,
				                                           RegistrationForm.FIELD_NAME_FIRST_NAME,
				                                           RegistrationForm.FIELD_NAME_LAST_NAME,
				                                           RegistrationForm.FIELD_NAME_PASSWORD,
				                                           RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD
				));
	}

	@Test
	@DatabaseSetup("no-users.xml")
	@ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void registerUserAccount_should_render_registration_form_with_errors_when_registration_with_too_long_values() throws Exception
	{

		String email = RandomStringUtils.random(101);
		String firstName = RandomStringUtils.random(101);
		String lastName = RandomStringUtils.random(101);

		mockMvc.perform(post("/user/register")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(RegistrationForm.FIELD_NAME_EMAIL, email)
				                .param(RegistrationForm.FIELD_NAME_FIRST_NAME, firstName)
				                .param(RegistrationForm.FIELD_NAME_LAST_NAME, lastName)
				                .param(RegistrationForm.FIELD_NAME_PASSWORD, PASSWORD)
				                .param(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, PASSWORD)
				                .sessionAttr(RegistrationForm.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
		)
				.andExpect(status().isOk())
				.andExpect(view().name("user/registrationForm"))
				.andExpect(model().attribute(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM, allOf(
						hasProperty(RegistrationForm.FIELD_NAME_EMAIL, is(email)),
						hasProperty(RegistrationForm.FIELD_NAME_FIRST_NAME, is(firstName)),
						hasProperty(RegistrationForm.FIELD_NAME_LAST_NAME, is(lastName)),
						hasProperty(RegistrationForm.FIELD_NAME_PASSWORD, is(PASSWORD)),
						hasProperty(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, is(PASSWORD))
				)))
				.andExpect(model().attributeHasFieldErrors(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM,
				                                           RegistrationForm.FIELD_NAME_EMAIL,
				                                           RegistrationForm.FIELD_NAME_FIRST_NAME,
				                                           RegistrationForm.FIELD_NAME_LAST_NAME
				));
	}

	@Test
	@DatabaseSetup("no-users.xml")
	@ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void registerUserAccount_should_render_registration_form_with_errors_when_registration_with_password_mismatch() throws Exception
	{
		String incorrectPassword = "mismatch";

		mockMvc.perform(post("/user/register")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(RegistrationForm.FIELD_NAME_EMAIL, EMAIL)
				                .param(RegistrationForm.FIELD_NAME_FIRST_NAME, FIRST_NAME)
				                .param(RegistrationForm.FIELD_NAME_LAST_NAME, LAST_NAME)
				                .param(RegistrationForm.FIELD_NAME_PASSWORD, PASSWORD)
				                .param(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, incorrectPassword)
				                .sessionAttr(RegistrationForm.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
		)
				.andExpect(status().isOk())
				.andExpect(view().name("user/registrationForm"))
				.andExpect(model().attribute(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM, allOf(
						hasProperty(RegistrationForm.FIELD_NAME_EMAIL, is(EMAIL)),
						hasProperty(RegistrationForm.FIELD_NAME_FIRST_NAME, is(FIRST_NAME)),
						hasProperty(RegistrationForm.FIELD_NAME_LAST_NAME, is(LAST_NAME)),
						hasProperty(RegistrationForm.FIELD_NAME_PASSWORD, is(PASSWORD)),
						hasProperty(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, is(incorrectPassword))
				)))
				.andExpect(model().attributeHasFieldErrors(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM,
				                                           RegistrationForm.FIELD_NAME_PASSWORD,
				                                           RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD
				));
	}

	@Test
	@DatabaseSetup("no-users.xml")
	@ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void registerUserAccount_should_render_registration_form_with_errors_when_registration_with_malformed_email() throws Exception
	{
		mockMvc.perform(post("/user/register")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(RegistrationForm.FIELD_NAME_EMAIL, MALFORMED_EMAIL)
				                .param(RegistrationForm.FIELD_NAME_FIRST_NAME, FIRST_NAME)
				                .param(RegistrationForm.FIELD_NAME_LAST_NAME, LAST_NAME)
				                .param(RegistrationForm.FIELD_NAME_PASSWORD, PASSWORD)
				                .param(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, PASSWORD)
				                .sessionAttr(RegistrationForm.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
		)
				.andExpect(status().isOk())
				.andExpect(view().name("user/registrationForm"))
				.andExpect(model().attribute(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM, allOf(
						hasProperty(RegistrationForm.FIELD_NAME_EMAIL, is(MALFORMED_EMAIL)),
						hasProperty(RegistrationForm.FIELD_NAME_FIRST_NAME, is(FIRST_NAME)),
						hasProperty(RegistrationForm.FIELD_NAME_LAST_NAME, is(LAST_NAME)),
						hasProperty(RegistrationForm.FIELD_NAME_PASSWORD, is(PASSWORD)),
						hasProperty(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, is(PASSWORD))
				)))
				.andExpect(model().attributeHasFieldErrors(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM,
				                                           RegistrationForm.FIELD_NAME_EMAIL
				));
	}

	@Test
	@DatabaseSetup("users.xml")
	@ExpectedDatabase(value = "users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void registerUserAccount_should_render_registration_form_with_errors_when_registration_with_email_exists() throws Exception
	{
		mockMvc.perform(post("/user/register")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(RegistrationForm.FIELD_NAME_EMAIL, IntegrationTestConstants.User.REGISTERED_USER.getEmail())
				                .param(RegistrationForm.FIELD_NAME_FIRST_NAME, FIRST_NAME)
				                .param(RegistrationForm.FIELD_NAME_LAST_NAME, LAST_NAME)
				                .param(RegistrationForm.FIELD_NAME_PASSWORD, PASSWORD)
				                .param(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, PASSWORD)
				                .sessionAttr(RegistrationForm.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
		)
				.andExpect(status().isOk())
				.andExpect(view().name("user/registrationForm"))
				.andExpect(model().attribute(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM, allOf(
						hasProperty(RegistrationForm.FIELD_NAME_EMAIL, is(IntegrationTestConstants.User.REGISTERED_USER.getEmail())),
						hasProperty(RegistrationForm.FIELD_NAME_FIRST_NAME, is(FIRST_NAME)),
						hasProperty(RegistrationForm.FIELD_NAME_LAST_NAME, is(LAST_NAME)),
						hasProperty(RegistrationForm.FIELD_NAME_PASSWORD, is(PASSWORD)),
						hasProperty(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, is(PASSWORD))
				)))
				.andExpect(model().attributeHasFieldErrors(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM,
				                                           RegistrationForm.FIELD_NAME_EMAIL
				));
	}

	@Test
	@DatabaseSetup("no-users.xml")
	@ExpectedDatabase(value = "register-user-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void registerUserAccount_should_create_new_account_and_render_index_page_when_registration_ok() throws Exception
	{
		mockMvc.perform(post("/user/register")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(RegistrationForm.FIELD_NAME_EMAIL, EMAIL)
				                .param(RegistrationForm.FIELD_NAME_FIRST_NAME, FIRST_NAME)
				                .param(RegistrationForm.FIELD_NAME_LAST_NAME, LAST_NAME)
				                .param(RegistrationForm.FIELD_NAME_PASSWORD, PASSWORD)
				                .param(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, PASSWORD)
				                .sessionAttr(RegistrationForm.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
		)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/"));
	}
}
