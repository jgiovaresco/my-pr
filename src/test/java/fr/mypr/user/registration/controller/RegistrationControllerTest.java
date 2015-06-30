package fr.mypr.user.registration.controller;

import fr.mypr.UnitTestWebConfiguration;
import fr.mypr.config.WebConfiguration;
import fr.mypr.security.util.SecurityContextAssert;
import fr.mypr.user.model.UserAccount;
import fr.mypr.user.registration.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static fr.mypr.user.registration.RegistrationFormAssert.assertThatRegistrationForm;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestWebConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
public class RegistrationControllerTest
{

	private static final String EMAIL = "john.smith@gmail.com";
	private static final String MALFORMED_EMAIL = "john.smithatgmail.com";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Smith";
	private static final String PASSWORD = "password";
	private static final String PASSWORD_VERIFICATION = "confirmPassword";

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private RegistrationService registrationServiceMock;

	@Before
	public void setUp()
	{
		Mockito.reset(registrationServiceMock);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();

		SecurityContextHolder.getContext().setAuthentication(null);
	}

	@Test
	public void showRegistrationForm_should_render_registration_page_with_empty_form() throws Exception
	{
		mockMvc.perform(get("/user/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/registrationForm"))
				.andExpect(model().attribute(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM, allOf(
						hasProperty(RegistrationForm.FIELD_NAME_EMAIL, isEmptyOrNullString()),
						hasProperty(RegistrationForm.FIELD_NAME_FIRST_NAME, isEmptyOrNullString()),
						hasProperty(RegistrationForm.FIELD_NAME_LAST_NAME, isEmptyOrNullString()),
						hasProperty(RegistrationForm.FIELD_NAME_PASSWORD, isEmptyOrNullString()),
						hasProperty(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, isEmptyOrNullString())
				)));

		verifyZeroInteractions(registrationServiceMock);
	}

	@Test
	public void registerUserAccount_should_render_registration_page_with_errors_when_registration_with_empty_form() throws Exception
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
				.andExpect(model().attributeHasFieldErrors(
						RegistrationForm.MODEL_ATTRIBUTE_USER_FORM,
						RegistrationForm.FIELD_NAME_EMAIL,
						RegistrationForm.FIELD_NAME_FIRST_NAME,
						RegistrationForm.FIELD_NAME_LAST_NAME,
						RegistrationForm.FIELD_NAME_PASSWORD,
						RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD
				));

