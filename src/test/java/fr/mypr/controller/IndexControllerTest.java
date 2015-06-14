package fr.mypr.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class IndexControllerTest
{
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Configuration
	@ComponentScan(basePackageClasses = IndexController.class)
	public static class TestConfiguration
	{
		@Bean
		public ViewResolver viewResolver()
		{
			ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
			templateResolver.setTemplateMode("XHTML");
			templateResolver.setPrefix("templates/");
			templateResolver.setSuffix(".html");

			SpringTemplateEngine engine = new SpringTemplateEngine();
			engine.setTemplateResolver(templateResolver);
			ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
			viewResolver.setTemplateEngine(engine);
			return viewResolver;
		}
	}

	@Before
	public void setUp() throws Exception
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void should_show_index_page() throws Exception
	{

		mockMvc.perform(get("/"))
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Welcome to <span>My PR</span>")));
	}

}