package integration.tests;

import com.github.springtestdbunit.annotation.*;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import fr.mypr.MyPrApplication;
import fr.mypr.user.registration.RegistrationForm;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"integrationTest"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MyPrApplication.class})
@WebAppConfiguration
public class RegistrationFormSubmitIT
{
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
	@ExpectedDatabase(value="no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void showRegistrationForm_should_render_registration_page_with_empty_form_when_normal() throws Exception
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
	@ExpectedDatabase(value="no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void registerUserAccount_should_render_registration_form_with_errors_when_normal_registration_and_empty_form() throws Exception {
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
	}


}