		SecurityContextAssert.assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
		verifyZeroInteractions(registrationServiceMock);
	}

	@Test
	public void registerUserAccount_should_render_registration_form_with_errors_when_fields_too_long() throws Exception
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

		SecurityContextAssert.assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
		verifyZeroInteractions(registrationServiceMock);
	}

	@Test
	public void registerUserAccount_should_render_registration_form_with_errors_when_password_mismatch() throws Exception
	{
		mockMvc.perform(post("/user/register")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(RegistrationForm.FIELD_NAME_EMAIL, EMAIL)
				                .param(RegistrationForm.FIELD_NAME_FIRST_NAME, FIRST_NAME)
				                .param(RegistrationForm.FIELD_NAME_LAST_NAME, LAST_NAME)
				                .param(RegistrationForm.FIELD_NAME_PASSWORD, PASSWORD)
				                .param(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, PASSWORD_VERIFICATION)
				                .sessionAttr(RegistrationForm.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
		)
				.andExpect(status().isOk())
				.andExpect(view().name("user/registrationForm"))
				.andExpect(model().attribute(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM, allOf(
						hasProperty(RegistrationForm.FIELD_NAME_EMAIL, is(EMAIL)),
						hasProperty(RegistrationForm.FIELD_NAME_FIRST_NAME, is(FIRST_NAME)),
						hasProperty(RegistrationForm.FIELD_NAME_LAST_NAME, is(LAST_NAME)),
						hasProperty(RegistrationForm.FIELD_NAME_PASSWORD, is(PASSWORD)),
						hasProperty(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, is(PASSWORD_VERIFICATION))
				)))
				.andExpect(model().attributeHasFieldErrors(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM,
				                                           RegistrationForm.FIELD_NAME_PASSWORD,
				                                           RegistrationForm.FIELD_NAME_PASSWORD
				));

		SecurityContextAssert.assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
		verifyZeroInteractions(registrationServiceMock);
	}

	@Test
	public void registerUserAccount_should_render_registration_form_with_errors_when_email_exists() throws Exception
	{
		when(registrationServiceMock.registerNewUserAccount(Matchers.isA(RegistrationForm.class))).thenThrow(
				new DuplicateEmailException(""));

		mockMvc.perform(post("/user/register")
				                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				                .param(RegistrationForm.FIELD_NAME_EMAIL, EMAIL)
				                .param(RegistrationForm.FIELD_NAME_FIRST_NAME, FIRST_NAME)
				                .param(RegistrationForm.FIELD_NAME_LAST_NAME, LAST_NAME)
				                .param(RegistrationForm.FIELD_NAME_PASSWORD, PASSWORD)
				                .param(RegistrationForm.FIELD_NAME_CONFIRM_PASSWORD, PASSWORD)
				                .sessionAttr(RegistrationForm.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
		)
				.andExpect(status().isOk())
				.andExpect(view().name("user/registrationForm"))
				.andExpect(model().attribute(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM, allOf(
						hasProperty(RegistrationForm.FIELD_NAME_EMAIL, is(EMAIL)),
						hasProperty(RegistrationForm.FIELD_NAME_FIRST_NAME, is(FIRST_NAME)),
						hasProperty(RegistrationForm.FIELD_NAME_LAST_NAME, is(LAST_NAME)),
						hasProperty(RegistrationForm.FIELD_NAME_PASSWORD, is(PASSWORD)),
						hasProperty(RegistrationForm.FIELD_NAME_PASSWORD, is(PASSWORD))
				)))
				.andExpect(model().attributeHasFieldErrors(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM, RegistrationForm.FIELD_NAME_EMAIL));

		SecurityContextAssert.assertThat(SecurityContextHolder.getContext()).userIsAnonymous();

		ArgumentCaptor<RegistrationForm> registrationFormArgument = ArgumentCaptor.forClass(RegistrationForm.class);
		verify(registrationServiceMock, times(1)).registerNewUserAccount(registrationFormArgument.capture());
		verifyNoMoreInteractions(registrationServiceMock);

		RegistrationForm formObject = registrationFormArgument.getValue();
		assertThatRegistrationForm(formObject)
				.hasEmail(EMAIL)
				.hasFirstName(FIRST_NAME)
				.hasLastName(LAST_NAME)
				.hasPassword(PASSWORD)
				.hasConfirmPassword(PASSWORD);
	}

	@Test
	public void registerUserAccount_should_render_registration_form_with_errors_when_email_malformed() throws Exception
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
						hasProperty(RegistrationForm.FIELD_NAME_PASSWORD, is(PASSWORD))
				)))
				.andExpect(model().attributeHasFieldErrors(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM, RegistrationForm.FIELD_NAME_EMAIL));

		SecurityContextAssert.assertThat(SecurityContextHolder.getContext()).userIsAnonymous();

		verifyZeroInteractions(registrationServiceMock);
	}

	@Test
	public void registerUserAccount_should_create_new_account_and_render_index_page() throws Exception
	{
		UserAccount registered = UserAccount.builder()
				.id(1L)
				.email(EMAIL)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.password(PASSWORD)
				.build();

		when(registrationServiceMock.registerNewUserAccount(Matchers.isA(RegistrationForm.class))).thenReturn(registered);

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

		SecurityContextAssert.assertThat(SecurityContextHolder.getContext())
				.loggedInUserIs(registered)
				.loggedInUserHasPassword(registered.getPassword());

		ArgumentCaptor<RegistrationForm> registrationFormArgument = ArgumentCaptor.forClass(RegistrationForm.class);
		verify(registrationServiceMock, times(1)).registerNewUserAccount(registrationFormArgument.capture());
		verifyNoMoreInteractions(registrationServiceMock);

		RegistrationForm formObject = registrationFormArgument.getValue();
		assertThatRegistrationForm(formObject)
				.hasEmail(EMAIL)
				.hasFirstName(FIRST_NAME)
				.hasLastName(LAST_NAME)
				.hasPassword(PASSWORD)
				.hasConfirmPassword(PASSWORD);
	}

}
