package fr.mypr.ihm.controller;

import fr.mypr.*;
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

import static integration.IntegrationTestConstants.User.REGISTERED_USER;
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

	@Before
	public void setUp()
	{

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();

		UserDetails registeredUser = REGISTERED_USER.getUserDetails();
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(registeredUser, null, registeredUser.getAuthorities()));
	}

	@Test
	public void showPersonalRecords_should_render_personal_records_list() throws Exception
	{
		mockMvc.perform(get("/pr"))
				.andExpect(status().isOk())
				.andExpect(view().name("/pr/list"));

	}
}