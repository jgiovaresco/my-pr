package fr.mypr.controller;

import fr.mypr.UnitTestWebConfiguration;
import fr.mypr.config.WebConfiguration;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {UnitTestWebConfiguration.class, WebConfiguration.class})
public class IndexControllerTest
{
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

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
				.andExpect(view().name("index"))
				.andExpect(forwardedUrl("templates/index.html"));
	}

}