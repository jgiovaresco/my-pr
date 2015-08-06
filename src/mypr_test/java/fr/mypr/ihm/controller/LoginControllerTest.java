package fr.mypr.ihm.controller;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class LoginControllerTest
{
	@Autowired
	private WebApplicationContext webAppContext;

	private MockMvc mockMvc;

	@Configuration
	public static class TestConfiguration
	{
		@Bean
		public LoginController loginController()
		{
			return new LoginController();
		}

		@Bean
		public ViewResolver viewResolver()
		{
			InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
			viewResolver.setPrefix("templates/");
			viewResolver.setSuffix(".html");
			return viewResolver;
		}
	}

	@Before
	public void setUp()
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
				.build();
	}

	@Test
	public void should_render_login_view() throws Exception
	{
		mockMvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/login"))
				.andExpect(forwardedUrl("templates/user/login.html"));
	}
}