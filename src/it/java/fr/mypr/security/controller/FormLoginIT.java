package fr.mypr.security.controller;

import fr.mypr.*;
import fr.mypr.rule.InitUserAccountRule;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"integrationTest"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MyPrApplication.class})
@WebAppConfiguration
public class FormLoginIT
{
	private static final String NOT_FOUND_USER = "not@found.com";
	private static final String INVALID_PASSWORD = "invalidPassword";

	private static final String REQUEST_PARAM_EMAIL = "email";
	private static final String REQUEST_PARAM_PASSWORD = "password";

	@Rule
	@Autowired
	public InitUserAccountRule initUserAccountRule;

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
	public void login_should_redirect_to_login_form_when_invalid_password() throws Exception
	{
		// @formatter:off
		mockMvc.perform(
				post("/login/authenticate")
						.param(REQUEST_PARAM_EMAIL, IntegrationTestConstants.User.REGISTERED_USER.getEmail())
						.param(REQUEST_PARAM_PASSWORD, INVALID_PASSWORD)
				)
//				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/login?error=bad_credentials"));
		// @formatter:on
	}

	@Test
	public void login_should_redirect_to_login_form_when_user_not_found() throws Exception
	{
		// @formatter:off
		mockMvc.perform(
				post("/login/authenticate")
						.param(REQUEST_PARAM_EMAIL, NOT_FOUND_USER)
						.param(REQUEST_PARAM_PASSWORD, INVALID_PASSWORD)
				)
//				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/login?error=bad_credentials"));
		// @formatter:on
	}

	@Test
	public void login_should_redirect_to_index_page_when_correct_credentials() throws Exception
	{
		// @formatter:off
		mockMvc.perform(
				post("/login/authenticate")
						.param(REQUEST_PARAM_EMAIL, IntegrationTestConstants.User.REGISTERED_USER.getEmail())
						.param(REQUEST_PARAM_PASSWORD, IntegrationTestConstants.User.REGISTERED_USER.getPassword())
				)
//				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/"));
		// @formatter:on
	}
}
